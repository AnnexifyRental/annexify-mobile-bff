package com.anuradha.annexifymobilebff.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class TokenDataDto {

    @JsonProperty("cognito:username")
    private String username;
    private String email;
    private long exp;
    private long iat;
}
