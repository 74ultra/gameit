package com.ignite.gameit.domain;


import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "user_points")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserPoints {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
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

    @Column(name = "last_updated")
    @UpdateTimestamp
    private Date lastUpdatedDate;
}
