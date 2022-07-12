package com.ignite.gameit.dto.game;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GameStatusDto {

    private Integer gameId;

    private Integer orgId;

    private String startDate;

    private String endDate;
}
