package com.chat.chatting.security;

import com.chat.chatting.domain.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;

public class MemberContext extends User {

    private Member member;

    public MemberContext(Member member, List<GrantedAuthority> roles) {
        super(member.getUsername(), member.getPassword(), roles);
        this.member = member;
    }

    public Member getMember() {
        return member;
    }
}
