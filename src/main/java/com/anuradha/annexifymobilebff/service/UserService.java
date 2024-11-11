package com.anuradha.annexifymobilebff.service;

import com.anuradha.annexifymobilebff.controller.outbound.CentralServiceClient;
import com.anuradha.annexifymobilebff.dto.TokenDataDto;
import com.anuradha.annexifymobilebff.dto.UserSaveDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final CentralServiceClient centralServiceClient;
    private final JwtUtilService jwtUtilService;

    public void saveUser(String idToken) {
        TokenDataDto tokenData = jwtUtilService.decodeToken(idToken);
        jwtUtilService.validateToken(tokenData);

        centralServiceClient.saveUser(
                new UserSaveDto(tokenData.getUsername(), tokenData.getEmail())
        );
    }


}
