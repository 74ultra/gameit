package com.ignite.gameit.domain;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Action {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(name = "game_id")
    @NotNull
    private Integer gameId;

    @Column(name = "action_name")
    @NotNull
    private String actionName;

    @Column(name = "action_description")
    private String actionDesc;

    @Column(name = "point_value")
    private Integer value;

    @Column(name = "created_date")
    @CreationTimestamp
    private Date createdDate;
}
