package com.example.todoapp.controller;


import com.example.todoapp.model.User;
import com.example.todoapp.payload.JwtResponse;
import com.example.todoapp.payload.LoginRequest;
import com.example.todoapp.payload.MessageResponse;
import com.example.todoapp.payload.SignupRequest;
import com.example.todoapp.repository.UserRepository;
import com.example.todoapp.security.JwtUtils;
import com.example.todoapp.service.UserDetailsImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(value = "User Rest Controller", description = "REST API for User")
@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    JwtUtils jwtUtils;


    @ApiOperation(value = "Sign In ", response = JwtResponse.class, tags = "authenticateUser")
    @PostMapping("/signin")
    public ResponseEntity<JwtResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();


        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getMail()));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        logger.info("signup"+signUpRequest.toString());

        if (userRepository.existsByName(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Hata: Bu kullanıcı adı kullanılmakta.!"));
        }

        if (userRepository.existsByMail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Hata: Bu Mail adresi kullanılmakta.!"));
        }

        // Create new user's account
        User user = User.builder().mail(signUpRequest.getEmail()).password(encoder.encode(signUpRequest.getPassword())).name(signUpRequest.getUsername()).build();


        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("Kullanıcı Kaydı Başarılı!"));
    }
}
