package com.rifatul.SpringBucks.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping(path = "/")
public class HomeController {

    @GetMapping
    public String home() {
        return "hello world.";
    }

    record BasicBody(int id, String firstname, String lastname) {
        public String fullname() {
            return String.format("%s %s", firstname(), lastname());
        }
    }

    @PostMapping
    public String home3(@RequestBody BasicBody body) {
        return body.fullname();
    }

}
