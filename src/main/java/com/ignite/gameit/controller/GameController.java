package com.ignite.gameit.controller;

import com.ignite.gameit.dto.game.GameReqDto;
import com.ignite.gameit.dto.game.GameStatusDto;
import com.ignite.gameit.service.GameService;
import com.ignite.gameit.utils.AbstractResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/game")
@AllArgsConstructor
public class GameController {

    @Autowired
    private GameService gameService;

    @PostMapping
    public ResponseEntity<? extends AbstractResponse> createGame(@RequestBody GameReqDto gameReqDto){
        return gameService.addGame(gameReqDto);
    }

    @GetMapping(value = "/{gameId}/{orgId}")
    public ResponseEntity<? extends AbstractResponse> getGame(@PathVariable(name = "gameId") Integer gameId, @PathVariable(name = "orgId") Integer orgId){
        return gameService.getGame(gameId, orgId);
    }

    @GetMapping(value = "/{orgId}")
    public ResponseEntity<? extends AbstractResponse> getAllOrgGames(@PathVariable(name = "orgId") Integer orgId){
        return gameService.getOrgGames(orgId);
    }

    @PutMapping(value = "/activate")
    public ResponseEntity<? extends AbstractResponse> activateGame(@RequestBody GameStatusDto gameStatusDto){
        return gameService.activateGame(gameStatusDto);
    }
}
