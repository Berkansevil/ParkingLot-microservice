package com.archisacademy.parking.services.concrete;

import com.archisacademy.parking.ApiResponse.ApiResponse;
import com.archisacademy.parking.dtos.request.ReviewRequest;
import com.archisacademy.parking.dtos.response.ReviewResponse;
import com.archisacademy.parking.model.ParkingLot;
import com.archisacademy.parking.model.Review;
import com.archisacademy.parking.modelmapper.ModelMapperService;
import com.archisacademy.parking.repositories.ParkingLotRepository;
import com.archisacademy.parking.repositories.ReviewRepository;
import com.archisacademy.parking.services.abstracts.ReviewService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ParkingLotRepository parkingLotRepository;
    private final ModelMapperService modelMapperService;

    public ReviewServiceImpl(ReviewRepository reviewRepository, ParkingLotRepository parkingLotRepository, ModelMapperService modelMapperService) {
        this.reviewRepository = reviewRepository;
        this.parkingLotRepository = parkingLotRepository;
        this.modelMapperService = modelMapperService;
    }

    @Override
    public ApiResponse<ReviewResponse> createReview(ReviewRequest reviewRequest) {
        Review review = modelMapperService.request().map(reviewRequest, Review.class);
        ParkingLot parkingLot = parkingLotRepository.findById(reviewRequest.getParkingLotId())
                .orElseThrow(() -> new RuntimeException("Parking lot not found"));
        review.setParkingLot(parkingLot);
        Review savedReview = reviewRepository.save(review);
        ReviewResponse reviewResponse = modelMapperService.response().map(savedReview, ReviewResponse.class);

        return new ApiResponse<>(true, "Review added successfully!", reviewResponse);
    }

    @Override
    public ApiResponse<ReviewResponse> getReviewById(Long id){
        Review review = getById(id);
        ReviewResponse reviewResponse = modelMapperService.response().map(review, ReviewResponse.class);
        return new ApiResponse<>(true, "Review found", reviewResponse);
    }

    @Override
    public ApiResponse<List<ReviewResponse>> getReviewsByParkingLotId(Long parkingLotId){
        List<Review> reviews = reviewRepository.findAllByParkingLotId(parkingLotId);
        List<ReviewResponse> reviewResponses = reviews.stream().map(review ->
                modelMapperService.response().map(review, ReviewResponse.class))
                .collect(Collectors.toList());
        return new ApiResponse<>(true, "Reviews found", reviewResponses);
    }

    @Override
    public ApiResponse<String> delete(Long reviewId) {
        Review review = getById(reviewId);
        reviewRepository.delete(review);
        return new ApiResponse<>(true, "Review deleted successfully");
    }


    public Review getById(Long id){
        return reviewRepository.findById(id).orElseThrow(()-> new RuntimeException("Review cannot be found!!!" + id));
    }
}
