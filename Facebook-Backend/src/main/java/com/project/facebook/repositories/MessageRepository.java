package com.project.facebook.repositories;

import com.project.facebook.models.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    @Query("SELECT m FROM Message m WHERE m.conversation.id = :conversationId")
    List<Message> getMessagesByConversationId(@Param("conversationId") Long conversationId);

}
