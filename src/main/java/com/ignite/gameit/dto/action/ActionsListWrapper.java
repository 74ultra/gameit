package com.ignite.gameit.dto.action;

import com.ignite.gameit.utils.AbstractResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ActionsListWrapper extends AbstractResponse {

    private List<ActionDto> actionsList;
}
