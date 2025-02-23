package org.springai.flash.controller;

import com.fasterxml.jackson.databind.JsonNode;
import org.springai.flash.Model.Users;
import org.springai.flash.exception.ErrorException;
import org.springai.flash.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/login")
    public ResponseEntity<JsonNode> performLogin(@RequestBody Users users){
        return ResponseEntity.ok(userService.verify(users));
    }

    @PostMapping(value = "/signup")
    public ResponseEntity<JsonNode> performSignup(@RequestBody Users users) throws ErrorException {
        return ResponseEntity.ok(userService.register(users));
    }



}
