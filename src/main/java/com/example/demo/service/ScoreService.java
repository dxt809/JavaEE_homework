package com.example.demo.service;

import com.example.demo.entity.Course;
import com.example.demo.entity.Score;
import com.example.demo.dto.ScoreCreateRequestDTO;
import com.example.demo.dto.ScoreUpdateRequestDTO;
import com.example.demo.common.ErrorCode;
import com.example.demo.exception.BusinessException;
import org.springframework.http.HttpStatus;
import com.example.demo.entity.Student;
import com.example.demo.repository.CourseRepository;
import com.example.demo.repository.ScoreRepository;
import com.example.demo.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScoreService {
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final ScoreRepository scoreRepository;

    public ScoreService(StudentRepository studentRepository,
                        CourseRepository courseRepository,
                        ScoreRepository scoreRepository) {
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
        this.scoreRepository = scoreRepository;
    }

    public List<Student> listStudents() {
        return studentRepository.findAll();
    }

    public List<StudentScoreView> listScoresByStudentNo(String studentNo) {
        if (studentRepository.findByStudentNo(studentNo).isEmpty()) {
            return List.of();
        }

        return scoreRepository.findByStudentNo(studentNo).stream()
                .map(score -> courseRepository.findByCourseId(score.getCourseId())
                        .map(course -> new StudentScoreView(
                                course.getCourseId(), course.getCourseName(),
                                course.getCredit(), score.getScore()))
                        .orElseThrow(() -> new IllegalStateException(
                                "课程数据缺失: " + score.getCourseId())))
                .toList();
    }

    public List<Course> listCourses() {
        return courseRepository.findAll();
    }

    public synchronized Score update(long id, ScoreUpdateRequestDTO request) {
        Score score = findRequiredScore(id);
        score.setScore(request.getScore());
        return score;
    }

    public synchronized void delete(long id) {
        Score score = findRequiredScore(id);
        scoreRepository.delete(score);
    }

    private Score findRequiredScore(long id) {
        return scoreRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND,
                        HttpStatus.NOT_FOUND, "成绩不存在"));
    }

    public synchronized Score create(ScoreCreateRequestDTO request) {
        if (studentRepository.findByStudentNo(request.getStudentNo()).isEmpty()) {
            throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND,
                    HttpStatus.NOT_FOUND, "学生不存在");
        }
        if (courseRepository.findByCourseId(request.getCourseId()).isEmpty()) {
            throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND,
                    HttpStatus.NOT_FOUND, "课程不存在");
        }
        if (scoreRepository.existsByStudentNoAndCourseId(request.getStudentNo(), request.getCourseId())) {
            throw new BusinessException(ErrorCode.DATA_CONFLICT,
                    HttpStatus.CONFLICT, "该学生这门课程已录入成绩");
        }
        return scoreRepository.save(new Score(
                request.getStudentNo(), request.getCourseId(), request.getScore()));
    }
}
