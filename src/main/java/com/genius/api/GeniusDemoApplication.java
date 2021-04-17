package com.genius.api;

import com.genius.api.svc.GeniusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class GeniusDemoApplication {

    @Autowired
    private GeniusService geniusService;

    public static void main(String[] args) {
        SpringApplication.run(GeniusDemoApplication.class, args);
    }

}