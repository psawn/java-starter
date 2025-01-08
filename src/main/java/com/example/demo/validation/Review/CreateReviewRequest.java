package com.example.demo.validation.Review;

import lombok.Data;

import java.util.List;

@Data
public class CreateReviewRequest {
    private List<ReviewRequest> reviews;
}
