package com.ignite.gameit.dto.action;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ActionReqDto {

    private Integer gameId;

    private String actionName;

    private String actionDesc;

    private Integer value;
}
