package com.example.blog.controller;

import com.example.blog.model.Post;
import com.example.blog.model.User;
import com.example.blog.service.PostService;
import com.example.blog.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
@Slf4j
public class PostRestController {

    private final PostService postService;
    private final UserService userService;

    @GetMapping("/{id}")
    public Post getPost(@PathVariable Long id) {
        Optional<Post> optionalPost = Optional.ofNullable(this.postService.findById(id));
        Post post = new Post();
        if (optionalPost.isPresent()) {
            post = optionalPost.get();
        }
        return post;
    }

    @PostMapping
    public Post createNewPost(@ModelAttribute Post post, Principal principal) {
        String authUsername = "anonymousUser";
        if (principal != null) {
            authUsername = principal.getName();
        }

        User user = userService.findByEmail(authUsername).orElseThrow(() -> new IllegalArgumentException("User not found"));

        post.setUser(user);
        return postService.save(post);
    }

    @PutMapping("/{id}")
    public Post updatePost(@PathVariable Long id, Post post) {
        Post existingPost = new Post();
        Optional<Post> optionalPost = Optional.ofNullable(postService.findById(id));
        if (optionalPost.isPresent()) {
            existingPost = optionalPost.get();

            existingPost.setTitle(post.getTitle());
            existingPost.setBody(post.getBody());

        }

        return postService.save(existingPost);
    }


    @DeleteMapping("/{id}")
    public String deletePost(@PathVariable Long id) {
        Optional<Post> optionalPost = Optional.ofNullable(postService.findById(id));
        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();

            postService.delete(post);
            return "SUCCESS";
        } else {
            return "ERROR";
        }
    }








}