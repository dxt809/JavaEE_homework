package com.example.demo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.example.demo.common.ApiResponse;
import com.example.demo.entity.Course;
import com.example.demo.service.ScoreService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "课程查询")
@RestController
@RequestMapping("/api/courses")
public class CourseController {
    private final ScoreService scoreService;

    public CourseController(ScoreService scoreService) {
        this.scoreService = scoreService;
    }

    @Operation(summary = "查询全部课程")
    @GetMapping
    public ApiResponse<List<Course>> listCourses() {
        return ApiResponse.ok(scoreService.listCourses());
    }
}
