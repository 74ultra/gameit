package com.ignite.gameit.controller;

import com.ignite.gameit.dto.org.OrgReqDto;
import com.ignite.gameit.service.OrgService;
import com.ignite.gameit.utils.AbstractResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/org")
@AllArgsConstructor
public class OrgController {

    @Autowired
    private OrgService orgService;

    @PostMapping
    public ResponseEntity<? extends AbstractResponse> addOrg(@RequestBody OrgReqDto orgReqDto){
        return orgService.addOrg(orgReqDto);
    }

    @GetMapping(value = "/{orgId}")
    public ResponseEntity<? extends AbstractResponse> getOrg(@PathVariable(name = "orgId") Integer orgId){
        return orgService.getOrg(orgId);
    }

    @GetMapping(value = "/all")
    public ResponseEntity<? extends AbstractResponse> getAllOrgs(){
        return orgService.getAllOrgs();
    }
}
