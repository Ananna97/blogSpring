package com.example.blog.controller;

import com.example.blog.model.Post;
import com.example.blog.model.Rating;
import com.example.blog.model.User;
import com.example.blog.service.PostService;
import com.example.blog.service.RatingService;
import com.example.blog.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@RestController
@RequestMapping("/api/ratings")
@RequiredArgsConstructor
@Slf4j
public class RatingRestController {

    private final RatingService ratingService;
    private final PostService postService;
    private final UserService userService;

    @PostMapping("/{postId}")
    public Rating addRating(@PathVariable Long postId, @RequestParam int value, Principal principal) {
        String authUsername = principal.getName();
        User user = userService.findByEmail(authUsername).orElseThrow(() -> new IllegalArgumentException("User not found"));
        Rating newRating = new Rating();

        Optional<Post> optionalPost = Optional.ofNullable(postService.findById(postId));
        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            newRating.setValue(value);
            newRating.setUser(user);
            newRating.setPost(post);

        }

        return ratingService.save(newRating);
    }
}