package com.wpshop;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)

public class ApplicationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getAllWhenEmptyRepository() throws Exception {
        this.mockMvc.perform(get("/offer/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("[]")));
    }

    @Test
    public void getSingleOnEmptyRepository() throws Exception {
        this.mockMvc.perform(get("/offer/1")).andExpect(status().isNotFound());
    }

    @Test
    public void postSingle() throws Exception {
        this.mockMvc.perform(post("/offer/")
                .content("{\"name\":\"name1\",\"description\":\"description1\",\"price\":{\"price\":100.0,\"currency\":\"GBP\"}}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void getExistingSingle() throws Exception {
        String json = "\"name\":\"name1\",\"description\":\"description1\",\"price\":{\"price\":100.0,\"currency\":\"GBP\"}";
        this.mockMvc.perform(post("/offer/")
                .content("{" + json + "}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
        this.mockMvc.perform(get("/offer/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(json)));
    }

    @Test
    public void getAllWhenNonEmpty() throws Exception {
        String json = "\"name\":\"name1\",\"description\":\"description1\",\"price\":{\"price\":100.0,\"currency\":\"GBP\"}";
        this.mockMvc.perform(post("/offer/")
                .content("{" + json + "}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
        this.mockMvc.perform(get("/offer/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(content().string(containsString(json)));
    }
}