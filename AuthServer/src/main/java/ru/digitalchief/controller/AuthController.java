package ru.digitalchief.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.digitalchief.dto.ErrorResponse;
import ru.digitalchief.dto.TokenResponse;
import ru.digitalchief.dto.User;
import ru.digitalchief.exception.LoginException;
import ru.digitalchief.exception.RegistrationException;
import ru.digitalchief.service.ClientService;
import ru.digitalchief.service.TokenGenerateService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final ClientService clientService;
    private final TokenGenerateService tokenGenerateService;

    @PostMapping
    public ResponseEntity<String> register(@RequestBody User user) {
        clientService.register(user.getClientId(), user.getClientSecret());
        return ResponseEntity.ok("Registered");
    }

    @PostMapping("/token")
    public TokenResponse getToken(@RequestBody User user) {
        clientService.checkCredentials(user.getClientId(), user.getClientSecret());
        return new TokenResponse(tokenGenerateService.generateToken(user.getClientId()));
    }

    @ExceptionHandler({RegistrationException.class, LoginException.class})
    public ResponseEntity<ErrorResponse> handleUserRegistrationException(RuntimeException ex) {
        return ResponseEntity
                .badRequest()
                .body(new ErrorResponse(ex.getMessage()));
    }
}
