package com.archisacademy.parking.services.abstracts;

import com.archisacademy.parking.ApiResponse.ApiResponse;
import com.archisacademy.parking.dtos.request.ReviewRequest;
import com.archisacademy.parking.dtos.response.ReviewResponse;
import com.archisacademy.parking.model.Review;

import java.util.List;

public interface ReviewService {
    ApiResponse<ReviewResponse> createReview(ReviewRequest reviewRequest);

    ApiResponse<ReviewResponse> getReviewById(Long id);

    ApiResponse<List<ReviewResponse>> getReviewsByParkingLotId(Long parkingLotId);

    ApiResponse<String> delete(Long reviewId);
}
