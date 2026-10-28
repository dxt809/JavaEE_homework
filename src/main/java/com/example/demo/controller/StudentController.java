package com.example.demo.controller;

import com.example.demo.common.ApiResponse;
import com.example.demo.entity.Student;
import com.example.demo.service.ScoreService;
import com.example.demo.service.StudentScoreView;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {
    private final ScoreService scoreService;

    public StudentController(ScoreService scoreService) {
        this.scoreService = scoreService;
    }

    @GetMapping
    public ApiResponse<List<Student>> listStudents() {
        return ApiResponse.ok(scoreService.listStudents());
    }

    @GetMapping("/{studentNo}/scores")
    public ApiResponse<List<StudentScoreView>> listScoresByStudentNo(@PathVariable String studentNo) {
        return ApiResponse.ok(scoreService.listScoresByStudentNo(studentNo));
    }
}
