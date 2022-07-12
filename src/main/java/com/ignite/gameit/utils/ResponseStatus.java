package com.ignite.gameit.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseStatus extends AbstractResponse{
    private String status;
}
