package com.university.university;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StudentController {

    @RequestMapping("/")
    public String hello()
    {
        return "hi";
    }
}
