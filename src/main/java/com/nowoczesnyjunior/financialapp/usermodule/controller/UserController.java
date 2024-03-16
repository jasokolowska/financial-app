package com.nowoczesnyjunior.financialapp.usermodule.controller;

import com.nowoczesnyjunior.financialapp.openapi.api.UserApi;
import com.nowoczesnyjunior.financialapp.openapi.model.CredentialDetailsDto;
import com.nowoczesnyjunior.financialapp.openapi.model.RegisterDetailsDto;
import com.nowoczesnyjunior.financialapp.openapi.model.TokenDto;
import com.nowoczesnyjunior.financialapp.openapi.model.UserDto;
import com.nowoczesnyjunior.financialapp.usermodule.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v1/api")
public class UserController implements UserApi {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Override
    public ResponseEntity<TokenDto> createUser(RegisterDetailsDto registerDetailsDto) {
        userService.createNewUser(registerDetailsDto.getUsername(), registerDetailsDto.getPassword(),
                registerDetailsDto.getRepeatedPassword());
        TokenDto tokenDto = userService.authenticate(registerDetailsDto.getUsername(),
                registerDetailsDto.getPassword());
        return ResponseEntity.ok(tokenDto);
    }


    @Override
    public ResponseEntity<TokenDto> loginUser(CredentialDetailsDto credentialDetailsDto) {
        TokenDto tokenDto = userService.authenticate(credentialDetailsDto.getUsername(),
                credentialDetailsDto.getPassword());
        return ResponseEntity.ok(tokenDto);
    }
}
