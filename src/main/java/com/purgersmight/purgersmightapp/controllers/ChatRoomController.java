package com.purgersmight.purgersmightapp.controllers;

import com.purgersmight.purgersmightapp.dto.ChatMessageReqDto;
import com.purgersmight.purgersmightapp.dto.ChatMessageResDto;
import com.purgersmight.purgersmightapp.utils.ChatMessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class ChatRoomController {

    @Autowired
    private ChatMessageUtil chatMessageUtil;

    @MessageMapping(value = "/chat-room/general")
    @SendTo(value = "/topic/chat-room/general")
    public ChatMessageResDto chatMessage(@RequestBody ChatMessageReqDto chatMessageReqDto) {

        return chatMessageUtil.processChatMessage(chatMessageReqDto);
    }
}
