package com.example.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ScoreCreateRequestDTO {
    @NotBlank(message = "学号不能为空")
    @Schema(description = "学号", example = "2023010001", requiredMode = Schema.RequiredMode.REQUIRED)
    private String studentNo;

    @NotBlank(message = "课程编号不能为空")
    @Schema(description = "课程编号", example = "C001", requiredMode = Schema.RequiredMode.REQUIRED)
    private String courseId;

    @NotNull(message = "分数不能为空")
    @Min(value = 0, message = "分数必须在 0 到 100 之间")
    @Max(value = 100, message = "分数必须在 0 到 100 之间")
    @Schema(description = "整数分数，范围0至100", example = "80", requiredMode = Schema.RequiredMode.REQUIRED)
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
