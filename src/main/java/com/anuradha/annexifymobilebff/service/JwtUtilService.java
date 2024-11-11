package com.anuradha.annexifymobilebff.service;

import com.anuradha.annexifymobilebff.dto.TokenDataDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Base64;

@Service
public class JwtUtilService {

    public TokenDataDto decodeToken(String token) {
        try {
            Base64.Decoder decoder = Base64.getUrlDecoder();
            String[] chunks = token.split("\\.");
            String payload = new String(decoder.decode(chunks[1]));
            return new ObjectMapper().readValue(payload, TokenDataDto.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Failed to decode token");
        }
    }

    public void validateToken(TokenDataDto tokenData) {

        long exp = tokenData.getExp();
        long currentTimeSeconds = System.currentTimeMillis() / 1000;

        if (exp < currentTimeSeconds) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token has expired");
        }
    }

}
