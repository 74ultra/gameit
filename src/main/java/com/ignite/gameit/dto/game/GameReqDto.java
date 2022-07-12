package com.ignite.gameit.dto.game;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GameReqDto {

    private Integer orgId;

    private String title;

    private String desc;

}
