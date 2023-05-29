package com.chat.chatting.service;

import com.chat.chatting.domain.Member;
import com.chat.chatting.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Long join(String username, String password, String nickname) {
        Member member = Member.builder()
                .username(username)
                .password(password)
                .nickname(nickname)
                .build();

        Member savedMember = memberRepository.save(member);

        return savedMember.getId();
    }

    public Member findById(Long id) {
        return memberRepository.findById(id).orElseThrow(()
                -> new IllegalArgumentException("Member not exist"));
    }

    public List<Member> findAll() {
        return memberRepository.findAll();
    }
}
