package com.example.blog.controller;

import com.example.blog.model.Comment;
import com.example.blog.model.Post;
import com.example.blog.model.User;
import com.example.blog.service.CommentService;
import com.example.blog.service.PostService;
import com.example.blog.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.Optional;

@Controller
@RequestMapping("/comments")
@RequiredArgsConstructor
@Slf4j
public class CommentController {

    private final CommentService commentService;
    private final PostService postService;
    private final UserService userService;

    @PostMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public String createNewComment(@PathVariable Long id, @ModelAttribute Comment comment, Principal principal) {
        String authUsername = "anonymousUser";

        if (principal != null) {
            authUsername = principal.getName();
        }

        User user = userService.findByEmail(authUsername).orElseThrow(() -> new IllegalArgumentException("User not found"));
        comment.setUser(user);

        Optional<Post> optionalPost = Optional.ofNullable(postService.findById(id));
        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            comment.setPost(post);
        }
        commentService.save(comment);
        return "redirect:/posts/" + id;
    }
}
