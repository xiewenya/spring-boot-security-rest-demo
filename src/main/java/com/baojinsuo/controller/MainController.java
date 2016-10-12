package com.baojinsuo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.PostConstruct;

/**
 * Created by bresai on 2016/10/2.
 */
@Controller
public class MainController {

    @Autowired
    private ApplicationContext applicationContext;

    @PostConstruct
    public void init() {
        System.out.println(" *** MainController.init with: " + applicationContext);
    }

    @RequestMapping("/")
    public String index() {
        return "index";
    }
}
