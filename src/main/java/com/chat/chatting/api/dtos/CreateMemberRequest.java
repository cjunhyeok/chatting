package com.chat.chatting.api.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateMemberRequest {

    private String username;
    private String password;
    private String nickname;
}
