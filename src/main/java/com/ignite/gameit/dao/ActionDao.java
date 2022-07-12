package com.ignite.gameit.dao;

import com.ignite.gameit.domain.Action;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActionDao extends JpaRepository<Action, Integer> {
}
