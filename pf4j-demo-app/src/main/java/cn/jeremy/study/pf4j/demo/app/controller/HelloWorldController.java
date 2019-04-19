package cn.jeremy.study.pf4j.demo.app.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@RestController
public class HelloWorldController
{
    @PostConstruct
    public void init(){

    }

    @RequestMapping("/hello")
    public String index() {
        return "Hello World";
    }
}
