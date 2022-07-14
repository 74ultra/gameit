package com.ignite.gameit.controller;

import com.ignite.gameit.dto.action.ActionReqDto;
import com.ignite.gameit.service.ActionService;
import com.ignite.gameit.utils.AbstractResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/action")
@AllArgsConstructor
public class ActionController {

    @Autowired
    private ActionService actionService;

    @PostMapping(value = "/{orgId}")
    public ResponseEntity<? extends AbstractResponse> createAction(@PathVariable(name = "orgId") Integer orgId, @RequestBody ActionReqDto actionReq){
        return actionService.createAction(orgId, actionReq);
    }

    @GetMapping(value = "/{orgId}/{gameId}")
    public ResponseEntity<? extends AbstractResponse> getGameActions(@PathVariable(name = "orgId") Integer orgId, @PathVariable(name = "gameId") Integer gameId){
        return actionService.getGameActions(orgId, gameId);
    }
}
