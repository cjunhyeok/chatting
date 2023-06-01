package com.chat.chatting.service;

import com.chat.chatting.domain.ChatRoom;
import com.chat.chatting.domain.Member;
import com.chat.chatting.repository.ChatRoomRepository;
import com.chat.chatting.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatRoomService {

    private final MemberRepository memberRepository;
    private final ChatRoomRepository chatRoomRepository;

    @Transactional
    public Long saveChatRoom(String title, Long senderId, Long receiverId) {

        Member findFirstMember = memberRepository.findById(senderId).orElseThrow(
                () -> new IllegalArgumentException("member not exist")
        );

        Member findSecondMember = memberRepository.findById(receiverId).orElseThrow(
                () -> new IllegalArgumentException("member not exist")
        );

        ChatRoom chatRoom = ChatRoom.builder()
                .title(title)
                .firstParticipation(findFirstMember)
                .secondParticipation(findSecondMember)
                .build();

        ChatRoom savedChatRoom = chatRoomRepository.save(chatRoom);

        return savedChatRoom.getId();
    }

    public ChatRoom findByTitle(String title) {
        return chatRoomRepository.findByTitle(title);
    }

    public ChatRoom findById(Long id) {
        return chatRoomRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("chatRoom not exist")
        );
    }

    public List<ChatRoom> findAllByLoginMember(Long loginMemberId) {
        return chatRoomRepository.findAllByLoginMemberId(loginMemberId);
    }

    public ChatRoom findByLoginMemberIdWithReceiverId(Long loginMemberId, Long receiverId) {
        return chatRoomRepository.findByLoginMemberIdWithReceiverId(loginMemberId, receiverId);
    }
}
