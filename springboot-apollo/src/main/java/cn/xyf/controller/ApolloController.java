package cn.xyf.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/apollo")
public class ApolloController {
    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }
}
