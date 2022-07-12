package com.ignite.gameit.domain;


import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "Organization")
@Table(name = "organization")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Organization {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(name = "org_name")
    @NotNull
    private String orgName;

    @Column(name = "num_games")
    private Integer numGames;

    @Column(name = "num_users")
    private Integer numUsers;

    @Column(name = "created_date")
    @CreationTimestamp
    private Date createdDate;

    // Use service to default to active
    @Column(name = "is_active")
    private boolean isActive;

    @Column(name = "admin_email")
    @NotNull
    private String adminEmail;

    @Column(name = "org_code")
    private String orgCode;
}
