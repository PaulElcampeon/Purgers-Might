package com.purgersmight.purgersmightapp.utils;

import com.purgersmight.purgersmightapp.PurgersMightAppApplication;
import com.purgersmight.purgersmightapp.config.WebSecurityConfig;
import com.purgersmight.purgersmightapp.dto.ChatMessageReqDto;
import com.purgersmight.purgersmightapp.dto.ChatMessageResDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {PurgersMightAppApplication.class, WebSecurityConfig.class}, webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class ChatMessageUtilTest {

    @Autowired
    private ChatMessageUtil chatMessageUtil;

    @Test
    public void processChatMessage_Test1() {

        ChatMessageReqDto chatMessageReqDto = new ChatMessageReqDto();

        chatMessageReqDto.setSender("Karlton");

        chatMessageReqDto.setContent("Hello how are you");

        ChatMessageResDto result = chatMessageUtil.processChatMessage(chatMessageReqDto);

        assertNotNull(result.getDate());

        assertNotNull(result.getTime());
    }
}
