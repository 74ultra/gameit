package com.ignite.gameit.domain;


import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserPoints {

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "game_id")
    @NotNull
    private Integer gameId;

    @Column(name = "org_id")
    @NotNull
    private Integer orgId;

    @Column(name = "user_id")
    @NotNull
    private Integer userId;

    @Column(name = "game_points")
    private Integer gamePoints;
}
