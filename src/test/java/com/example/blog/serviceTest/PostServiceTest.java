package com.example.blog.serviceTest;

import com.example.blog.model.Post;
import com.example.blog.model.Rating;
import com.example.blog.repository.PostRepository;
import com.example.blog.service.PostService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

public class PostServiceTest {

    @Test
    public void testFindById() {
        PostRepository postRepository = Mockito.mock(PostRepository.class);
        PostService postService = new PostService(postRepository);

        Post samplePost = new Post();
        samplePost.setId(1L);
        samplePost.setTitle("Test Post");
        samplePost.setCreatedAt(LocalDateTime.now());
        samplePost.setUpdatedAt(LocalDateTime.now());

        when(postRepository.findById(1L)).thenReturn(java.util.Optional.of(samplePost));
        when(postRepository.findById(2L)).thenReturn(java.util.Optional.empty());

        Post foundPost = postService.findById(1L);
        assertEquals(samplePost, foundPost);

        Post nonExistentPost = postService.findById(2L);
        assertNull(nonExistentPost);
    }

    @Test
    public void testCalculateAverageRating() {
        PostService postService = new PostService(null);

        Post postWithRatings = new Post();
        Rating rating1 = new Rating();
        rating1.setValue(4);
        Rating rating2 = new Rating();
        rating2.setValue(5);
        postWithRatings.setRatings(Arrays.asList(rating1, rating2));

        Double averageRating = postService.calculateAverageRating(postWithRatings);
        assertEquals(4.5, averageRating);

        Post postWithoutRatings = new Post();
        Double averageRatingWithoutRatings = postService.calculateAverageRating(postWithoutRatings);
        assertEquals(0.0, averageRatingWithoutRatings);
    }
}