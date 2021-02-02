package com.kyriexu.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author KyrieXu
 * @since 2021/1/31 21:36
 **/
@RestController
public class MainController {

    @GetMapping("/")
    public String index() {
        return "hello";
    }
}
