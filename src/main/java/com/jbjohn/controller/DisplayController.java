package com.jbjohn.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Web controller
 */
@Controller
public class DisplayController {

    @RequestMapping("/")
    String index() {
        return "default";
    }
}