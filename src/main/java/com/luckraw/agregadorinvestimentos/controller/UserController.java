package com.luckraw.agregadorinvestimentos.controller;

import com.luckraw.agregadorinvestimentos.entity.User;
import com.luckraw.agregadorinvestimentos.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/v1/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody CreateUserDTO createUserDTO) {
        var userId = userService.createUser(createUserDTO);
        return ResponseEntity.created(URI.create("/v1/users/" + userId.toString())).build();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUser(@PathVariable("userId") String userId) {
        var user = userService.getUserById(userId);

        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<User>> listUsers(){
        var users = userService.listUsers();
        return ResponseEntity.ok(users);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable("userId") String userId, @RequestBody UpdateUserDTO updateUserDTO){
        userService.updateUserById(userId, updateUserDTO);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable("userId") String userId){
        userService.deleteById(userId);
        return ResponseEntity.noContent().build();
    }

}
