package cn.lazylhxzzy.save_upload.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

// Controller class for handling login and token validation
@Controller
public class AuthController {
    @Value("${user.username}")
    private String configuredUsername;

    @Value("${user.pwd}")
    private String configuredPassword;

    @Value("${secret.key}")
    private String secretKey;

    private static final String ALGORITHM = "HS256";
    private Map<String, String> tokenStore = new HashMap<>();
    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestParam String username, @RequestParam String password) {
        if (configuredUsername.equals(username) && configuredPassword.equals(password)) {
            String token = generateToken(username);
            tokenStore.put(token, username);
            return ResponseEntity.ok(Map.of("token", token, "redirect", "/navigate.html"));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }
    @GetMapping("/auth/validate")
    public ResponseEntity<?> validateToken(@RequestParam String token) {
        if (tokenStore.containsKey(token)) {
            return ResponseEntity.ok("Token is valid");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
        }
    }

    private String generateToken(String username) {
        String header = Base64.getEncoder().encodeToString(("{\"alg\":\"" + ALGORITHM + "\",\"typ\":\"JWT\"}").getBytes());
        String payload = Base64.getEncoder().encodeToString(("{\"username\":\"" + username + "\"}").getBytes());
        String signature = Base64.getEncoder().encodeToString((header + "." + payload + secretKey).getBytes());
        return header + "." + payload + "." + signature;
    }
}


