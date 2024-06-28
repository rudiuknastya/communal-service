package com.example.chairman.specification;

import com.example.chairman.entity.Chat;
import com.example.chairman.entity.ChatMessage;
import org.springframework.data.jpa.domain.Specification;

public interface ChatMessageSpecification {
    static Specification<ChatMessage> byChat(Chat chat){
        return (root, query, builder) ->
                builder.equal(root.get("chat"), chat);
    }
}
