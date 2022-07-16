package com.ignite.gameit.dto.action;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TopGameUsersReq {

    private Integer gameId;

    private Integer orgId;
}
