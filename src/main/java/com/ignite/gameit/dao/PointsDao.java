package com.ignite.gameit.dao;

import com.ignite.gameit.domain.UserPoints;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointsDao extends JpaRepository<UserPoints, Integer> {
}
