package com.purgersmight.purgersmightapp.controllers;

import com.purgersmight.purgersmightapp.models.BattleStatistics;
import com.purgersmight.purgersmightapp.services.BattleStatisticsService;
import com.purgersmight.purgersmightapp.utils.ObjectMapperUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = BattleStatisticsServiceController.class, secure = false)
public class BattleStatisticsServiceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BattleStatisticsService battleStatisticsService;

    @Test
    public void getLeaderboard_Test1() throws Exception {

        List<BattleStatistics> battleStatisticsList = Arrays.asList(
                new BattleStatistics("Kable"),
                new BattleStatistics("Plato"),
                new BattleStatistics("Teno"),
                new BattleStatistics("Krendo"),
                new BattleStatistics("Lendo"),
                new BattleStatistics("Picto"),
                new BattleStatistics("Nexto"),
                new BattleStatistics("Trecto")
        );

        when(battleStatisticsService.getLeaderBoard(anyInt())).thenReturn(battleStatisticsList);

        String jsonRes = ObjectMapperUtils.getObjectMapper().writeValueAsString(battleStatisticsList);

        mockMvc.perform(get("/battle-statistics-service/leaderboard/0")
                .with(user("admin").password("admin123").roles("USER", "ADMIN"))
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(content().json(jsonRes))
                .andReturn();

        verify(battleStatisticsService, times(1)).getLeaderBoard(anyInt());
    }

    @Test
    public void getBattleStatistics() throws Exception {

        BattleStatistics battleStatistics = new BattleStatistics("Kable");

        when(battleStatisticsService.getBattleStatistics(anyString())).thenReturn(battleStatistics);

        String jsonRes = ObjectMapperUtils.getObjectMapper().writeValueAsString(battleStatistics);

        mockMvc.perform(get("/battle-statistics-service/battle-statistics//Kable")
                .with(user("admin").password("admin123").roles("USER", "ADMIN"))
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(content().json(jsonRes))
                .andReturn();

        verify(battleStatisticsService, times(1)).getBattleStatistics(anyString());
    }
}
