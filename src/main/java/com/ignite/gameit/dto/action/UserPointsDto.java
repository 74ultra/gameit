package com.ignite.gameit.dto.action;

import com.ignite.gameit.utils.AbstractResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserPointsDto extends AbstractResponse {

    private Integer userId;

    private Integer gameId;

    private Integer orgId;

    private Integer gamePoints;

    private String lastUpdated;
}
