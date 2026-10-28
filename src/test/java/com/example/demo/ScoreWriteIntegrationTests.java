package com.example.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ScoreWriteIntegrationTests {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper mapper;

    @Test
    void updatesDeletesAndRecreatesScore() throws Exception {
        mvc.perform(put("/api/scores/1").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"score\":80}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.studentNo").value("2023010001"))
                .andExpect(jsonPath("$.data.courseId").value("C001"))
                .andExpect(jsonPath("$.data.score").value(80));
        mvc.perform(get("/api/students/2023010001/scores"))
                .andExpect(jsonPath("$.data[0].score").value(80));
        mvc.perform(delete("/api/scores/1"))
                .andExpect(status().isNoContent()).andExpect(content().string(""));
        mvc.perform(get("/api/students/2023010001/scores"))
                .andExpect(jsonPath("$.data", hasSize(2)));
        String result = mvc.perform(post("/api/scores").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"studentNo\":\"2023010001\",\"courseId\":\"C001\",\"score\":90}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.id").value(16))
                .andExpect(jsonPath("$.data.score").value(90))
                .andReturn().getResponse().getContentAsString();
        long id = mapper.readTree(result).path("data").path("id").asLong();
        mvc.perform(put("/api/scores/{id}", id).contentType(MediaType.APPLICATION_JSON)
                        .content("{\"score\":100}"))
                .andExpect(status().isOk()).andExpect(jsonPath("$.data.score").value(100));
    }

    @Test
    void missingScoresReturnSameErrorForUpdateAndDelete() throws Exception {
        mvc.perform(put("/api/scores/9999").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"score\":80}"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value(40401))
                .andExpect(jsonPath("$.message").value("成绩不存在"))
                .andExpect(content().json("{\"code\":40401,\"message\":\"成绩不存在\",\"data\":null}", true));
        mvc.perform(delete("/api/scores/9999"))
                .andExpect(status().isNotFound())
                .andExpect(content().json("{\"code\":40401,\"message\":\"成绩不存在\",\"data\":null}", true));
    }

    @Test
    void updateRejectsInvalidOrMissingScoreWithoutChangingData() throws Exception {
        for (String body : new String[]{"{\"score\":101}", "{\"score\":-1}", "{}"}) {
            mvc.perform(put("/api/scores/1").contentType(MediaType.APPLICATION_JSON).content(body))
                    .andExpect(status().isBadRequest()).andExpect(jsonPath("$.code").value(40001));
        }
        mvc.perform(get("/api/students/2023010001/scores"))
                .andExpect(jsonPath("$.data[0].score").value(92));
        mvc.perform(put("/api/scores/1").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"score\":0}"))
                .andExpect(status().isOk()).andExpect(jsonPath("$.data.score").value(0));
    }
}
