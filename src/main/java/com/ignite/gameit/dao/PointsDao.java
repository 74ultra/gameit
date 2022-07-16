package com.ignite.gameit.dao;

import com.ignite.gameit.domain.UserPoints;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PointsDao extends JpaRepository<UserPoints, Integer> {

    Optional<UserPoints> findByUserIdAndGameId(@Param(value = "userId") Integer userId, @Param(value = "gameId") Integer gameId);

    @Query(nativeQuery = true, value = "SELECT * FROM user_points WHERE org_id = :orgId AND game_id = :gameId ORDER BY game_points DESC LIMIT :numResults")
    List<UserPoints> findTopUsersByGameIdAndOrgId(@Param(value = "gameId") Integer gameId, @Param(value = "orgId") Integer orgId, @Param(value = "numResults") Integer numResults);

    @Query(nativeQuery = true, value = "SELECT u.id, u.first_name, u.last_name, u.email, u.dept_id, u.job_id, up.game_id, up.game_points, up.last_updated FROM user u JOIN user_points up ON u.id = up.user_id WHERE u.id = :userId")
    Object[] findUserInfoAndPointsById(@Param(value = "userId") Integer userId);
}
