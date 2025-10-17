package com.backend.android.api.controller;

import com.backend.android.api.entity.UserEntity;
import com.backend.android.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("users")
public class UserController {

    @Autowired
    private UserService userService;

    //creando a los usuarios
    @PostMapping
    public ResponseEntity<UserEntity> addUser(@RequestBody UserEntity user ){
        UserEntity newUser = userService.addUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }

    //vamos a obtener todos los usuarios
    @GetMapping
    public ResponseEntity<List<UserEntity>> getAllUsers (){
        List<UserEntity> getUsers = userService.getUser();
        return ResponseEntity.ok(getUsers);
    }

    //buscamos al usuario por "id"
    @GetMapping("/{id}")
    public ResponseEntity<?> getUsersById(@PathVariable Long id){
        Optional<UserEntity> user = userService.getUserById(id);
        if (user.isPresent()){
            return ResponseEntity.ok(user.get());
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
        }
    }

    //con esto vamos a poder actualizar a los usuarios
    @PutMapping("/{id}")
    public ResponseEntity<UserEntity> updateUser (@PathVariable Long id, @RequestBody UserEntity users){
        UserEntity updatedUser = userService.updateUser(id, users);
        return ResponseEntity.ok(updatedUser);
    }

    //eliminar usuarios por medio de un "id"
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id){
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<UserEntity> findByUsername(@PathVariable String username){
        UserEntity getUserByUsername = userService.getByUsername(username);
        return ResponseEntity.ok(getUserByUsername );

    }
}
