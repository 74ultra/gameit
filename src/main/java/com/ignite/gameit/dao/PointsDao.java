package com.ignite.gameit.dao;

import com.ignite.gameit.domain.UserPoints;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PointsDao extends JpaRepository<UserPoints, Integer> {

    Optional<UserPoints> findByUserIdAndGameId(@Param(value = "userId") Integer userId, @Param(value = "gameId") Integer gameId);
}
