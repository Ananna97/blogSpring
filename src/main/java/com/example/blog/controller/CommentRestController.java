package com.example.blog.controller;

import com.example.blog.model.Comment;
import com.example.blog.model.Post;
import com.example.blog.model.User;
import com.example.blog.service.CommentService;
import com.example.blog.service.PostService;
import com.example.blog.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
@Slf4j
public class CommentRestController {

    private final CommentService commentService;
    private final PostService postService;
    private final UserService userService;

    @PostMapping("/{postId}")
    public Comment createNewComment(@PathVariable Long postId, @RequestParam String text, @RequestParam Long userId, @ModelAttribute Comment comment, Principal principal) {
        String authUsername = "anonymousUser";

        if (principal != null) {
            authUsername = principal.getName();
        }

        User user = userService.findByEmail(authUsername).orElseThrow(() -> new IllegalArgumentException("User not found"));
        comment.setUser(user);

        Optional<Post> optionalPost = Optional.ofNullable(postService.findById(postId));
        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            comment.setText(text);
            comment.setPost(post);
            comment.setUser(user);
        }

        return commentService.save(comment);
    }
}
