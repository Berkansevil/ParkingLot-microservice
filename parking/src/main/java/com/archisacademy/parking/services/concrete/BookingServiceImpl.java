package com.archisacademy.parking.services.concrete;

import com.archisacademy.parking.dtos.request.BookingRequest;
import com.archisacademy.parking.dtos.response.BookingHistoryResponse;
import com.archisacademy.parking.dtos.response.BookingResponse;
import com.archisacademy.parking.model.Booking;
import com.archisacademy.parking.modelmapper.ModelMapperService;
import com.archisacademy.parking.repositories.BookingRepository;
import com.archisacademy.parking.repositories.ParkingSpotRepository;
import com.archisacademy.parking.repositories.UserRepository;
import com.archisacademy.parking.services.abstracts.BookingService;
import org.modelmapper.ModelMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final ModelMapperService modelMapperService;
    private final UserRepository userRepository;
    private final ParkingSpotRepository parkingSpotRepository;
    private final ModelMapper modelMapper;

    public BookingServiceImpl(BookingRepository bookingRepository, ModelMapperService modelMapperService, UserRepository userRepository, ParkingSpotRepository parkingSpotRepository,
                              ModelMapper modelMapper) {
        this.bookingRepository = bookingRepository;
        this.modelMapperService = modelMapperService;
        this.userRepository = userRepository;
        this.parkingSpotRepository = parkingSpotRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public BookingResponse bookParkingSpot(BookingRequest request){
        Booking booking = modelMapperService.request().map(request, Booking.class);
        bookingRepository.save(booking);
        BookingResponse response = modelMapperService.response().map(booking, BookingResponse.class);
        return response;
    }

    @Override
    public BookingResponse getBookingById(Long id){
        Booking booking = bookingRepository.findById(id).orElseThrow(()-> new RuntimeException("booking cannot be found!!"));
        BookingResponse response = modelMapperService.response().map(booking, BookingResponse.class);
        return response;
    }

    @Scheduled(cron = "0 0 8 * * ?")
    @Override
    public List<BookingHistoryResponse> userBookings(Long id){
        List<Booking> bookings = bookingRepository.findByUserId(id);
        if (bookings.isEmpty()){
            throw new RuntimeException("No booking can be found!!");
        }
        return bookings.stream().map(booking -> modelMapper.map(booking, BookingHistoryResponse.class)).collect(Collectors.toList());
    }
}
