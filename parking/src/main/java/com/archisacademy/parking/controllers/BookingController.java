package com.archisacademy.parking.controllers;

import com.archisacademy.parking.dtos.request.BookingRequest;
import com.archisacademy.parking.dtos.response.BookingHistoryResponse;
import com.archisacademy.parking.dtos.response.BookingResponse;
import com.archisacademy.parking.model.Booking;
import com.archisacademy.parking.services.abstracts.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/booking")
public class BookingController {
    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/save")
    public ResponseEntity<BookingResponse> createBooking(@RequestBody BookingRequest request){
        BookingResponse response = bookingService.bookParkingSpot(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingResponse> getBookingById(@PathVariable Long id){
        BookingResponse bookingResponse = bookingService.getBookingById(id);
        return ResponseEntity.ok(bookingResponse);
    }



}
