package com.example.demo.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class ScoreUpdateRequestDTO {
    @NotNull(message = "分数不能为空")
    @Min(value = 0, message = "分数必须在 0 到 100 之间")
    @Max(value = 100, message = "分数必须在 0 到 100 之间")
    private Integer score;

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}
