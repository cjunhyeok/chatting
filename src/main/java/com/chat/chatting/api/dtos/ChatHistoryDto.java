package com.chat.chatting.api.dtos;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ChatHistoryDto {

    private String senderNickname;
    private String receiverNickname;
    private String message;

    @Builder
    public ChatHistoryDto(String senderNickname, String receiverNickname, String message) {
        this.senderNickname = senderNickname;
        this.receiverNickname = receiverNickname;
        this.message = message;
    }
}
