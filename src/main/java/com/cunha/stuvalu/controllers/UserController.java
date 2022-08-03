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
    public ResponseEntity<Object> createUser(@RequestBody @Valid UserDto user){
        var existsUser = userService.getUserByEmail(user.getEmail());
        if(existsUser.isPresent()){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Usuário já cadastrado");
        }else{
            var userModel = new UserModel();
            user.setPassword(encoder.encode(user.getPassword()));
            BeanUtils.copyProperties(user, userModel);
            return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(userModel));
        }

    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody String email, @RequestBody String password){
        var user = userService.getUserByEmail(email);
        if(user.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email não cadastrado");
        }

        boolean passwordMatch = encoder.matches(password, user.get().getPassword());

        if(passwordMatch){
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Usuário logado com sucesso!");
        }else{
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Senha incorreta!");
        }
    }
}
