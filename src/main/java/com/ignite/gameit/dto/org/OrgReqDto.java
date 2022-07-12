package com.ignite.gameit.dto.org;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class OrgReqDto {

    private String orgName;

    private String adminEmail;
}
