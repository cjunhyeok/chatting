package com.chat.chatting.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Chat {

    @Id @GeneratedValue
    @Column(name = "chat_id")
    private Long id;
    private String content; // 메시지
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member sender; // 발신자
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member receiver; // 수신자
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_id")
    private ChatRoom chatRoom;


    @Builder
    public Chat(Member sender, Member receiver, ChatRoom chatRoom, String content) {
        this.content = content;
        this.sender = sender;
        this.receiver = receiver;
        this.chatRoom = chatRoom;
    }
}
