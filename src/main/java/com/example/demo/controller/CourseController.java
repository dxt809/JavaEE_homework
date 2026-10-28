package com.example.demo.controller;

import com.example.demo.entity.Course;
import com.example.demo.service.ScoreService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {
    private final ScoreService scoreService;

    public CourseController(ScoreService scoreService) {
        this.scoreService = scoreService;
    }

    @GetMapping
    public List<Course> listCourses() {
        return scoreService.listCourses();
    }
}
