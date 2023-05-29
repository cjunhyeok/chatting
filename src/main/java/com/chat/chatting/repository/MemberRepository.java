package com.chat.chatting.repository;

import com.chat.chatting.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Member findByUsername(String username);
    int countByUsername(String username);

    List<Member> findAll();
}
