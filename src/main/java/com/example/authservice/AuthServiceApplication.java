package com.example.authservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.SecureRandom;
import java.util.Base64;

@SpringBootApplication
public class AuthServiceApplication {

    public static void main(String[] args) {
        /*// Generate 32 random bytes
        byte[] key = new byte[32];
        new SecureRandom().nextBytes(key);

        // Encode to Base64
        String base64Secret = Base64.getEncoder().encodeToString(key);
        System.out.println("Proper 32-byte secret: " + base64Secret);
        System.out.println("Length: " + base64Secret.length() + " chars");*/

        SpringApplication.run(AuthServiceApplication.class, args);}

}
