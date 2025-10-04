package com.vikingsrise.simulator.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ReactController {

    // Handle React Router routes - forward all non-API routes to index.html
    @RequestMapping(value = {"/", "/simulator", "/about", "/help"})
    public String index() {
        return "forward:/index.html";
    }
}
