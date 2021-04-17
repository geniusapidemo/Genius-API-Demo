package com.genius.api.svc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.genius.api.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class GeniusServiceImpl implements GeniusService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GeniusServiceImpl.class);

    // Rest template with 30 seconds connection timeout
    private RestTemplate restTemplate = new RestTemplateBuilder().setConnectTimeout(Duration.ofSeconds(30)).build();

    private static final String ACCESS_TOKEN = "gxCt58BJMPC5gRF_qNaQj9rAbPdimFMHE7UjI-LshRuJEkopZuowlUwfMzdaieFT";
    private static final String SEARCH_URL = "https://api.genius.com/search?q=%s&page=1&per_page=20&access_token=%s";
    private static final String ARTIST_DETAILS_URL = "https://api.genius.com/artists/%d?&access_token=%s";
    private static final String ARTIST_SONGS_URL = "https://api.genius.com/artists/%d/songs?access_token=%s&page=1&per_page=15&sort=popularity";

    @Override
    @Cacheable("artists")
    public Set<Integer> getArtistIds(String artistName) {
        String url = String.format(SEARCH_URL, artistName, ACCESS_TOKEN);
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        return processSearchResponse(response, artistName);
    }

    @Override
    public int getPopularArtist(Set<Integer> artistIds) {
        try {
            // make non-blocking parallel calls to retrieve details for multiple artists
            List<CompletableFuture<Artist>> artistDetailFutures = artistIds.stream()
                    .map(artistId -> getArtistDetails(artistId))
                    .collect(Collectors.toList());

            CompletableFuture<Void> allFutures = CompletableFuture.allOf(
                    artistDetailFutures.toArray(new CompletableFuture[artistDetailFutures.size()])
            );

            CompletableFuture<List<Artist>> allArtistFutures = allFutures.thenApply(v -> {
                return artistDetailFutures.stream()
                        .map(artistDetailFuture -> artistDetailFuture.join())
                        .collect(Collectors.toList());
            });

            CompletableFuture<Artist> popularArtistFuture = allArtistFutures
                    .thenApply(artistFuture -> artistFuture.stream()
                            .filter(artist -> artist != null)
                            .max(Comparator.comparing(Artist::getFollowersCount))
                            .get());

            Artist popularArtist = popularArtistFuture.get(30, TimeUnit.SECONDS);
            LOGGER.info("Popular artist id is: {}", popularArtist.getId());
            return popularArtist.getId();
        } catch (Exception e) {
            LOGGER.error("Error occurred while retrieving Popular Artist", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    @Cacheable("songs")
    public List<Song> getSongs(int artistId) {
        String url = String.format(ARTIST_SONGS_URL, artistId, ACCESS_TOKEN);
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        return processArtistSongsResponse(response, artistId);
    }

    @Cacheable("artistDetails")
    private CompletableFuture<Artist> getArtistDetails(Integer artistId) {
        return CompletableFuture.supplyAsync(() -> {
            String requestURL = String.format(ARTIST_DETAILS_URL, artistId, ACCESS_TOKEN);
            ResponseEntity<String> responseEntity = restTemplate.getForEntity(requestURL, String.class);
            return processArtistResponse(responseEntity);
        });
    }

    /**
     * Method to process artist songs service response
     *
     * @param response Service response
     * @param artistId
     * @return List of songs
     */
    private List<Song> processArtistSongsResponse(ResponseEntity<String> response, int artistId) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            GeniusArtistSongsSvcResponse artistSongsSvcResponse = mapper.readValue(response.getBody(), GeniusArtistSongsSvcResponse.class);
            Song[] songs = artistSongsSvcResponse.getResponse().getSongs();
            if (songs == null || songs.length == 0) {
                return Collections.EMPTY_LIST;
            }
            LOGGER.info("Retrieved popular songs for the artist: {}", artistId);
            return Arrays.stream(songs)
                    .collect(Collectors.toList());
        } catch (JsonProcessingException e) {
            LOGGER.error("Error while parsing Genius Artist Service Response", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * Method to process artist service response
     *
     * @param response Artist service response
     * @return Artist object
     */
    private Artist processArtistResponse(ResponseEntity<String> response) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            GeniusArtistSvcResponse artistResponse = mapper.readValue(response.getBody(), GeniusArtistSvcResponse.class);
            return artistResponse.getResponse().getArtist();
        } catch (JsonProcessingException e) {
            LOGGER.error("Error while parsing Genius Artist Service Response", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * Method to process search service response
     *
     * @param response   Search service response
     * @param artistName
     * @return Set of Artist Ids
     */
    private Set<Integer> processSearchResponse(ResponseEntity<String> response, String artistName) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            GeniusSearchSvcResponse searchResponse = mapper.readValue(response.getBody(), GeniusSearchSvcResponse.class);
            if (searchResponse.getMeta().getStatus() != 200) {
                throw new RuntimeException("Search call to Genius failed");
            }
            Hit[] hits = searchResponse.getResponse().getHits();
            if (hits == null || hits.length == 0)
                return Collections.EMPTY_SET;

            LOGGER.info("Found {} Artists with the name: '{}'", hits.length, artistName);
            return Arrays.stream(hits).map(hit -> hit.getResult())
                    .filter(result -> result != null)
                    .map(result -> result.getPrimaryArtist())
                    .filter(primaryArtist -> primaryArtist != null)
                    .map(primaryArtist -> primaryArtist.getId())
                    .collect(Collectors.toSet());
        } catch (JsonProcessingException e) {
            LOGGER.error("Error occurred while processing artist id search", e);
            throw new RuntimeException(e);
        }
    }
}