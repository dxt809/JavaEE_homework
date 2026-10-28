package com.example.demo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.example.demo.common.ApiResponse;
import com.example.demo.dto.ScoreCreateRequestDTO;
import com.example.demo.dto.ScoreUpdateRequestDTO;
import com.example.demo.entity.Score;
import com.example.demo.service.ScoreService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "成绩维护")
@RestController
@RequestMapping("/api/scores")
public class ScoreController {
    private final ScoreService scoreService;

    public ScoreController(ScoreService scoreService) {
        this.scoreService = scoreService;
    }

    @Operation(summary = "录入成绩", description = "学生和课程须存在；重复录入返回409。初始组合已录满，请先删除再测试创建。")
    @PostMapping
    public ResponseEntity<ApiResponse<Score>> create(@Valid @RequestBody ScoreCreateRequestDTO request) {
        Score created = scoreService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.ok(created));
    }

    @Operation(summary = "修改成绩", description = "仅修改分数；成绩不存在返回404。")
    @PutMapping("/{id}")
    public ApiResponse<Score> update(@PathVariable long id,
                                     @Valid @RequestBody ScoreUpdateRequestDTO request) {
        return ApiResponse.ok(scoreService.update(id, request));
    }

    @Operation(summary = "删除成绩", description = "成功返回204空响应；成绩不存在返回404。")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        scoreService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
