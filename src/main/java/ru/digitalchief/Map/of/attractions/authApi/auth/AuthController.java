package ru.digitalchief.Map.of.attractions.authApi.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.digitalchief.Map.of.attractions.authApi.auth.dto.ErrorResponse;
import ru.digitalchief.Map.of.attractions.authApi.auth.dto.TokenResponse;
import ru.digitalchief.Map.of.attractions.authApi.auth.dto.User;
import ru.digitalchief.Map.of.attractions.authApi.auth.exception.LoginException;
import ru.digitalchief.Map.of.attractions.authApi.auth.exception.RegistrationException;
import ru.digitalchief.Map.of.attractions.authApi.auth.service.ClientService;
import ru.digitalchief.Map.of.attractions.authApi.auth.service.TokenGenerateService;

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
