package com.athul.restful_web_services.user;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class UserJPAResource {

    private UserDaoService service;

    private UserRepository userRepo;
    private PostRepository postRepository;


    public UserJPAResource(UserDaoService service, UserRepository userRepo, PostRepository postRepository) {
        this.service = service;
        this.userRepo = userRepo;
        this.postRepository = postRepository;

    }

    @GetMapping("/jpa/users")
    public List<User> retrieveAllUsers(){
        return userRepo.findAll();
    }

    //get details of a single user
    @GetMapping("/jpa/users/{id}")
    public User retrieveOneUser(@PathVariable int id){
        Optional<User> user =  userRepo.findById(id);
        if(user.isEmpty()) throw new UserNotFoundException("id:"+id);
        return user.get();
    }

    @DeleteMapping("/jpa/users/{id}")
    public void deleteUser(@PathVariable int id){
        userRepo.deleteById(id);

    }

    @GetMapping("/jpa/users/{id}/posts")
    public List<Post> retrievePostsForUser(@PathVariable int id) {
        Optional<User> user = userRepo.findById(id);

        if(user.isEmpty())
            throw new UserNotFoundException("id:"+id);

        return user.get().getPostList();

    }

    @GetMapping("/jpa/users/{id}/posts/{postId}")
    public Post retrieveOnePostsForUser(@PathVariable int id, @PathVariable int postId) {
        Optional<User> user = userRepo.findById(id);
        Optional<Post> post = postRepository.findById(Long.valueOf(postId));
        if(post.isEmpty())
            throw new UserNotFoundException("id:"+id);

        return post.get();

    }


    //create a user
    @PostMapping("/jpa/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user ){
         User savedUser = userRepo.save(user);
         //build uri components with the same path by using the id from saved user
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PostMapping("/jpa/users/{id}/posts")
    public ResponseEntity<Post> createPostsForUser(@PathVariable int id, @Valid @RequestBody Post post) {
        Optional<User> user = userRepo.findById(id);

        if(user.isEmpty())
            throw new UserNotFoundException("id:"+id);

        post.setUser(user.get());
        Post savedPost = postRepository.save(post);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedPost.getId()).toUri();

        return ResponseEntity.created(location).build();

    }
}
