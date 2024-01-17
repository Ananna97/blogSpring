package com.example.blog.controllerTest;

import com.example.blog.controller.RatingController;
import com.example.blog.model.Post;
import com.example.blog.model.Rating;
import com.example.blog.model.User;
import com.example.blog.service.PostService;
import com.example.blog.service.RatingService;
import com.example.blog.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.security.Principal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class RatingControllerTest {

    @Mock
    private RatingService ratingService;

    @Mock
    private PostService postService;

    @Mock
    private UserService userService;

    @InjectMocks
    private RatingController ratingController;

    @Captor
    private ArgumentCaptor<Rating> ratingCaptor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addRating() {
        Long postId = 1L;
        int value = 5;

        User user = new User();
        user.setEmail("testuser@example.com");

        Post post = new Post();
        post.setId(postId);

        when(userService.findByEmail("testuser@example.com")).thenReturn(Optional.of(user));
        when(postService.findById(postId)).thenReturn(post);

        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), "password");
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String viewName = ratingController.addRating(postId, value, mockPrincipal(user.getEmail()));
        assertEquals("redirect:/posts/" + postId, viewName);
        verify(userService, times(1)).findByEmail("testuser@example.com");
        verify(postService, times(1)).findById(postId);
        verify(ratingService, times(1)).save(ratingCaptor.capture());

        Rating capturedRating = ratingCaptor.getValue();
        assertEquals(value, capturedRating.getValue());
        assertEquals(user, capturedRating.getUser());
        assertEquals(post, capturedRating.getPost());
    }

    private Principal mockPrincipal(String username) {
        return new UsernamePasswordAuthenticationToken(username, "password");
    }
}

