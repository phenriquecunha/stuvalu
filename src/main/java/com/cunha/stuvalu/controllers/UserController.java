package com.cunha.stuvalu.controllers;

import com.cunha.stuvalu.dtos.UserDto;
import com.cunha.stuvalu.models.UserModel;
import com.cunha.stuvalu.services.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@RestController()
@RequestMapping("/user")
public class UserController {

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    UserService userService;

    @GetMapping("/users")
    public ResponseEntity<Object> getUsers(){
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(userService.getUsers());
    }

    @PostMapping("/create")
    public ResponseEntity<Object> createUser(@RequestBody @NotNull @Valid UserDto user){
        Optional<UserModel> existsUser = userService.getUserByEmail(user.getEmail());

        if(existsUser.isPresent()){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User already registered!");
        }else{
            UserModel userModel = new UserModel();
            user.setPassword(encoder.encode(user.getPassword()));
            BeanUtils.copyProperties(user, userModel);
            userModel.setCreatedAt(LocalDateTime.now());
            return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(userModel));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody String email, @RequestBody String password){
        Optional<UserModel> user = userService.getUserByEmail(email);
        if(user.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email not registered!");
        }

        boolean passwordMatch = encoder.matches(password, user.get().getPassword());

        if(passwordMatch){
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("User authenticated");
        }else{
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Incorrect password");
        }
    }

    @PostMapping("/user")
    public ResponseEntity<Object> getUser(@RequestBody UUID id){
      //TODO
      return null;
    }
}
