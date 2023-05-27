package com.chat.chatting.repository;

import com.chat.chatting.domain.ChatParticipation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatParticipationRepository extends JpaRepository<ChatParticipation, Long> {
}
