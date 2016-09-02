package com.jbjohn.controller;

import com.jbjohn.properties.Configurations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * Web controller
 */
@Controller
public class DisplayController {

    @Autowired
    private Configurations config;

    @RequestMapping("/")
    String index(final Model model) {
        return "default";
    }

    @RequestMapping("/partSearch")
    String partSearch(final Model model) {
        return "partSearch";
    }
}
