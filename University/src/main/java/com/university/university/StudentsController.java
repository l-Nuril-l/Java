package com.university.university;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

@Controller
public class StudentsController {
    ArrayList<Student> students = new ArrayList<>();

    @RequestMapping("/s")
    public String students(Student student, Model model)
    {
        if(student.getName() != null)
            students.add(student);
        model.addAttribute("students",students);
        System.out.println(students.size());
        return "students";
    }

    @RequestMapping("/w")
    public String welcome()
    {
        return "welcome";
    }
}
