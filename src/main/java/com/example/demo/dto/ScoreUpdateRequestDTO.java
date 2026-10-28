package com.example.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class ScoreUpdateRequestDTO {
    @NotNull(message = "分数不能为空")
    @Min(value = 0, message = "分数必须在 0 到 100 之间")
    @Max(value = 100, message = "分数必须在 0 到 100 之间")
    @Schema(description = "整数分数，范围0至100", example = "80", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer score;

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}
