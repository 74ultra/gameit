package com.ignite.gameit.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserReqDto {

    private Integer orgId;

    private String firstName;

    private String lastName;

    private String email;

    private String deptId;

    private String jobId;

}
