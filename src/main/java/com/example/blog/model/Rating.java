package com.example.blog.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Value for rating cannot be null")
    @Min(value = 1, message = "Value rating should not be less than 1")
    @Max(value = 10, message = "Value rating should not be greater than 10")
    private int value;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Rating() {}

    public Rating(int value) {
        this.value = value;
    }

    public Rating(Long id, int value) {
        this.id = id;
        this.value = value;
    }
}