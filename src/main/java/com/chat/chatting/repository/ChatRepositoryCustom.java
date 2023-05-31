package com.chat.chatting.repository;

import com.chat.chatting.domain.Chat;
import com.chat.chatting.domain.Member;

import java.util.List;

public interface ChatRepositoryCustom {

    List<Chat> findChatRooms(Member member);
}
