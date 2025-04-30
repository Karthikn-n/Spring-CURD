package com.example.helloworld.security.auth;

import com.example.helloworld.security.model.EncryptedResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * This is where the API request comes first,
 * It will control the API request from client side
 * If User is call {@code  http://localhost:8000/api/v1/auth/register} it will call the register method via
 * {@link PostMapping @PostMapping} . {@link RestController @RestController} is tell spring container that it is Spring Bean so insert Dependency,
 * Handle its objects by own.
 * {@link RequestMapping @RequestMapping} it will map all the request from client side to the controller,
 * If more than one {@link RequestMapping @RequestMapping} is detected it will log a error in console.
 * {@link RequiredArgsConstructor @RequiredArgsConstructor} It will generate a constructor for final fields and {@code NonNull}
 */
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    /// This will automatically integrated to this controller whenever it needed
    /// We don't need to create any Object for this service
    private final AuthenticationService service;

    /// It will call when client Request register endpoint and Return Bearer Token as response
    /// to the client from the server.
    /// {@link RequestBody @RequestBody} will take the body parameter from request and it is required
    /// If don't want required body then set it as false to avoid exception,
    /// It works well with Java Object so it accept JSON, XML and other complex types as body param.
    /// Same {@link RequestParam @RequestParam} it will accept simple datatypes like String, int so it works well with
    /// application/x-www-form-urlencoded
    /// {@link ResponseEntity @ResponseEntity} is send HTTP response from it In-Built template with our response params
    @PostMapping("/register")
    public ResponseEntity<EncryptedResponse> register(
            @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(service.authenticate(request));
    }

}
