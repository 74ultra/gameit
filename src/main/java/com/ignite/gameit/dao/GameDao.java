package com.ignite.gameit.dao;

import com.ignite.gameit.domain.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GameDao extends JpaRepository<Game, Integer> {

    @Query(nativeQuery = true, value = "SELECT * FROM game WHERE title = :gameTitle AND org_id = :orgId")
    Optional<Game> findByGameTitleAndOrgId(@Param(value = "gameTitle") String gameTitle, @Param(value = "orgId") Integer orgId);
}
