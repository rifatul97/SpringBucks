package com.rifatul.SpringBucks.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping(path = "/")
public class HomeController {

    @GetMapping
    public String home() {
        return "hello world.";
    }

    @GetMapping("/reponse")
    public ResponseEntity<String> responseEntityHome() {
        return ResponseEntity.ok(String.valueOf(100));
    }

}
