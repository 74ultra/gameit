package com.ignite.gameit.dto.action;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserInfoPointsDto {

    private Integer userId;

    private String firstName;

    private String lastName;

    private String email;

    private String deptId;

    private String jobId;

    private Integer gameId;

    private Integer gamePoints;

    private String dateLastUpdated;
}
