package com.animalchipping.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FrontEndController {
    @GetMapping({"/", "/home", "/account", "/animal", "/animal-type", "/location", "/visited-location"})
    public String index() {
        return "index.html";
    }
}
