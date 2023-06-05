package com.chat.chatting.api.dtos;

import lombok.*;

@Getter
@Setter
public class Message {

    private String message;
    private Long receiverId;
    private Long chatRoomId;
    private String senderNickname;

    public Message() {
    }

    public Message(String message, Long receiverId) {
        this.message = message;
        this.receiverId = receiverId;
    }

    @Builder
    public Message(String message, Long receiverId, Long chatRoomId, String senderNickname) {
        this.message = message;
        this.receiverId = receiverId;
        this.chatRoomId = chatRoomId;
        this.senderNickname = senderNickname;
    }
}

