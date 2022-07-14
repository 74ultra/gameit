package com.ignite.gameit.service;

import com.ignite.gameit.dao.ActionDao;
import com.ignite.gameit.dao.GameDao;
import com.ignite.gameit.domain.Action;
import com.ignite.gameit.domain.Game;
import com.ignite.gameit.dto.action.ActionDto;
import com.ignite.gameit.dto.action.ActionReqDto;
import com.ignite.gameit.dto.action.ActionsListWrapper;
import com.ignite.gameit.utils.AbstractResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ActionService {

    @Autowired
    private ActionDao actionDao;

    @Autowired
    private GameDao gameDao;

    public ResponseEntity<? extends AbstractResponse> createAction(Integer orgId, ActionReqDto actionReq){
        ResponseEntity responseEntity = null;
        // Check if game exists and is associated with org
        Optional<Game> gameOpt = gameDao.findById(actionReq.getGameId());
        if(gameOpt.isPresent()){
            Game game = gameOpt.get();
            if(game.getOrgId() != orgId){
                responseEntity = new ResponseEntity("Game with id " + actionReq.getGameId() + " was not found", HttpStatus.NOT_FOUND);
            }
            else{
                try {
                    Action action = Action.builder().gameId(actionReq.getGameId()).actionName(actionReq.getActionName())
                            .actionDesc(actionReq.getActionDesc()).value(actionReq.getValue()).build();

                    Action savedAction = actionDao.save(action);
                    responseEntity = new ResponseEntity("Action saved with id: "+ savedAction.getId(), HttpStatus.CREATED);
                } catch (Exception e) {
                    responseEntity = new ResponseEntity("There was an problem creating the action: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        }
        else {
            responseEntity = new ResponseEntity("Game with id " + actionReq.getGameId() + " not found", HttpStatus.NOT_FOUND);
        }
        return responseEntity;
    }

    public ResponseEntity<? extends AbstractResponse> getGameActions(Integer orgId, Integer gameId){
        ResponseEntity responseEntity = null;

        Optional<Game> gameOpt = gameDao.findById(gameId);
        if(gameOpt.isPresent()){
            Game game = gameOpt.get();
            if(game.getOrgId() != orgId){
                responseEntity = new ResponseEntity("Game with id " + gameId + " was not found", HttpStatus.NOT_FOUND);
            }
            else {
                List<Action> actionList = actionDao.findAll();
                System.out.println(actionList.toString());
                List<Action> gameActions = actionList.stream().filter(a -> a.getGameId().equals(gameId)).collect(Collectors.toList());
                if(gameActions.isEmpty()){
                    responseEntity = new ResponseEntity("No actions have been created for game with id " + gameId, HttpStatus.NOT_FOUND);
                }

                List<ActionDto> actionDtoList = gameActions.stream().map(a -> ActionDto.builder().actionId(a.getId()).gameId(a.getGameId())
                        .actionName(a.getActionName()).actionDesc(a.getActionDesc())
                        .pointValue(a.getValue()).build()
                ).collect(Collectors.toList());

                ActionsListWrapper actionsListWrapper  = new ActionsListWrapper(actionDtoList);

                responseEntity = new ResponseEntity(actionsListWrapper, HttpStatus.OK);
            }
        }
        else {
            responseEntity = new ResponseEntity("Game with id " + gameId + " was not found", HttpStatus.NOT_FOUND);
        }
        return responseEntity;
    }
}
