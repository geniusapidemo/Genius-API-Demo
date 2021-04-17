package com.genius.api.svc;

import com.genius.api.model.Song;

import java.util.List;
import java.util.Set;

/**
 * Service to retrieve Artists and Songs using Genius API
 */
public interface GeniusService {

    /**
     * Method to retrieve Artist Ids that match given name
     *
     * @param name Artist name
     * @return Set of artist ids
     */
    public Set<Integer> getArtistIds(String name);

    /**
     * Method to find the popular Artist among the given Artist Ids
     *
     * @param artistIds Set of Artist Ids
     * @return Popular Artist Id
     */
    public int getPopularArtist(Set<Integer> artistIds);

    /**
     * Method to return list of songs for a given Artist Id
     *
     * @param artistId Artist ID
     * @return List of songs
     */
    public List<Song> getSongs(int artistId);
}