package com.example.blog.serviceTest;

import com.example.blog.model.Rating;
import com.example.blog.repository.RatingRepository;
import com.example.blog.service.RatingService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class RatingServiceTest {

    @Test
    public void testFindAll() {
        RatingRepository ratingRepository = Mockito.mock(RatingRepository.class);
        RatingService ratingService = new RatingService(ratingRepository);

        Rating rating1 = new Rating(1L, 5);
        Rating rating2 = new Rating(2L, 4);

        when(ratingRepository.findAll()).thenReturn(Arrays.asList(rating1, rating2));

        List<Rating> ratings = ratingService.findAll();
        assertEquals(2, ratings.size());
        assertEquals(rating1, ratings.get(0));
        assertEquals(rating2, ratings.get(1));
    }

    @Test
    public void testSave() {
        RatingRepository ratingRepository = Mockito.mock(RatingRepository.class);
        RatingService ratingService = new RatingService(ratingRepository);

        Rating ratingToSave = new Rating(5);

        when(ratingRepository.save(ratingToSave)).thenReturn(new Rating(1L, 5));

        Rating savedRating = ratingService.save(ratingToSave);
        assertEquals(1L, savedRating.getId());
        assertEquals(5, savedRating.getValue());
    }
}
