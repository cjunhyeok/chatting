package com.chat.chatting.controller;

import com.chat.chatting.dto.ChatMessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final SimpMessagingTemplate template; // 특정 broker로 메시지 전달

    // Client 가 SEND 할 수 있는 경로
    // WebSocketConfig 에서 설정한 applicationDestinationPrefixes 와 @MessageMapping 경로가 병합
    // "/pub/chat/enter"
    @MessageMapping("/chat/enter")
    public void enter(ChatMessageDto message) {
        message.setMessage(message.getWriter() + "님이 채팅방에 참여하였습니다.");
        template.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
    }

    @MessageMapping("/chat/message")
    public void message(ChatMessageDto message) {
        System.out.println("message : " + message.getMessage());
        template.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
    }
}
