package com.cunha.stuvalu.services;

import com.cunha.stuvalu.models.UserModel;
import com.cunha.stuvalu.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository  userRepository;

    @Transactional
    public UserModel createUser(UserModel user){
        return userRepository.save(user);
    }

    public Optional<UserModel> getUserById(UUID id){
        return userRepository.findById(id);
    }

    public Optional<UserModel> getUserByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public List<UserModel> getUsers(){
        return userRepository.findAll();
    }
}
