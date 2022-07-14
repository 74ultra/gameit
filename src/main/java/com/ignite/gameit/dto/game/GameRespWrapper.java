package com.ignite.gameit.dto.game;

import com.ignite.gameit.utils.AbstractResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GameRespWrapper extends AbstractResponse {

    private List<GameRespDto> gamesList;
}
