package com.example.blog.controllerTest;

import com.example.blog.controller.CommentController;
import com.example.blog.model.Comment;
import com.example.blog.model.Post;
import com.example.blog.model.User;
import com.example.blog.service.CommentService;
import com.example.blog.service.PostService;
import com.example.blog.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;

import java.security.Principal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CommentControllerTest {

    @Mock
    private CommentService commentService;

    @Mock
    private PostService postService;

    @Mock
    private UserService userService;

    @Mock
    private Model model;

    @InjectMocks
    private CommentController commentController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createNewComment() {
        Long postId = 1L;

        Comment comment = new Comment();
        User user = new User();
        user.setEmail("testuser@example.com");

        Post post = new Post();
        post.setId(postId);

        when(userService.findByEmail("testuser@example.com")).thenReturn(Optional.of(user));
        when(postService.findById(postId)).thenReturn(post);

        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), "password");
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String viewName = commentController.createNewComment(postId, comment, mockPrincipal(user.getEmail()));

        assertEquals("redirect:/posts/" + postId, viewName);
        verify(userService, times(1)).findByEmail("testuser@example.com");
        verify(postService, times(1)).findById(postId);
        verify(commentService, times(1)).save(comment);
    }

    private Principal mockPrincipal(String username) {
        return new UsernamePasswordAuthenticationToken(username, "password");
    }
}

