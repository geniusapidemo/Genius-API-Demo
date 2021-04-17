package com.genius.api.controller;

import com.genius.api.model.Song;
import com.genius.api.svc.GeniusService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Set;

/**
 * Controller class to retrieve songs
 */
@Controller
public class GeniusController {

    private static final Logger LOGGER = LoggerFactory.getLogger(GeniusController.class);

    @Autowired
    private GeniusService geniusService;

    /**
     * Method to retrieve songs that belong to the given artist name.  Current implementation considers popular artist by follower count when multiple artists are found.
     *
     * @param artistName Artist name
     * @return List of songs
     */
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful retrieval", response = Song.class, responseContainer = "List"),
            @ApiResponse(code = 500, message = "Internal server error.  Please contact developer.")
    })
    @ApiOperation(value = "getSongs", notes = "Retrieve songs for a given artist name; songs are sorted by popularity", response = Song.class)
    @RequestMapping(value = "/genius/songs", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity getSongs(@ApiParam(value = "Artist name", required = true, defaultValue = "Elvis Presley")
                                   @RequestParam String artistName) {
        try {
            // retrieve artist ids for a given artist name
            Set<Integer> artistIds = geniusService.getArtistIds(artistName);

            // find out the popular among the artists per popular count
            int popularArtistId = geniusService.getPopularArtist(artistIds);

            // retrieve songs
            List<Song> songs = geniusService.getSongs(popularArtistId);

            return new ResponseEntity(songs, HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}