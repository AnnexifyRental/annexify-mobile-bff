package com.anuradha.annexifymobilebff.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenDataDto {

    @JsonProperty("cognito:username")
    private String username;
    private String email;
    private long exp;
    private long iat;
}
