package com.genius.api.controller;

import com.genius.api.model.Song;
import com.genius.api.svc.GeniusService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

/**
 * JUnit test case for GeniusController
 */
@RunWith(MockitoJUnitRunner.class)
public class GeniusControllerTest {

    @Mock
    private GeniusService geniusService;

    @InjectMocks
    private GeniusController controller;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testHappyPath() {
        Set<Integer> artists = new HashSet<>();
        artists.add(2020);
        artists.add(2021);

        Song song = new Song();
        song.setId(3399L);
        song.setApiPath("/songs/3399");
        song.setFullTitle("It's my song by SK");

        List<Song> songs = Arrays.asList(song);

        // mock service calls
        when(geniusService.getArtistIds(anyString())).thenReturn(artists);
        when(geniusService.getPopularArtist(any())).thenReturn(2021);
        when(geniusService.getSongs(anyInt())).thenReturn(songs);

        // controller test case
        ResponseEntity resp = controller.getSongs("SK");
        assertNotNull(resp);
        assertNotNull(resp.getBody());
    }
}