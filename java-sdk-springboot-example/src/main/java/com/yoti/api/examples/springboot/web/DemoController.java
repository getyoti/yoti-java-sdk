package com.yoti.api.examples.springboot.web;

import com.yoti.api.examples.springboot.auth.YotiPrincipal;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DemoController {

    public static final String MAPPING_URI = "/";

    @GetMapping(MAPPING_URI)
    public String homePage(@AuthenticationPrincipal final YotiPrincipal user, final ModelMap model) {
        model.addAttribute("user", user);
        return "home";
    }
}
