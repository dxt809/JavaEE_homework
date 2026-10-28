package com.example.demo.controller;

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

@RestController
@RequestMapping("/api/scores")
public class ScoreController {
    private final ScoreService scoreService;

    public ScoreController(ScoreService scoreService) {
        this.scoreService = scoreService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Score>> create(@Valid @RequestBody ScoreCreateRequestDTO request) {
        Score created = scoreService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.ok(created));
    }

    @PutMapping("/{id}")
    public ApiResponse<Score> update(@PathVariable long id,
                                     @Valid @RequestBody ScoreUpdateRequestDTO request) {
        return ApiResponse.ok(scoreService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        scoreService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
