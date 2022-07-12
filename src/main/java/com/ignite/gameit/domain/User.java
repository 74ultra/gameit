package com.ignite.gameit.domain;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "User")
@Table(name = "user")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(name = "org_id")
    @NotNull
    private Integer orgId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    @NotNull
    private String email;

    @Column(name = "dept_id")
    private String deptId;

    @Column(name = "job_id")
    private String jobId;

    // Use service to default to active
    @Column(name = "is_active")
    private boolean isActive;

    @Column(name = "create_date")
    @CreationTimestamp
    private Date createdDate;
}
