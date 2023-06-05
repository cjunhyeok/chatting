package com.chat.chatting.api;

import com.chat.chatting.api.dtos.ChatsResponse;
import com.chat.chatting.api.dtos.CreateMemberRequest;
import com.chat.chatting.api.dtos.MembersDto;
import com.chat.chatting.domain.Chat;
import com.chat.chatting.domain.Member;
import com.chat.chatting.security.MemberContext;
import com.chat.chatting.service.ChatService;
import com.chat.chatting.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController // json
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;
    private final ChatService chatService;

    @PostMapping("/api/members/new")
    public ResponseEntity<Long> createMember(@RequestBody CreateMemberRequest request) {

        Long joinMember = memberService.join(
                request.getUsername(),
                passwordEncoder.encode(request.getPassword()),
                request.getNickname());

        return ResponseEntity.ok(joinMember);
    }

    @GetMapping("/api/members")
    public List<MembersDto> members(@AuthenticationPrincipal MemberContext memberContext) {

        List<Member> members = memberService.findAll();

        return members.stream().map(
                        m -> new MembersDto(
                                m.getId(),
                                m.getNickname()
                        ))
                .collect(Collectors.toList());
    }

    @GetMapping("/chatRooms")
    public List<ChatsResponse> chats(@RequestParam(name = "receiverId", required = false) Long receiverId,
                                     @AuthenticationPrincipal MemberContext memberContext) {

        List<Chat> findChatRooms = chatService.findChatRooms(memberContext.getMember().getId());
        List<ChatsResponse> collect = findChatRooms.stream()
                .map(c -> new ChatsResponse(
                        c.getReceiver().getId()
                )).collect(Collectors.toList());

        return collect;
    }
}
