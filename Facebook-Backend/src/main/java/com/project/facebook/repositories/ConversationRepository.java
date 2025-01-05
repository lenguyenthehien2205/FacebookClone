package com.project.facebook.repositories;

import com.project.facebook.models.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ConversationRepository extends JpaRepository<Conversation, Long> {
    @Query(value = "SELECT * FROM conversations c " +
            "WHERE (c.person1 = :person1 AND c.person2 = :person2) " +
            "   OR (c.person2 = :person1 AND c.person1 = :person2)",
            nativeQuery = true)
    Optional<Conversation> findConversationByUsers(@Param("person1") Long person1, @Param("person2") Long person2);

}
