package com.example.demo.repository;

import com.example.demo.entity.Student;
import org.springframework.stereotype.Repository;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Repository
public class StudentRepository {
    private final List<Student> students = List.of(
            new Student("2023010001", "张明", "计算机2301"),
            new Student("2023010002", "李婷", "计算机2301"),
            new Student("2023010003", "王浩", "计算机2301"),
            new Student("2023010004", "陈静", "计算机2302"),
            new Student("2023010005", "刘洋", "计算机2302")
    );

    public List<Student> findAll() {
        return students.stream()
                .sorted(Comparator.comparing(Student::getStudentNo))
                .toList();
    }

    public Optional<Student> findByStudentNo(String studentNo) {
        return students.stream()
                .filter(student -> student.getStudentNo().equals(studentNo))
                .findFirst();
    }
}
