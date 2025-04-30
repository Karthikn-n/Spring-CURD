package com.example.helloworld.security.auth;

import com.example.helloworld.security.config.JwtService;
import com.example.helloworld.security.model.EncryptedResponse;
import com.example.helloworld.security.user.Role;
import com.example.helloworld.security.user.User;
import com.example.helloworld.security.user.UserRepository;
import com.example.helloworld.security.util.RsaKeyUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    /// It will use the {@link lombok.Builder @Builder} to create structured data
    /// var is accept dynamic data or Use val from lombok.
    /// Before send to the Client as Response save the User Detail in Database using {@link UserRepository UserRepository}.
    /// Then it will generate the token using {@link JwtService JwtService}.
    /// Then it will wrap the Bearer token in the token variable then send as response to the client.
    public EncryptedResponse register(RegisterRequest request) {
        // Step 1: Generate RSA key pair
        try {
            KeyPair keyPair = RsaKeyUtil.generateRsaKeyPair();
            PublicKey publicKey = keyPair.getPublic();
            PrivateKey privateKey = keyPair.getPrivate();

            var user = User.builder()
                    .firstName(request.getFirstName())
                    .lastName(request.getLastName())
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(Role.USER)
                    .build();
            repository.save(user);

// JWT Generation
            var jwtToken = jwtService.generateToken(user);

// AES Key Generation
            SecretKey aesKey = RsaKeyUtil.generateAESKey(256);

// Encrypt JWT with AES
            byte[] encryptedJwt = RsaKeyUtil.encryptAES(jwtToken, aesKey);
            String encryptedJwtBase64 = Base64.getEncoder().encodeToString(encryptedJwt);

// Encrypt AES key with RSA
            Cipher rsaCipher = Cipher.getInstance("RSA");
            rsaCipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] encryptedAesKey = rsaCipher.doFinal(aesKey.getEncoded());
            String encryptedAesKeyBase64 = Base64.getEncoder().encodeToString(encryptedAesKey);

// Prepare response
            return EncryptedResponse.builder()
                    .encryptedToken(encryptedJwtBase64)
                    .encryptedKey(encryptedAesKeyBase64)
                    .privateKey(Base64.getEncoder().encodeToString(privateKey.getEncoded()))
                    .status("ok")
                    .message("Registered successfully")
                    .build();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow();

        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
