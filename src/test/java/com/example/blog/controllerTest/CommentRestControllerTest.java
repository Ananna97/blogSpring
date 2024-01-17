package com.example.blog.controllerTest;

import com.example.blog.controller.CommentRestController;
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

import java.security.Principal;
import java.util.Optional;

import static org.mockito.Mockito.*;

class CommentRestControllerTest {

    @Mock
    private CommentService commentService;

    @Mock
    private PostService postService;

    @Mock
    private UserService userService;

    @InjectMocks
    private CommentRestController commentRestController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createNewComment() {
        Long postId = 1L;
        String text = "Test comment";
        Long userId = 1L;

        Principal principal = createPrincipal("testuser@example.com");

        User user = new User();
        user.setEmail("testuser@example.com");

        Post post = new Post();
        post.setId(postId);

        Comment expectedComment = new Comment();
        expectedComment.setText(text);
        expectedComment.setUser(user);
        expectedComment.setPost(post);

        when(userService.findByEmail("testuser@example.com")).thenReturn(Optional.of(user));
        when(postService.findById(postId)).thenReturn(post);
        when(commentService.save(any(Comment.class))).thenReturn(expectedComment);

        Comment result = commentRestController.createNewComment(postId, text, userId, new Comment(), principal);

        verify(userService, times(1)).findByEmail("testuser@example.com");
        verify(postService, times(1)).findById(postId);
        verify(commentService, times(1)).save(any(Comment.class));

    }

    private Principal createPrincipal(String username) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(username, "password");
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return authentication;
    }
}

