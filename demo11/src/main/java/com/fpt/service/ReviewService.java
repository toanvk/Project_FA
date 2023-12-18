package com.fpt.service;

import com.fpt.dto.ReviewDto;
import com.fpt.model.Review;

import java.util.List;

public interface ReviewService {

    Review findById(Long id);
    ReviewDto createReview(ReviewDto review);

    ReviewDto updateReview(Long id, ReviewDto review);

    boolean deleteReview(Long id);

    List<ReviewDto> listAllReview();

    List<ReviewDto> listReviewByLaptopId(Long id);

    List<ReviewDto> listReviewByUserId(Long id);
}
