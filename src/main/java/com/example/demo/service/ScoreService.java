package com.example.demo.service;

import com.example.demo.entity.Course;
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
}
