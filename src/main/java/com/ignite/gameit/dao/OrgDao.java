package com.ignite.gameit.dao;

import com.ignite.gameit.domain.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface OrgDao extends JpaRepository<Organization, Integer> {

    @Query(nativeQuery = true, value = "SELECT * FROM organization WHERE org_name = :orgName")
    Optional<Organization> findByOrgName(@Param(value = "orgName") String orgName);

}
