package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ScoreQueryIntegrationTests {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void listsAllStudentsInStudentNumberOrder() throws Exception {
        mockMvc.perform(get("/api/students"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(5)))
                .andExpect(jsonPath("$[0].studentNo").value("2023010001"))
                .andExpect(jsonPath("$[0].name").value("张明"))
                .andExpect(jsonPath("$[0].className").value("计算机2301"))
                .andExpect(jsonPath("$[4].studentNo").value("2023010005"));
    }

    @Test
    void listsScoresWithCourseDetails() throws Exception {
        mockMvc.perform(get("/api/students/2023010001/scores"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].courseId").value("C001"))
                .andExpect(jsonPath("$[0].courseName").value("Java EE 程序设计"))
                .andExpect(jsonPath("$[0].credit").value(3.0))
                .andExpect(jsonPath("$[0].score").value(92));
    }

    @Test
    void unknownStudentReturnsAnEmptyList() throws Exception {
        mockMvc.perform(get("/api/students/9999999999/scores"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    void listsAllCourses() throws Exception {
        mockMvc.perform(get("/api/courses"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].courseId").value("C001"))
                .andExpect(jsonPath("$[0].courseName").value("Java EE 程序设计"))
                .andExpect(jsonPath("$[0].credit").value(3.0));
    }
}
