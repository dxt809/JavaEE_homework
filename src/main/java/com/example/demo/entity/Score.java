package com.example.demo.entity;

public class Score {
    private long id;
    private String studentNo;
    private String courseId;
    private int score;

    public Score() {
    }

    public Score(String studentNo, String courseId, int score) {
        this.studentNo = studentNo;
        this.courseId = courseId;
        this.score = score;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
