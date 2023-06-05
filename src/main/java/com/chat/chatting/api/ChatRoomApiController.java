package com.chat.chatting.api;

import com.chat.chatting.api.dtos.ChatHistoryDto;
import com.chat.chatting.api.dtos.ChatRoomResponse;
import com.chat.chatting.domain.Chat;
import com.chat.chatting.domain.ChatRoom;
import com.chat.chatting.domain.Member;
import com.chat.chatting.security.MemberContext;
import com.chat.chatting.service.ChatRoomService;
import com.chat.chatting.service.ChatService;
import com.chat.chatting.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController // json
@RequiredArgsConstructor
public class ChatRoomApiController {

    private final ChatRoomService chatRoomService;
    private final MemberService memberService;
    private final ChatService chatService;

    @GetMapping("/api/chat/rooms")
    public List<ChatRoomResponse> chatRooms(@AuthenticationPrincipal MemberContext memberContext) {

        Member loginMember = memberContext.getMember();

        List<ChatRoom> findAllByLoginMember = chatRoomService.findAllByLoginMember(loginMember.getId());

        List<ChatRoomResponse> collect = findAllByLoginMember.stream().map(
                f -> ChatRoomResponse.builder()
                        .chatRoomId(f.getId())
                        .title(f.getTitle())
                        .receiverNickname(getOtherParticipationNickname(f, loginMember))
                        .receiverId(getOtherParticipationId(f, loginMember))
                        .build()
        ).collect(Collectors.toList());

        return collect;
    }
    @GetMapping("/api/chat/room")
    public ChatRoomResponse chatRoom(@RequestParam(name = "receiverId") Long receiverId,
                                     @AuthenticationPrincipal MemberContext memberContext) {

        Member loginMember = memberContext.getMember();

        ChatRoom findByLoginMemberIdWithReceiverId = chatRoomService.findByLoginMemberIdWithReceiverId(loginMember.getId(), receiverId);

        if (findByLoginMemberIdWithReceiverId == null) {
            Long savedChatRoom = chatRoomService.saveChatRoom(loginMember.getId() + "with" + receiverId, loginMember.getId(), receiverId);
            findByLoginMemberIdWithReceiverId = chatRoomService.findById(savedChatRoom);
        }

        return ChatRoomResponse.builder()
                .chatRoomId(findByLoginMemberIdWithReceiverId.getId())
                .myNickname(memberContext.getMember().getNickname())
                .build();
    }

    @GetMapping("/api/chat/history")
    public List<ChatHistoryDto> chatHistory(@RequestParam("receiverId") Long receiverId,
                                      @AuthenticationPrincipal MemberContext memberContext) {

        Member loginMember = memberContext.getMember();

        List<Chat> chatHistory = chatService.findChatHistory(loginMember.getId(), receiverId);

        List<ChatHistoryDto> collect = chatHistory.stream().map(
                c -> ChatHistoryDto.builder()
                        .senderNickname(c.getSender().getNickname())
                        .receiverNickname(c.getReceiver().getNickname())
                        .message(c.getContent())
                        .build()
        ).collect(Collectors.toList());

        return collect;
    }

    private String getOtherParticipationNickname(ChatRoom chatRoom, Member loginMember) {
        if (chatRoom.getFirstParticipation().getId().equals(loginMember.getId())) {
            return chatRoom.getSecondParticipation().getNickname();
        } else if (chatRoom.getSecondParticipation().getId().equals(loginMember.getId())) {
            return chatRoom.getFirstParticipation().getNickname();
        } else {
            throw new IllegalArgumentException("no participation");
        }
    }

    private Long getOtherParticipationId(ChatRoom chatRoom, Member loginMember) {
        if (chatRoom.getFirstParticipation().getId().equals(loginMember.getId())) {
            return chatRoom.getSecondParticipation().getId();
        } else if (chatRoom.getSecondParticipation().getId().equals(loginMember.getId())) {
            return chatRoom.getFirstParticipation().getId();
        } else {
            throw new IllegalArgumentException("no participation");
        }
    }
}
