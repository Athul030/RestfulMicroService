package com.athul.restful_web_services.user;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class UserResource {

    private UserDaoService service;
    private UserRepository userRepository;

    public UserResource(UserDaoService service, UserRepository userRepository ) {
        this.service = service;
        this.userRepository = userRepository;

    }

    @GetMapping("/users")
    public List<User> retrieveAllUsers(){
        return service.findAll();
    }

    //get details of a single user
    @GetMapping("/users/{id}")
    public User retrieveOneUser(@PathVariable int id){
        User user =  service.findOne(id);
        if(user==null) throw new UserNotFoundException("id:"+id);
        return user;
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable int id){
        service.deleteUserById(id);

    }

    //create a user
    @PostMapping("/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user ){
         User savedUser = service.createUser(user);
         //build uri components with the same path by using the id from saved user

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId()).toUri();
        return ResponseEntity.created(location).build();
    }




}
