package org.springai.flash.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springai.flash.Model.SuccessResponse;
import org.springai.flash.Model.Users;
import org.springai.flash.exception.ErrorException;
import org.springai.flash.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserService {

    @Autowired
    private JwtService jwtService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public JsonNode register(Users users) throws ErrorException {
        if (users.getUsername() == null || users.getPassword() == null || users.getUsername().trim().isEmpty() || users.getPassword().trim().isEmpty()) {
            return buildErrorResponse(new ErrorException("Username or Password cannot be null",HttpStatus.BAD_REQUEST.value()));
        }
        users.setPassword(encoder.encode(users.getPassword()));
        try {
            userRepository.save(users);
            return buildSuccessResp(new SuccessResponse(users,HttpStatus.CREATED.value()));
        } catch (Exception e) {
            return buildErrorResponse(new ErrorException("Error registering user: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }

    }

    public JsonNode verify(Users user) {
        if (user.getUsername() == null || user.getPassword() == null || user.getUsername().trim().isEmpty() || user.getPassword().trim().isEmpty()) {
            return buildErrorResponse(new ErrorException("Username or Password cannot be null",HttpStatus.BAD_REQUEST.value()));
        }
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
            );

            if (authentication.isAuthenticated()) {
                String token = jwtService.generateToken(user.getUsername());
                return buildSuccessResponse(new SuccessResponse("Authentication successful", token, HttpStatus.OK.value()));
            } else {
                return buildErrorResponse(new ErrorException("Authentication failed", HttpStatus.UNAUTHORIZED.value()));
            }
        } catch (Exception e) {
            return buildErrorResponse(new ErrorException("Authentication error", HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    private JsonNode buildSuccessResponse(SuccessResponse sresponse) {
        Map<String, Object> successresponse = Map.of(
                "status", sresponse.getStatusCode(),
                "message", sresponse.getMessage(),
                "token", sresponse.getToken());
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.valueToTree(successresponse);
    }

    private JsonNode buildSuccessResp(SuccessResponse sresponse) {
        Map<String, Object> successresponse = Map.of(
                "status", sresponse.getStatusCode(),
                "user", sresponse.getUser());
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.valueToTree(successresponse);
    }
    private JsonNode buildErrorResponse (ErrorException e)
    {
        Map<String, Object> errorResponse = Map.of(
                "status", e.getStatusCode(),
                "message", e.getMessage());
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.valueToTree(errorResponse);
    }
}