package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class OpenApiIntegrationTests {
    @Autowired
    private MockMvc mvc;

    @Test
    void documentPageAndContractAreAvailable() throws Exception {
        mvc.perform(get("/doc.html")).andExpect(status().isOk());
        mvc.perform(get("/v3/api-docs"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.info.title").value("学生成绩管理系统接口文档"))
                .andExpect(jsonPath("$.paths['/api/students'].get").exists())
                .andExpect(jsonPath("$.paths['/api/students/{studentNo}/scores'].get").exists())
                .andExpect(jsonPath("$.paths['/api/courses'].get").exists())
                .andExpect(jsonPath("$.paths['/api/scores'].post.responses['201']").exists())
                .andExpect(jsonPath("$.paths['/api/scores'].post.responses['409']").exists())
                .andExpect(jsonPath("$.paths['/api/scores/{id}'].put.responses['200']").exists())
                .andExpect(jsonPath("$.paths['/api/scores/{id}'].delete.responses['204']").exists())
                .andExpect(jsonPath("$.paths['/api/scores/{id}'].delete.responses['204'].content").doesNotExist());
    }
}
