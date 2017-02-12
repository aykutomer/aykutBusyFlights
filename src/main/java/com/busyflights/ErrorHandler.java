package com.busyflights;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorHandler implements ErrorController {

    @RequestMapping("/error")
    public String errorHandle(Model model){


        return "error";

    }

    @Override
    public String getErrorPath() {
        return null;
    }
}
