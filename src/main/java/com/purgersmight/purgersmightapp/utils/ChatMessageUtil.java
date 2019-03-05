package com.purgersmight.purgersmightapp.utils;

import com.purgersmight.purgersmightapp.dto.ChatMessageReqDto;
import com.purgersmight.purgersmightapp.dto.ChatMessageResDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public class ChatMessageUtil {

    @Autowired
    private DateAndTimeUtil dateAndTimeUtil;

    public ChatMessageResDto processChatMessage(final ChatMessageReqDto chatMessageReqDto) {

        ChatMessageResDto chatMessageResDto = new ChatMessageResDto();

        chatMessageResDto.setSender(chatMessageReqDto.getSender());

        chatMessageResDto.setMessage(chatMessageReqDto.getContent());

        chatMessageResDto.setDate(dateAndTimeUtil.getDate());

        chatMessageResDto.setTime(dateAndTimeUtil.getTime());

        return chatMessageResDto;
    }
}
