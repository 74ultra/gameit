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
public class Game {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(name = "org_id")
    @NotNull
    private Integer orgId;

    @Column(name = "title")
    @NotNull
    private String title;

    @Column(name = "created_date")
    @CreationTimestamp
    private Date createdDate;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "description")
    private String desc;

    // Use service to default to active
    @Column(name = "is_active")
    private boolean isActive;

    public Integer getOrgId() {
        return orgId;
    }
}
