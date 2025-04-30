package com.example.helloworld.security.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EncryptedResponse {
    private Object response;
    private String encryptedToken;
    private String encryptedKey;
    private String privateKey;
    private String message;
    private String status;
}
