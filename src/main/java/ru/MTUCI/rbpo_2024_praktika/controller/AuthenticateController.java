package ru.MTUCI.rbpo_2024_praktika.controller;

import ru.MTUCI.rbpo_2024_praktika.configuration.JwtTokenProvider;
import ru.MTUCI.rbpo_2024_praktika.controller.dto.LoginRequest;
import ru.MTUCI.rbpo_2024_praktika.controller.dto.LoginResponse;
import ru.MTUCI.rbpo_2024_praktika.controller.dto.RegRequest;
import ru.MTUCI.rbpo_2024_praktika.model.ApplicationRole;
import ru.MTUCI.rbpo_2024_praktika.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.stream.Collectors;


@RequiredArgsConstructor
@RequestMapping("/auth")
@RestController
public class AuthenticateController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtProvider;
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            return ResponseEntity.ok(new LoginResponse(request.getEmail(), jwtProvider.createToken(request.getEmail(),
                    authenticationManager
                            .authenticate(
                                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()))
                            .getAuthorities().stream().collect(Collectors.toSet()))));
        } catch (AuthenticationException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Incorrect password");
        }
    }

    @PostMapping("/reg")
    public ResponseEntity<?> register(@RequestBody RegRequest request) {
        try {
            userService.registerUser(request.getLogin(), request.getPassword(), request.getEmail(), ApplicationRole.USER);
            return ResponseEntity.ok("Successful registation");
        } catch (IllegalStateException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(ex.getMessage());
        }
    }
}