package com.ignite.gameit.dao;

import com.ignite.gameit.domain.Action;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ActionDao extends JpaRepository<Action, Integer> {

    @Query(nativeQuery = true, value = "SELECT * FROM action WHERE game_id = :game_id")
    List<Action> findGameActions(@Param(value = "game_id") Integer gameId);
}
