package com.purgersmight.purgersmightapp.controllers;

import com.purgersmight.purgersmightapp.services.ExperienceService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = ExperienceServiceController.class, secure = false)
public class ExperienceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @SpyBean
    private ExperienceService experienceService;

    @Test
    public void getExperiencePoint_Test1() throws Exception {

        mockMvc.perform(get("/experience-service/get-experience/7/8")
                .accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk())
                .andExpect(content().string("24"))
                .andReturn();
    }

    @Test
    public void getExperiencePoint_Test2() throws Exception {

        mockMvc.perform(get("/experience-service/get-experience/8/7")
                .accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk())
                .andExpect(content().string("16"))
                .andReturn();
    }

    @Test
    public void getExperiencePoint_Test3() throws Exception {

        mockMvc.perform(get("/experience-service/get-experience/7/7")
                .accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk())
                .andExpect(content().string("20"))
                .andReturn();
    }

    @Test
    public void getExperiencePoint_Test4() throws Exception {

        mockMvc.perform(get("/experience-service/get-experience/1/7")
                .accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk())
                .andExpect(content().string("40"))
                .andReturn();
    }

    @Test
    public void getExperiencePoint_Test5() throws Exception {

        mockMvc.perform(get("/experience-service/get-experience/7/1")
                .accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk())
                .andExpect(content().string("0"))
                .andReturn();
    }
}
