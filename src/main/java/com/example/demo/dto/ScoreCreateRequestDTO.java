package com.example.demo.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ScoreCreateRequestDTO {
    @NotBlank(message = "学号不能为空")
    private String studentNo;

    @NotBlank(message = "课程编号不能为空")
    private String courseId;

    @NotNull(message = "分数不能为空")
    @Min(value = 0, message = "分数必须在 0 到 100 之间")
    @Max(value = 100, message = "分数必须在 0 到 100 之间")
    private Integer score;

    public String getStudentNo() {
        return studentNo;
    }

    public void setStudentNo(String studentNo) {
        this.studentNo = studentNo;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}
