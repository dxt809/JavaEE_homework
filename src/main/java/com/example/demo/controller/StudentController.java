package com.example.demo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.example.demo.common.ApiResponse;
import com.example.demo.entity.Student;
import com.example.demo.service.ScoreService;
import com.example.demo.service.StudentScoreView;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "学生查询")
@RestController
@RequestMapping("/api/students")
public class StudentController {
    private final ScoreService scoreService;

    public StudentController(ScoreService scoreService) {
        this.scoreService = scoreService;
    }

    @Operation(summary = "查询全部学生")
    @GetMapping
    public ApiResponse<List<Student>> listStudents() {
        return ApiResponse.ok(scoreService.listStudents());
    }

    @Operation(summary = "按学号查询成绩", description = "含课程名称和学分；学号不存在返回200和空列表。")
    @GetMapping("/{studentNo}/scores")
    public ApiResponse<List<StudentScoreView>> listScoresByStudentNo(@PathVariable String studentNo) {
        return ApiResponse.ok(scoreService.listScoresByStudentNo(studentNo));
    }
}
