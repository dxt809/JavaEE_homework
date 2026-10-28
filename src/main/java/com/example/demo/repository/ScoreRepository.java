package com.example.demo.repository;

import com.example.demo.entity.Score;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class ScoreRepository {
    private final List<Score> scores = new CopyOnWriteArrayList<>(List.of(
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
    ));
    private final AtomicLong nextId;

    public ScoreRepository() {
        long id = 1;
        for (Score score : scores) {
            score.setId(id++);
        }
        nextId = new AtomicLong(id);
    }

    public boolean existsByStudentNoAndCourseId(String studentNo, String courseId) {
        return scores.stream().anyMatch(score -> score.getStudentNo().equals(studentNo)
                && score.getCourseId().equals(courseId));
    }

    public Score save(Score score) {
        score.setId(nextId.getAndIncrement());
        scores.add(score);
        return score;
    }

    public Optional<Score> findById(long id) {
        return scores.stream().filter(score -> score.getId() == id).findFirst();
    }

    public void delete(Score score) {
        scores.remove(score);
    }

    public List<Score> findByStudentNo(String studentNo) {
        return scores.stream()
                .filter(score -> score.getStudentNo().equals(studentNo))
                .toList();
    }
}
