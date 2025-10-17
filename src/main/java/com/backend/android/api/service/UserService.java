package com.backend.android.api.service;

import com.backend.android.api.entity.UserEntity;
import com.backend.android.api.exceptions.DuplicateResourceException;
import com.backend.android.api.exceptions.ResourceNotFoundException;
import com.backend.android.api.repository.UserRepository;
import jakarta.transaction.TransactionScoped;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserEntity addUser(UserEntity user){

        if(userRepository.getByUsername(user.getUsername()).isPresent()){
            throw new DuplicateResourceException("El nombre de usuario: \"" + user.getUsername() + "\" ya existe dentro de la base de datos...");
        }
        return userRepository.save(user);
    }


    public List<UserEntity> getUser(){
        return  userRepository.findAll();
    }

    public Optional<UserEntity> getUserById(Long id){
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("El usuario con el id: " + id + ", no fue posible encontrarlo."));
        return Optional.of(user);
    }


    public UserEntity updateUser (Long id, UserEntity updatedUser){
        return userRepository.findById(id)
                .map(user ->{
                    if(userRepository.getByUsername(updatedUser.getUsername()).isPresent() &&
                    !user.getId().equals(id)){
                        throw new DuplicateResourceException("El nombre de usuario \"" + updatedUser.getUsername() +"\" ya esta en uso");
                    }
                    user.setUsername(user.getUsername());
                    user.setEmail(user.getEmail());

                    return userRepository.save(user);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Usuario con el id: \"" + id + "\" no encontrado para actualizar"));
    }

    public void deleteUserById( Long id){

        boolean exist = userRepository.existsById(id);
        if(!exist){
            throw new ResourceNotFoundException("El id: \"" + id + "\" que especifícas, no existe.");
        }
        userRepository.deleteById(id);
    }

    public UserEntity getByUsername(String username){
        return userRepository.getByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario: \"" + username + "\" no encontrado"));
    }
}
