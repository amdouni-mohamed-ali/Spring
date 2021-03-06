package org.spring.tutorial.mvc.boot.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HelloController {

    /*
        endpoint url : http://localhost:8080/hello-spring-mvc
        error page example url : http://localhost:8080/hello-spring-mvc/anyThing
     */
    @RequestMapping("/")
    String home(ModelMap modal) {
        modal.addAttribute("title", "Dear Learner");
        modal.addAttribute("message", "Welcome to SpringBoot");
        return "hello";
    }
}
