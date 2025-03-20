package com.archisacademy.parking.controllers;

import com.archisacademy.parking.ApiResponse.ApiResponse;
import com.archisacademy.parking.dtos.request.ReviewRequest;
import com.archisacademy.parking.dtos.response.ReviewResponse;
import com.archisacademy.parking.services.abstracts.ReviewService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ReviewResponse>> createReview(@Valid @RequestBody ReviewRequest reviewRequest){
        ApiResponse<ReviewResponse> response = reviewService.createReview(reviewRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ReviewResponse>> getReviewById(@PathVariable Long id){
        ReviewResponse review = reviewService.getReviewById(id).getData();
        return ResponseEntity.ok(new ApiResponse<>(true, "Review found", review));
    }

    @GetMapping("/parkingLot")
    public ResponseEntity<ApiResponse<List<ReviewResponse>>> getReviewsByParkingLotId(@RequestParam Long parkingLotId){
        List<ReviewResponse> reviews = reviewService.getReviewsByParkingLotId(parkingLotId).getData();
        return ResponseEntity.ok(new ApiResponse<>(true, "Reviews found", reviews));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteReview(@PathVariable Long id){
        ApiResponse<String> response = reviewService.delete(id);
        return ResponseEntity.ok(response);
    }
}
