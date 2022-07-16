package com.ignite.gameit.dao;

import com.ignite.gameit.domain.UserAction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserActionDao extends JpaRepository<UserAction, Integer> {

    @Query(nativeQuery = true, value = "SELECT * FROM user_action WHERE user_id = :userId")
    List<UserAction> findByUserId(@Param(value = "userId") Integer userId);

    @Query(nativeQuery = true, value = "SELECT * FROM user_action WHERE org_id = :orgId AND game_id = :gameId")
    List<UserAction> findByOrgIdAndByGameId(@Param(value = "orgId") Integer orgId, @Param(value = "gameId") Integer gameId);

    @Query(nativeQuery = true, value = "SELECT * FROM user_action WHERE user_id = :userId AND game_id = :gameId")
    List<UserAction> findByUserIdAndByGameId(@Param(value = "userId") Integer userId, @Param(value = "gameId") Integer gameId);
}
