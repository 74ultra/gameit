package com.ignite.gameit.dto.action;

import com.ignite.gameit.utils.AbstractResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserPointsListWrapper extends AbstractResponse {

    List<UserInfoPointsDto> userPointsList;
}
