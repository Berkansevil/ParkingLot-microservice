package com.archisacademy.parking.services.abstracts;

import com.archisacademy.parking.dtos.request.BookingRequest;
import com.archisacademy.parking.dtos.response.BookingHistoryResponse;
import com.archisacademy.parking.dtos.response.BookingResponse;
import com.archisacademy.parking.model.Booking;

import java.util.List;

public interface BookingService {
    BookingResponse bookParkingSpot(BookingRequest request);

    BookingResponse getBookingById(Long id);

    List<BookingHistoryResponse> userBookings(Long id);
}
