package com.ignite.gameit.dto.org;

import com.ignite.gameit.utils.AbstractResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class OrgResponseDto extends AbstractResponse {

    private Integer id;

    private String orgName;

    private Integer numGames;

    private Integer numUsers;

    private String createdDate;

    private boolean isActive;

    private String adminEmail;

    private String orgCode;
}
