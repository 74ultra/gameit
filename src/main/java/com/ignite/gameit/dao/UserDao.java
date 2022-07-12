package com.ignite.gameit.dao;

import com.ignite.gameit.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserDao extends JpaRepository<User, Integer> {

    @Query(nativeQuery = true, value = "SELECT * FROM gameit.user WHERE org_id = :orgId")
    List<User> findAllByOrgId(@Param(value = "orgId") Integer orgId);

    @Query(nativeQuery = true, value = "SELECT * FROM user WHERE email = :email AND org_id = :orgId")
    Optional<User> findByEmailAndOrgId(@Param(value = "email") String email, @Param(value = "orgId") Integer orgId);
}
