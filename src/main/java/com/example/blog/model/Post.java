package com.example.blog.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@Builder
@AllArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Transient
    private Double averageRating;

    @NotNull(message = "Title cannot be null")
    String title;

    @NotNull(message = "The post's body cannot be null")
    @Column(columnDefinition = "TEXT")
    String body;

    LocalDateTime createdAt;
    LocalDateTime updatedAt;


    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    User user;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "categoryId")
    Category category;

    @JsonManagedReference
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Rating> ratings = new ArrayList<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "post", cascade = {CascadeType.ALL})
    private List<Comment> comments = new ArrayList<>();

}
