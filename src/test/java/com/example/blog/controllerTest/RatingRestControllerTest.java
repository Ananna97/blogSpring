package com.example.blog.controllerTest;

import com.example.blog.controller.RatingRestController;
import com.example.blog.model.Post;
import com.example.blog.model.Rating;
import com.example.blog.model.User;
import com.example.blog.service.PostService;
import com.example.blog.service.RatingService;
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

class RatingRestControllerTest {

    @Mock
    private RatingService ratingService;

    @Mock
    private PostService postService;

    @Mock
    private UserService userService;

    @InjectMocks
    private RatingRestController ratingRestController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addRating() {
        Long postId = 1L;
        int value = 5;

        Principal principal = createPrincipal("testuser@example.com");

        User user = new User();
        user.setEmail("testuser@example.com");

        Post post = new Post();
        post.setId(postId);

        Rating expectedRating = new Rating();
        expectedRating.setValue(value);
        expectedRating.setUser(user);
        expectedRating.setPost(post);

        when(userService.findByEmail("testuser@example.com")).thenReturn(Optional.of(user));
        when(postService.findById(postId)).thenReturn(post);
        when(ratingService.save(any(Rating.class))).thenReturn(expectedRating);

        Rating result = ratingRestController.addRating(postId, value, principal);

        verify(userService, times(1)).findByEmail("testuser@example.com");
        verify(postService, times(1)).findById(postId);
        verify(ratingService, times(1)).save(any(Rating.class));
    }

    private Principal createPrincipal(String username) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(username, "password");
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return authentication;
    }
}
