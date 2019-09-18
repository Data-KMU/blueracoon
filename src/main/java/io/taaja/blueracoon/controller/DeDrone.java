package io.taaja.blueracoon.controller;

import io.taaja.blueracoon.config.Constants;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Constants.API_PREFIX + "/area")
public class DeDrone {


    @GetMapping("")
    public String getFullTwin(){
        return "full";
    }


}