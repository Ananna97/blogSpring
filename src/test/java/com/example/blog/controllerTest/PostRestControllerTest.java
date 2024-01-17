package com.example.blog.controllerTest;

import com.example.blog.controller.PostRestController;
import com.example.blog.model.Post;
import com.example.blog.model.Role;
import com.example.blog.model.User;
import com.example.blog.service.PostService;
import com.example.blog.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class PostRestControllerTest {

    @Test
    public void testGetPost() {
        PostService postService = Mockito.mock(PostService.class);
        UserService userService = Mockito.mock(UserService.class);

        PostRestController postRestController = new PostRestController(postService, userService);

        Post samplePost = new Post();
        samplePost.setId(1L);
        samplePost.setTitle("Sample Title");
        samplePost.setBody("Sample Body");
        samplePost.setCreatedAt(LocalDateTime.now());
        samplePost.setUpdatedAt(LocalDateTime.now());
        User u = new User();
        samplePost.setUser(u);

        when(postService.findById(1L)).thenReturn(samplePost);

        Post resultPost = postRestController.getPost(1L);

        assertEquals(samplePost, resultPost);
        verify(postService, times(1)).findById(1L);
    }

    @Test
    public void testCreateNewPost() {
        PostService postService = Mockito.mock(PostService.class);
        UserService userService = Mockito.mock(UserService.class);

        PostRestController postRestController = new PostRestController(postService, userService);

        User sampleUser = new User();
        sampleUser.setId(1L);
        sampleUser.setEmail("test@example.com");
        sampleUser.setPassword("password");

        Role role = new Role();
        role.setName("ROLE_USER");
        sampleUser.setRoles(Collections.singleton(role));

        when(userService.findByEmail("test@example.com")).thenReturn(Optional.of(sampleUser));

        Post postToCreate = new Post();
        postToCreate.setTitle("New Title");
        postToCreate.setBody("New Body");

        when(postService.save(any(Post.class))).thenAnswer(invocation -> {
            Post savedPost = invocation.getArgument(0);
            savedPost.setId(1L);
            return savedPost;
        });

        Principal principal = new UsernamePasswordAuthenticationToken("test@example.com", "password");

        Post createdPost = postRestController.createNewPost(postToCreate, principal);

        assertEquals(1L, createdPost.getId());
        assertEquals("New Title", createdPost.getTitle());
        assertEquals("New Body", createdPost.getBody());
        assertEquals(sampleUser, createdPost.getUser());
        verify(userService, times(1)).findByEmail("test@example.com");
        verify(postService, times(1)).save(any(Post.class));
    }

    @Test
    public void testUpdatePost() {
        PostService postService = Mockito.mock(PostService.class);
        UserService userService = Mockito.mock(UserService.class);

        PostRestController postRestController = new PostRestController(postService, userService);

        Post existingPost = new Post();

        existingPost.setId(1L);
        existingPost.setTitle("Existing Title");
        existingPost.setBody("Existing Body");
        existingPost.setCreatedAt(LocalDateTime.now());
        existingPost.setUpdatedAt(LocalDateTime.now());
        User u1 = new User();
        existingPost.setUser(u1);

        Post updatedPost = new Post();
        updatedPost.setId(1L);
        updatedPost.setTitle("Updated Title");
        updatedPost.setBody("Updated Body");
        updatedPost.setCreatedAt(LocalDateTime.now());
        updatedPost.setUpdatedAt(LocalDateTime.now());

        when(postService.findById(1L)).thenReturn(existingPost);

        when(postService.save(any(Post.class))).thenAnswer(invocation -> {
            Post savedPost = invocation.getArgument(0);
            savedPost.setId(1L);
            return savedPost;
        });

        Post resultPost = postRestController.updatePost(1L, updatedPost);

        assertEquals(1L, resultPost.getId());
        assertEquals("Updated Title", resultPost.getTitle());
        assertEquals("Updated Body", resultPost.getBody());
        assertEquals(existingPost.getUser(), resultPost.getUser());
        verify(postService, times(1)).findById(1L);
        verify(postService, times(1)).save(any(Post.class));
    }

    @Test
    public void testDeletePost() {
        PostService postService = Mockito.mock(PostService.class);
        UserService userService = Mockito.mock(UserService.class);

        PostRestController postRestController = new PostRestController(postService, userService);

        Post existingPost = new Post();
        existingPost.setId(1L);
        existingPost.setTitle("Sample Title");
        existingPost.setBody("Sample Body");
        User u = new User();
        existingPost.setUser(u);

        when(postService.findById(1L)).thenReturn(existingPost);

        String result = postRestController.deletePost(1L);

        assertEquals("SUCCESS", result);
        verify(postService, times(1)).findById(1L);
        verify(postService, times(1)).delete(existingPost);
    }


    @Test
    public void testDeletePostPostNotFound() {
        PostService postService = Mockito.mock(PostService.class);
        UserService userService = Mockito.mock(UserService.class);

        PostRestController postRestController = new PostRestController(postService, userService);

        when(postService.findById(1L)).thenReturn(null);

        String result = postRestController.deletePost(1L);

        assertEquals("ERROR", result);
        verify(postService, times(1)).findById(1L);
    }
}

