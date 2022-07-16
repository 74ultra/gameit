package com.ignite.gameit.dto.action;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PointsRequest {

    private Integer actionId;

    private Integer userId;
}
