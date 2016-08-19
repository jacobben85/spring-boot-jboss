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
        String dockerUri = "http://" + config.getEshost() + "/";
        model.addAttribute("dockerUri", dockerUri);
        return "default";
    }

    @RequestMapping("/search")
    String search(final Model model) {
        String dockerUri = "http://" + config.getEshost() + "/";
        model.addAttribute("dockerUri", dockerUri);
        return "search";
    }

    @RequestMapping("/postgres")
    String postgres(final Model model) {
        String dockerUri = "http://" + config.getEshost() + "/";
        model.addAttribute("dockerUri", dockerUri);
        return "postgres";
    }
}
