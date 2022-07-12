package com.ignite.gameit.service;

import com.ignite.gameit.dao.OrgDao;
import com.ignite.gameit.dao.UserDao;
import com.ignite.gameit.domain.Organization;
import com.ignite.gameit.domain.User;
import com.ignite.gameit.dto.user.OrgUsersResponse;
import com.ignite.gameit.dto.user.UserReqDto;
import com.ignite.gameit.dto.user.UserResponse;
import com.ignite.gameit.utils.AbstractResponse;
import com.ignite.gameit.utils.ResponseStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private OrgDao orgDao;

    public ResponseEntity<? extends AbstractResponse> addNewUser(UserReqDto userReqDto){
        ResponseEntity responseEntity = null;

        Optional<Organization> orgOpt = orgDao.findById(userReqDto.getOrgId());
        Optional<User> userOpt = userDao.findByEmailAndOrgId(userReqDto.getEmail(), userReqDto.getOrgId());
        if(orgOpt.isEmpty()){
            responseEntity = new ResponseEntity(new ResponseStatus("An organization with id " + userReqDto.getOrgId() + " is not registered"), HttpStatus.NOT_FOUND);
        }
        else if(userOpt.isPresent()){
            responseEntity = new ResponseEntity(new ResponseStatus("A user associated with this organization with email " + userOpt.get().getEmail() + " already exists"), HttpStatus.BAD_REQUEST);
        }
        else{
            System.out.println(userReqDto.toString());
            try {
                User newUser = User.builder().orgId(userReqDto.getOrgId()).firstName(userReqDto.getFirstName())
                        .lastName(userReqDto.getLastName()).email(userReqDto.getEmail())
                        .deptId(userReqDto.getDeptId()).jobId(userReqDto.getJobId())
                        .isActive(true)
                        .build();

                System.out.println(newUser.toString());
                User savedUser = userDao.save(newUser);

                Organization org = orgDao.findById(savedUser.getOrgId()).get();
                org.setNumUsers(org.getNumUsers() + 1);
                orgDao.save(org);

                responseEntity = new ResponseEntity(new ResponseStatus("User saved with id " + savedUser.getId()), HttpStatus.CREATED);
            } catch (Exception e){
                responseEntity = new ResponseEntity(new ResponseStatus("There was an error saving the user: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return responseEntity;
    }

    public ResponseEntity<? extends AbstractResponse> getUser(Integer userId){
        Optional<User> userOpt = userDao.findById(userId);
        ResponseEntity responseEntity = null;

        // Add date conversion

        if(userOpt.isPresent()){
            User user = userOpt.get();
            UserResponse userResponse = mapToUserResponse(user);
            responseEntity = new ResponseEntity(userResponse, HttpStatus.OK);
        }
        else{
            responseEntity = new ResponseEntity("User with id " + userId + "not found", HttpStatus.NOT_FOUND);
        }
        return responseEntity;
    }

    public ResponseEntity<? extends AbstractResponse> getAllUsersByOrg(Integer orgId){
        List<User> orgUsersList = userDao.findAllByOrgId(orgId);
        ResponseEntity responseEntity = null;
        if(!orgUsersList.isEmpty()){
            List<UserResponse> orgUserResponseList = orgUsersList.stream().map(this::mapToUserResponse).collect(Collectors.toList());
            responseEntity = new ResponseEntity<>(new OrgUsersResponse(orgUserResponseList), HttpStatus.OK);
        }
        else responseEntity = new ResponseEntity<>(new ResponseStatus("No users found for organization id " + orgId), HttpStatus.NOT_FOUND);

        return responseEntity;
    }

    private UserResponse mapToUserResponse(User user){
        UserResponse userResponse = UserResponse.builder().userId(user.getId()).orgId(user.getOrgId())
                .firstName(user.getFirstName()).lastName(user.getLastName())
                .email(user.getEmail()).deptId(user.getDeptId())
                .jobId(user.getJobId()).isActive(user.isActive())
                .createdDate(user.getCreatedDate()).build();

        return userResponse;
    }
}
