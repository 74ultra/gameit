package com.ignite.gameit.dao;

import com.ignite.gameit.domain.UserAction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserActionDao extends JpaRepository<UserAction, Integer> {
}
