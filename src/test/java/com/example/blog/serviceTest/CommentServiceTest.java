package com.example.blog.serviceTest;
import com.example.blog.model.Comment;
import com.example.blog.model.Post;
import com.example.blog.repository.CommentRepository;
import com.example.blog.service.CommentService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class CommentServiceTest {

    @Test
    public void testFindAll() {
        CommentRepository commentRepository = Mockito.mock(CommentRepository.class);
        CommentService commentService = new CommentService(commentRepository);

        Comment comment1 = new Comment(1L, "Great post!", new Post());
        Comment comment2 = new Comment(2L, "Well done!", new Post());

        when(commentRepository.findAll()).thenReturn(Arrays.asList(comment1, comment2));

        List<Comment> comments = commentService.findAll();
        assertEquals(2, comments.size());
        assertEquals(comment1, comments.get(0));
        assertEquals(comment2, comments.get(1));
    }

    @Test
    public void testFindAllByPost() {
        CommentRepository commentRepository = Mockito.mock(CommentRepository.class);
        CommentService commentService = new CommentService(commentRepository);

        Post post = new Post();

        Comment comment1 = new Comment(1L, "Great post!", post);
        Comment comment2 = new Comment(2L, "Well done!", post);

        when(commentRepository.findByPost(post)).thenReturn(Arrays.asList(comment1, comment2));

        List<Comment> commentsForPost = commentService.findAllByPost(post);
        assertEquals(2, commentsForPost.size());
        assertEquals(comment1, commentsForPost.get(0));
        assertEquals(comment2, commentsForPost.get(1));
    }

    @Test
    public void testSave() {
        CommentRepository commentRepository = Mockito.mock(CommentRepository.class);
        CommentService commentService = new CommentService(commentRepository);

        Comment commentToSave = new Comment("Great post!", new Post());

        when(commentRepository.save(commentToSave)).thenReturn(new Comment(1L, "Great post!", new Post()));

        Comment savedComment = commentService.save(commentToSave);
        assertEquals(1L, savedComment.getId());
        assertEquals("Great post!", savedComment.getText());
    }
}