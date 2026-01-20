package com.college.eventclub.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Web UI Controller - Serves the main HTML page
 */
@Controller
public class WebController {

    @GetMapping("/")
    public String index() {
        return "redirect:/index.html";
    }

    @GetMapping("/login")
    public String login() {
        return "redirect:/index.html";
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "redirect:/index.html";
    }
}
