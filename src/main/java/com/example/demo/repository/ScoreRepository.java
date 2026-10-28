package com.example.demo.repository;

import com.example.demo.entity.Score;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ScoreRepository {
    private final List<Score> scores = List.of(
            new Score("2023010001", "C001", 92),
            new Score("2023010001", "C002", 85),
            new Score("2023010001", "C003", 78),
            new Score("2023010002", "C001", 88),
            new Score("2023010002", "C002", 91),
            new Score("2023010002", "C003", 86),
            new Score("2023010003", "C001", 74),
            new Score("2023010003", "C002", 68),
            new Score("2023010003", "C003", 80),
            new Score("2023010004", "C001", 95),
            new Score("2023010004", "C002", 89),
            new Score("2023010004", "C003", 92),
            new Score("2023010005", "C001", 61),
            new Score("2023010005", "C002", 72),
            new Score("2023010005", "C003", 65)
    );

    public List<Score> findByStudentNo(String studentNo) {
        return scores.stream()
                .filter(score -> score.getStudentNo().equals(studentNo))
                .toList();
    }
}
