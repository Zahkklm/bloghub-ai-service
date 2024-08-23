package com.patika.bloghubservice.service;

import com.patika.bloghubservice.client.email.EmailServiceClient;
import com.patika.bloghubservice.client.payment.PaymentServiceClient;
import com.patika.bloghubservice.converter.UserConverter;
import com.patika.bloghubservice.dto.enums.EmailTemplate;
import com.patika.bloghubservice.dto.request.ChangePasswordRequest;
import com.patika.bloghubservice.dto.request.EmailCreateRequest;
import com.patika.bloghubservice.dto.request.PaymentRequest;
import com.patika.bloghubservice.dto.request.UserSaveRequest;
import com.patika.bloghubservice.dto.response.BlogResponse;
import com.patika.bloghubservice.dto.response.GenericResponse;
import com.patika.bloghubservice.dto.response.UserResponse;
import com.patika.bloghubservice.exception.BlogHubException;
import com.patika.bloghubservice.exception.ExceptionMessages;
import com.patika.bloghubservice.model.User;
import com.patika.bloghubservice.model.enums.StatusType;
import com.patika.bloghubservice.model.enums.UserType;
import com.patika.bloghubservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final EmailServiceClient emailServiceClient;
    private final PaymentServiceClient paymentServiceClient;

    public UserService(UserRepository userRepository, EmailServiceClient emailServiceClient, PaymentServiceClient paymentServiceClient) {
        this.userRepository = userRepository;
        this.emailServiceClient = emailServiceClient;
        this.paymentServiceClient = paymentServiceClient;
    }

    public UserResponse saveUser(UserSaveRequest request) {
        if (request.getEmail() == null) {
            log.error("request: {},", request + "\n" + ExceptionMessages.USER_EMAIL_CAN_NOT_BE_EMPTY);
            throw new BlogHubException(ExceptionMessages.USER_EMAIL_CAN_NOT_BE_EMPTY);
        }

        Optional<User> optionalUser = userRepository.findByEmail(request.getEmail());

        if (optionalUser.isPresent()) {
            throw new BlogHubException(ExceptionMessages.USER_ALREADY_DEFINED);
        } else {
            User savedUser = new User(request.getEmail(), request.getPassword());

            userRepository.save(savedUser);
            //emailServiceClient.sendEmail(new EmailCreateRequest(request.getEmail(), EmailTemplate.CREATE_USER_TEMPLATE));

            return UserConverter.toResponse(savedUser);
        }

    }

    public UserResponse getUserByEmail(String email) {

        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("user bulunamadı"));

        return UserConverter.toResponse(user);
    }

    public List<UserResponse> getAllUsers() {
        List<User> users = userRepository.findAll();
        return UserConverter.toResponse(users);
    }

    public GenericResponse<StatusType> changeStatus(String email, StatusType statusType) {
        User user = validateUser(email);

        user.setStatusType(statusType);
        userRepository.save(user);

        return GenericResponse.success(user.getStatusType(), "Kullanıcı durumu başarıyla değiştirildi");
    }

    public void changeStatusBulk(List<String> emailList, StatusType statusType) {
        emailList.forEach(email -> changeStatus(email, statusType));
    }

    public Map<String, User> getAllUsersMap() {
        return userRepository.findAll()
                .stream()
                .collect(Collectors.toMap(User::getEmail, Function.identity()));
    }

    public UserResponse changePassword(String email, ChangePasswordRequest changePasswordRequest) {
        User foundUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new BlogHubException(ExceptionMessages.USER_NOT_FOUND));

        if (!foundUser.getPassword().equals(changePasswordRequest.currentPassword())) {
            throw new BlogHubException(ExceptionMessages.CURRENT_PASSWORD_DOES_NOT_MATCH);
        }

        foundUser.setPassword(changePasswordRequest.newPassword());

        userRepository.save(foundUser);

        return UserConverter.toResponse(foundUser);
    }

    public User validateUser(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new BlogHubException(ExceptionMessages.USER_NOT_FOUND));
    }
    public User validateUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new BlogHubException(ExceptionMessages.USER_NOT_FOUND));
    }

    public GenericResponse<UserType> changeUserType(String email, UserType userType) {
        User user = validateUser(email);

        if (userType.equals(UserType.PREMIUM)) {
            paymentServiceClient.createPayment(new PaymentRequest(new BigDecimal("9.99"),email));

            user.setUserType(userType);
        } else {
            user.setUserType(UserType.STANDARD);
        }

        userRepository.save(user);

        return GenericResponse.success(user.getUserType(), "Kullanıcı türü başarıyla değiştirildi");
    }
}
