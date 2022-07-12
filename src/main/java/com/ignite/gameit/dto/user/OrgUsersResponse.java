package com.ignite.gameit.dto.user;

import com.ignite.gameit.utils.AbstractResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrgUsersResponse extends AbstractResponse {

    List<UserResponse> userResponseList;
}
