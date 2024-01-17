package com.example.blog.service;

import com.example.blog.model.Post;
import com.example.blog.model.Rating;
import com.example.blog.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Post findById(Long id) {
        return postRepository.findById(id).orElse(null);
    }

    public List<Post> findAll() {
        return postRepository.findAll();
    }

    public Post save(Post post) {
        if (post.getId() == null) {
            post.setCreatedAt(LocalDateTime.now());
        }
        post.setUpdatedAt(LocalDateTime.now());
        return postRepository.save(post);
    }

    public void delete(Post post) {
        postRepository.delete(post);
    }

    public Double calculateAverageRating(Post post) {
        List<Rating> ratings = post.getRatings();
        if (ratings != null && !ratings.isEmpty()) {
            double sum = ratings.stream().mapToInt(Rating::getValue).sum();
            return (int) (sum / ratings.size() * 100) / 100.0;
        } else {
            return 0.0;
        }
    }

}