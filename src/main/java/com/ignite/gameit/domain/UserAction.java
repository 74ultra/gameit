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
public class UserAction {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(name = "user_id")
    @NotNull
    private Integer userId;

    @Column(name = "action_id")
    @NotNull
    private Integer actionId;

    @Column(name = "point_value")
    @NotNull
    private Integer pointValue;

    @Column(name = "created_date")
    @CreationTimestamp
    private Date lastUpdatedDate;

}
