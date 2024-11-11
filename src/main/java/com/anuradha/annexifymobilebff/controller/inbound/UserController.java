package com.anuradha.annexifymobilebff.controller.inbound;

import com.anuradha.annexifymobilebff.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PostMapping
    public void saveUser(@RequestParam String idToken) {
        userService.saveUser(idToken);
    }

}
