package com.ignite.gameit.dto.user;

import com.ignite.gameit.utils.AbstractResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse extends AbstractResponse {

    private Integer userId;

    private Integer orgId;

    private String firstName;

    private String lastName;

    private String email;

    private String deptId;

    private String jobId;

    private boolean isActive;

    private Date createdDate;
}
