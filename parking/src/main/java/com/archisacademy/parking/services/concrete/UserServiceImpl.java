package com.archisacademy.parking.services.concrete;

import com.archisacademy.parking.ApiResponse.ApiResponse;
import com.archisacademy.parking.dtos.request.UserFeedbackRequest;
import com.archisacademy.parking.dtos.request.UserRequest;
import com.archisacademy.parking.dtos.request.UserUpdateRequest;
import com.archisacademy.parking.dtos.response.UserFeedbackResponse;
import com.archisacademy.parking.dtos.response.UserResponse;
import com.archisacademy.parking.dtos.response.UserUpdateResponse;
import com.archisacademy.parking.exception.user.UserExistByEmailException;
import com.archisacademy.parking.exception.user.UserNotFoundException;
import com.archisacademy.parking.model.User;
import com.archisacademy.parking.model.UserFeedback;
import com.archisacademy.parking.modelmapper.ModelMapperServiceImpl;
import com.archisacademy.parking.repositories.UserFeedbackRepository;
import com.archisacademy.parking.repositories.UserRepository;
import com.archisacademy.parking.services.abstracts.UserService;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserFeedbackRepository userFeedbackRepository;
    private final ModelMapperServiceImpl modelMapperServiceImpl;

    public UserServiceImpl(UserRepository userRepository, UserFeedbackRepository userFeedbackRepository, ModelMapperServiceImpl modelMapperServiceImpl) {
        this.userRepository = userRepository;
        this.userFeedbackRepository = userFeedbackRepository;
        this.modelMapperServiceImpl=modelMapperServiceImpl;

    }
    @Override
    public UserResponse createUser(UserRequest userRequest) {
        if (userRepository.existsByEmail(userRequest.getEmail())) {
           new UserExistByEmailException("Email already exists");
        }
        User user =modelMapperServiceImpl.request().map(userRequest, User.class);
        user.setActive(true);
        userRepository.save(user);
        UserResponse userResponse = modelMapperServiceImpl.response().map(user, UserResponse.class);
        return userResponse;
    }

    @Override
    @Scheduled(cron = "0 0 0 1/2 * ?")
    public List<UserResponse> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserResponse> userResponses=users.stream().map(user ->modelMapperServiceImpl.response().map(user,UserResponse.class))
                .collect(Collectors.toList());
        return userResponses;
    }

    @Transactional
    @Override
    public UserUpdateResponse updateUser(Long id, UserUpdateRequest userRequest) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        modelMapperServiceImpl.request().map(userRequest, user);
        userRepository.save(user);

        return new UserUpdateResponse("User successfully updated");
    }

    @Transactional
    @Override
    public String deleteUser(Long id) {
        Optional<User> user =userRepository.findById(id);
        if(user.isEmpty()){
            throw new UserNotFoundException("User not found");
        }
        userRepository.deleteById(id);
        return "User successfully deleted";
    }

    @Override
    public ResponseEntity<List<UserResponse>> searchUsers(String name, String email) {
        List<UserResponse> users = userRepository
                .findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(name, email)
                .stream()
                .map(user -> new UserResponse(
                        user.getName(),
                        user.getUsername(),
                        user.getEmail(),
                        user.getPhoneNumber(),
                        user.isActive()
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(users);
    }

    @Override
    public UserFeedbackResponse sendFeedback(UserFeedbackRequest userFeedbackRequest) {

        User user = userRepository.findById(userFeedbackRequest.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found!"));

        UserFeedback userFeedback = modelMapperServiceImpl.request().map(userFeedbackRequest, UserFeedback.class);
        userFeedback.setUser(user);

        UserFeedback savedFeedback = userFeedbackRepository.save(userFeedback);

        UserFeedbackResponse userFeedbackResponse = modelMapperServiceImpl.response().map(savedFeedback, UserFeedbackResponse.class);
        userFeedbackResponse.setUserId(savedFeedback.getUser().getId());

        return userFeedbackResponse;
    }

}
