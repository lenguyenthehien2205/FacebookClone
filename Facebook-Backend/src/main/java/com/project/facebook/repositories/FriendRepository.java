package com.project.facebook.repositories;

import com.project.facebook.models.Friend;
import com.project.facebook.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FriendRepository extends JpaRepository<Friend, Long> {

    @Query("SELECT COUNT(f) > 0 FROM Friend f " +
            "WHERE (f.firstUser.id = :firstUserId AND f.secondUser.id = :secondUserId) " +
            "OR (f.firstUser.id = :secondUserId AND f.secondUser.id = :firstUserId)")
    boolean existsFriendship(@Param("firstUserId") Long firstUserId, @Param("secondUserId") Long secondUserId);

    @Query("SELECT u FROM User u WHERE u.id IN (" +
            "SELECT CASE " +
            "WHEN f.firstUser.id = :userId THEN f.secondUser.id " +
            "WHEN f.secondUser.id = :userId THEN f.firstUser.id " +
            "END " +
            "FROM Friend f " +
            "WHERE f.firstUser.id = :userId OR f.secondUser.id = :userId)")
    List<User> findAllFriendsByUserId(@Param("userId") Long userId);
}
