package com.chat.chatting.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String username;
    private String password;
    private String nickname;
    private String state; // 채팅방 접근 상태


    @Builder
    public Member(String username, String password, String nickname, String state) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.state = state;
    }
}
