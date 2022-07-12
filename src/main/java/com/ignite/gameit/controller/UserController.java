package com.ignite.gameit.controller;

import com.ignite.gameit.dto.user.UserReqDto;
import com.ignite.gameit.service.UserService;
import com.ignite.gameit.utils.AbstractResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/users")
@AllArgsConstructor
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<? extends AbstractResponse> addUser(@RequestBody UserReqDto userReqDto){
        return userService.addNewUser(userReqDto);
    }

    @GetMapping(value = "/{userId}")
    public ResponseEntity<? extends AbstractResponse> getUser(@PathVariable(name = "userId") Integer userId){
        return userService.getUser(userId);
    }

    @GetMapping(value = "/org/{orgId}")
    public ResponseEntity<? extends AbstractResponse> getUsersByOrgId(@PathVariable(name = "orgId") Integer orgId){
        return userService.getAllUsersByOrg(orgId);
    }
}
