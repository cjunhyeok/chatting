package com.chat.chatting.service;

import com.chat.chatting.domain.Chat;
import com.chat.chatting.domain.Member;
import com.chat.chatting.repository.ChatRepository;
import com.chat.chatting.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatService {

    private final ChatRepository chatRepository;
    private final MemberRepository memberRepository;

    public Long saveChat(Long senderId, Long receiverId, String content, Boolean isRead) {
        Member findSender = memberRepository.findById(senderId).orElseThrow(
                () -> new IllegalArgumentException("member not exist")
        );

        Member findReceiver = memberRepository.findById(receiverId).orElseThrow(
                () -> new IllegalArgumentException("member not exist")
        );

        Chat chat = Chat.builder()
                .sender(findSender)
                .receiver(findReceiver)
                .content(content)
                .isRead(isRead)
                .build();

        Chat savedChat = chatRepository.save(chat);

        return savedChat.getId();
    }
}
