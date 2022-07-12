package com.ignite.gameit.dto.game;

import com.ignite.gameit.utils.AbstractResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GameRespDto extends AbstractResponse {

    private Integer gameId;

    private Integer orgId;

    private String title;

    private String createdDate;

    private String startDate;

    private String endDate;

    private String desc;

    private boolean isActive;
}
