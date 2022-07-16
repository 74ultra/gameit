package com.ignite.gameit.service;

import com.ignite.gameit.dao.*;
import com.ignite.gameit.domain.*;
import com.ignite.gameit.dto.action.*;
import com.ignite.gameit.utils.AbstractResponse;
import com.ignite.gameit.utils.ResponseStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ActionService {

    @Autowired
    private ActionDao actionDao;

    @Autowired
    private GameDao gameDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserActionDao userActionDao;

    @Autowired
    private PointsDao pointsDao;

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
                else {
                    List<ActionDto> actionDtoList = gameActions.stream().map(a -> ActionDto.builder().actionId(a.getId()).gameId(a.getGameId())
                            .actionName(a.getActionName()).actionDesc(a.getActionDesc())
                            .pointValue(a.getValue()).build()
                    ).collect(Collectors.toList());

                    ActionsListWrapper actionsListWrapper  = new ActionsListWrapper(actionDtoList);

                    responseEntity = new ResponseEntity(actionsListWrapper, HttpStatus.OK);
                }
            }
        }
        else {
            responseEntity = new ResponseEntity("Game with id " + gameId + " was not found", HttpStatus.NOT_FOUND);
        }
        return responseEntity;
    }


    public ResponseEntity<? extends AbstractResponse> logPoints(PointsRequest pointsRequest){
        ResponseEntity responseEntity = null;
        Optional<User> userOpt = userDao.findById(pointsRequest.getUserId());
        Optional<Action> actionOpt = actionDao.findById(pointsRequest.getActionId());

        if(userOpt.isEmpty()){
            responseEntity = new ResponseEntity<>(new ResponseStatus("User not found"), HttpStatus.NOT_FOUND);
        }
        else if (actionOpt.isEmpty()){
            responseEntity = new ResponseEntity<>(new ResponseStatus("Action not found"), HttpStatus.NOT_FOUND);
        }
        else {
            User user = userOpt.get();
            Action action = actionOpt.get();
            Game game = gameDao.findById(action.getGameId()).get();
            if(!Objects.equals(user.getOrgId(), game.getOrgId())){
                responseEntity = new ResponseEntity<>(new ResponseStatus("Game not found"), HttpStatus.NOT_FOUND);
            }
            else {
                UserAction userAction = UserAction.builder().userId(user.getId()).actionId(action.getId())
                        .pointValue(action.getValue()).build();

                try {
                    UserAction savedAction = userActionDao.save(userAction);
                    Integer actionPoints = savedAction.getPointValue();
                    Optional<UserPoints> userPointsOpt = pointsDao.findByUserIdAndGameId(user.getId(), action.getGameId());
                    if(userPointsOpt.isPresent()){
                        UserPoints userPoints = userPointsOpt.get();
                        userPoints.setGamePoints(actionPoints + userPoints.getGamePoints());
                        pointsDao.save(userPoints);
                    }
                    else {
                        UserPoints userPoints = UserPoints.builder().gameId(game.getId()).orgId(game.getOrgId()).userId(user.getId())
                                .gamePoints(actionPoints).build();
                        UserPoints savedPoints = pointsDao.save(userPoints);

                    }
                    responseEntity = new ResponseEntity<>(new ResponseStatus("Action recorded"), HttpStatus.CREATED);
                }
                catch (Exception e){
                    responseEntity = new ResponseEntity<>(new ResponseStatus("There was an error logging the user action"), HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        }
        return responseEntity;
    }

    public ResponseEntity<? extends AbstractResponse> getUserPoints(Integer userId, Integer gameId){
        ResponseEntity responseEntity = null;
        Optional<UserPoints> userPointsOpt = pointsDao.findByUserIdAndGameId(userId, gameId);

        if(userPointsOpt.isPresent()){
            UserPoints userPoints = userPointsOpt.get();
            UserPointsDto userPointsDto = mapToUserPointsDto(userPoints);

            responseEntity = new ResponseEntity(userPointsDto, HttpStatus.OK);
        }
        else {
            responseEntity = new ResponseEntity(new ResponseStatus("User has not logged any points for this game"), HttpStatus.NOT_FOUND);
        }
        return responseEntity;
    }

    public ResponseEntity<? extends AbstractResponse> findTopUsers(TopGameUsersReq topGameUsersReq, Integer numResults){
        ResponseEntity responseEntity = null;
        Integer gameId = topGameUsersReq.getGameId();
        Integer orgId = topGameUsersReq.getOrgId();
        Integer res = numResults;

        if(numResults == null || numResults <= 0){
            Integer numUsers = userDao.findNumUsersByOrgId(orgId);
            numResults = numUsers;
        }

        List<UserPoints> topUserPointsList = pointsDao.findTopUsersByGameIdAndOrgId(gameId, orgId, numResults);
        if(!topUserPointsList.isEmpty()){
            List<UserInfoPointsDto> topUsersDtoList = topUserPointsList.stream().map(up -> mapToUserInfoPtsDto(up)).collect(Collectors.toList());
            responseEntity = new ResponseEntity<>(new UserPointsListWrapper(topUsersDtoList), HttpStatus.OK);
        }
        else responseEntity = new ResponseEntity<>(new ResponseStatus("No users found for organization id: " + orgId), HttpStatus.NOT_FOUND);

        return responseEntity;
    }

    private UserInfoPointsDto mapToUserInfoPtsDto(UserPoints userPoints){
        Optional<User> userOpt = userDao.findById(userPoints.getUserId());
        if(userOpt.isPresent()){
            User user = userOpt.get();
            return UserInfoPointsDto.builder().userId(userPoints.getUserId())
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .email(user.getEmail())
                    .deptId(user.getDeptId())
                    .jobId(user.getJobId())
                    .gameId(userPoints.getGameId())
                    .gamePoints(userPoints.getGamePoints())
                    .dateLastUpdated(dateToString(userPoints.getLastUpdatedDate()))
                    .build();
        }
        else return new UserInfoPointsDto();
    }


    private UserPointsDto mapToUserPointsDto(UserPoints userPoints){
        return UserPointsDto.builder().userId(userPoints.getUserId()).gameId(userPoints.getGameId()).orgId(userPoints.getOrgId())
                .gamePoints(userPoints.getGamePoints())
                .lastUpdated(dateToString(userPoints.getLastUpdatedDate()))
                .build();
    }

    private String dateToString(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
        String dateString = sdf.format(date);
        return dateString;
    }
}
