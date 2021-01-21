package com.yoti.docscan.demo.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DocScanErrorController implements ErrorController {

    @RequestMapping("/error")
    public String showErrorPage(final Model model, @RequestParam(value = "yotiErrorCode", required = false) String yotiErrorCode) {
        model.addAttribute("error", "An unknown error has occurred");

        if (yotiErrorCode != null && !yotiErrorCode.equals("")) {
            model.addAttribute("error", String.format("Error Code: %s", yotiErrorCode));
        }

        return "error";
    }

    @Override
    public String getErrorPath() {
        return null;
    }
}
