package com.ignite.gameit.dto.org;

import com.ignite.gameit.utils.AbstractResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrgListWrapper extends AbstractResponse {

    List<OrgResponseDto> organizationList;
}
