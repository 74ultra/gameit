package com.ignite.gameit.service;

import com.ignite.gameit.dao.ActionDao;
import com.ignite.gameit.dao.GameDao;
import com.ignite.gameit.dao.OrgDao;
import com.ignite.gameit.domain.Action;
import com.ignite.gameit.domain.Game;
import com.ignite.gameit.domain.Organization;
import com.ignite.gameit.dto.game.GameReqDto;
import com.ignite.gameit.dto.game.GameRespDto;
import com.ignite.gameit.dto.game.GameRespWrapper;
import com.ignite.gameit.dto.game.GameStatusDto;
import com.ignite.gameit.utils.AbstractResponse;
import com.ignite.gameit.utils.ResponseStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GameService {

    @Autowired
    private GameDao gameDao;

    @Autowired
    private ActionDao actionDao;

    @Autowired
    private OrgDao orgDao;

    public ResponseEntity<? extends AbstractResponse> addGame(GameReqDto gameReqDto){
        ResponseEntity responseEntity = null;

        Optional<Organization> orgOpt = orgDao.findById(gameReqDto.getOrgId());
        Optional<Game> gameOpt = gameDao.findByGameTitleAndOrgId(gameReqDto.getTitle(), gameReqDto.getOrgId());
        if(!orgOpt.isPresent()){
            responseEntity = new ResponseEntity<>(new ResponseStatus("An organization with id " + gameReqDto.getOrgId() + " not found"), HttpStatus.NOT_FOUND);
        }
        else if(gameOpt.isPresent()){
            responseEntity = new ResponseEntity<>(new ResponseStatus("A game with that title already exists for your organization"), HttpStatus.BAD_REQUEST);
        }
        else{
            try {
                Game newGame = Game.builder().orgId(gameReqDto.getOrgId()).title(gameReqDto.getTitle())
                        .desc(gameReqDto.getDesc()).isActive(false).build();

                Game savedGame = gameDao.save(newGame);
                Organization org = orgDao.findById(savedGame.getOrgId()).get();
                org.setNumGames(org.getNumGames() + 1);
                orgDao.save(org);
                responseEntity = new ResponseEntity(new ResponseStatus("Game created with id of " + savedGame.getId()), HttpStatus.CREATED);

            } catch (Exception e){
                responseEntity = new ResponseEntity(new ResponseStatus("There was a problem creating the game: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return responseEntity;
    }

    public ResponseEntity<? extends AbstractResponse> getGame(Integer gameId, Integer orgId){
        ResponseEntity responseEntity = null;

        try{
            Optional<Game> gameOpt = gameDao.findById(gameId);
            if(gameOpt.isEmpty()){
                responseEntity = new ResponseEntity(new ResponseStatus("No game found with id " + gameId), HttpStatus.NOT_FOUND);
            }
            else if (gameOpt.get().getOrgId() != orgId) {
                responseEntity = new ResponseEntity(new ResponseStatus("Game with id " + gameId + " is not associated with your organization"), HttpStatus.NOT_FOUND);
            }
            else {
                Game game = gameOpt.get();
                GameRespDto gameRespDto = GameRespDto.builder().gameId(game.getId()).orgId(game.getOrgId())
                        .title(game.getTitle()).createdDate(game.getCreatedDate() == null ? null : game.getCreatedDate().toString())
                        .startDate(game.getStartDate() == null ? null : game.getStartDate().toString())
                        .endDate(game.getEndDate() == null ? null : game.getEndDate().toString())
                        .desc(game.getDesc()).isActive(game.isActive()).build();

                responseEntity = new ResponseEntity(gameRespDto, HttpStatus.OK);
            }
        } catch (Exception e){
            responseEntity = new ResponseEntity(new ResponseStatus("There was a problem retrieving the game: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }


    public ResponseEntity<? extends AbstractResponse> activateGame(GameStatusDto gameStatusDto){
        ResponseEntity responseEntity = null;
        try {
            Optional<Game> gameOpt = gameDao.findById(gameStatusDto.getGameId());
            List<Action> gameActions = actionDao.findGameActions(gameStatusDto.getGameId());

            if(gameOpt.isEmpty()){
                responseEntity = new ResponseEntity(new ResponseStatus("No game found with id " + gameStatusDto.getGameId()), HttpStatus.NOT_FOUND);
            }
            else if (gameOpt.get().getOrgId() != gameStatusDto.getOrgId()) {
                responseEntity = new ResponseEntity(new ResponseStatus("No game with id " + gameStatusDto.getGameId() + " is associated with your organization"), HttpStatus.NOT_FOUND);
            }
            else if (gameOpt.get().getStartDate() != null){
                responseEntity = new ResponseEntity(new ResponseStatus("Game with id " + gameStatusDto.getGameId() + " is already active"), HttpStatus.BAD_REQUEST);
            }
            else if(gameActions.isEmpty()){
                responseEntity = new ResponseEntity(new ResponseStatus("Game with id " + gameStatusDto.getGameId() + " must have at least one associated action before it can be activated"), HttpStatus.BAD_REQUEST);
            }
            else {
                Game game = gameOpt.get();
                SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
                game.setStartDate(sdf.parse(gameStatusDto.getStartDate()));
                game.setEndDate(sdf.parse(gameStatusDto.getEndDate()));
                game.setActive(true);
                gameDao.save(game);
                responseEntity = new ResponseEntity(new ResponseStatus("Game with id " + game.getId() + " activated"), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            responseEntity = new ResponseEntity(new ResponseStatus("There was a problem updating the game status: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    public ResponseEntity<? extends AbstractResponse> getOrgGames(Integer orgId){
        ResponseEntity responseEntity = null;
        try{
            List<Game> allGames = gameDao.findAll();
            List<Game> gamesList = allGames.stream().filter(g -> orgId.equals(g.getOrgId())).collect(Collectors.toList());
            if(!gamesList.isEmpty()){
                List<GameRespDto> gamesDtoList = gamesList.stream().map(g -> GameRespDto.builder().gameId(g.getId())
                        .orgId(g.getOrgId()).title(g.getTitle())
                        .startDate(g.getStartDate() == null ? null : dateToString(g.getStartDate()))
                        .endDate(g.getEndDate() == null ? null : dateToString(g.getEndDate()))
                        .createdDate(dateToString(g.getCreatedDate()))
                        .desc(g.getDesc()).isActive(g.isActive()).build()
                ).collect(Collectors.toList());

                responseEntity = new ResponseEntity(new GameRespWrapper(gamesDtoList), HttpStatus.OK);
            }
            else{
                responseEntity = new ResponseEntity(new ResponseStatus("No games found for org with id " + orgId), HttpStatus.NOT_FOUND);
            }
        }
        catch (Exception e){
            responseEntity = new ResponseEntity(new ResponseStatus("There was a problem retrieving games: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    private String dateToString(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
        String dateString = sdf.format(date);
        return dateString;
    }

}
