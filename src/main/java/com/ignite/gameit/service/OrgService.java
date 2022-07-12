package com.ignite.gameit.service;

import com.ignite.gameit.dao.OrgDao;
import com.ignite.gameit.domain.Organization;
import com.ignite.gameit.dto.org.OrgReqDto;
import com.ignite.gameit.dto.org.OrgResponseDto;
import com.ignite.gameit.utils.AbstractResponse;
import com.ignite.gameit.utils.ResponseStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class OrgService {

    @Autowired
    private OrgDao orgDao;

    public ResponseEntity<? extends AbstractResponse> addOrg(OrgReqDto orgReqDto){

        ResponseEntity responseEntity = null;

        Optional<Organization> orgOpt = orgDao.findByOrgName(orgReqDto.getOrgName());

        if(orgOpt.isPresent()){
            responseEntity = new ResponseEntity<>(new ResponseStatus("An organization with the name " + orgReqDto.getOrgName() + " already exists"), HttpStatus.BAD_REQUEST);
        }
        else{
            try{
                UUID uuid = UUID.randomUUID();
                Organization newOrg = Organization.builder().orgName(orgReqDto.getOrgName())
                        .adminEmail(orgReqDto.getAdminEmail())
                        .numGames(0).numUsers(0)
                        .isActive(true)
                        .orgCode(uuid.toString())
                        .build();

                Organization savedOrg = orgDao.save(newOrg);
                responseEntity = new ResponseEntity<>(new ResponseStatus("Organization saved with id " + savedOrg.getId()), HttpStatus.CREATED);
            } catch (Exception e){
                responseEntity = new ResponseEntity<>(new ResponseStatus("An error occurred. Organization was not saved"), HttpStatus.INTERNAL_SERVER_ERROR);
            }

        }
        return responseEntity;
    }

    public ResponseEntity<? extends AbstractResponse> getOrg(Integer orgId){
        ResponseEntity responseEntity = null;
        Optional<Organization> orgOpt = orgDao.findById(orgId);
        if(orgOpt.isPresent()){
            Organization org = orgOpt.get();
            OrgResponseDto orgResponseDto = OrgResponseDto.builder().id(org.getId()).orgName(org.getOrgName())
                    .numGames(org.getNumGames()).numUsers(org.getNumUsers())
                    .createdDate(org.getCreatedDate().toString())
                    .isActive(org.isActive())
                    .adminEmail(org.getAdminEmail())
                    .orgCode(org.getOrgCode()).build();

            responseEntity = new ResponseEntity<>(orgResponseDto, HttpStatus.OK);
        }
        else {
            responseEntity = new ResponseEntity<>(new ResponseStatus("An organization with id " + orgId + " not found"), HttpStatus.NOT_FOUND);
        }
        return responseEntity;
    }

}
