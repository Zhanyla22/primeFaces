package org.com.jsfspring.service.impl;

import lombok.RequiredArgsConstructor;
import org.com.jsfspring.dto.request.AddNewPayment;
import org.com.jsfspring.dto.response.AddPaymentResponse;
import org.com.jsfspring.entity.UserEntity;
import org.com.jsfspring.exceptions.BaseException;
import org.com.jsfspring.mapper.PaymentMapper;
import org.com.jsfspring.repo.UserRepo;
import org.com.jsfspring.security.jwt.JWTService;
import org.com.jsfspring.service.PaymentServiceService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.com.jsfspring.dto.response.PaymentDto;
import org.com.jsfspring.entity.Payment;
import org.com.jsfspring.repo.PaymentRepo;
import org.com.jsfspring.service.PaymentService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class PaymentServiceServiceImpl implements PaymentServiceService {

    private final PaymentRepo paymentRepo;
    private final UserRepo userRepo;
    private final UserDetailServiceImpl userDetailService;
    private final JWTService jwtService;

    public static PaymentDto convert(PaymentService paymentService) {
        return new PaymentDto(
                paymentService.getId(),
                paymentService.getFirstName(),
                paymentService.getCreatedDate(),
                paymentService.getSum()
        );
    }

    @Override
    public List<PaymentDto> getAllPayment() {
        List<PaymentService> paymentServices = paymentRepo.getAllPayment();
        List<PaymentDto> paymentDtos = new ArrayList<>();
        for (PaymentService p : paymentServices) {
            PaymentDto paymentDto = convert(p);
            paymentDtos.add(paymentDto);
        }
        return paymentDtos;
    }

    @Override
    public AddPaymentResponse addNewPayment(AddNewPayment addNewPayment) {
        UserEntity userEntity = userRepo.findById(addNewPayment.getUserId()).orElseThrow(
                () -> new BaseException("пользователь с id = "+addNewPayment.getUserId()+" не найден", HttpStatus.NOT_FOUND)
        );
        if (addNewPayment.getMoney() > 0) {
            Payment payment = new Payment();
            payment.setUserEntity(userEntity);
            payment.setSum(addNewPayment.getMoney());
            payment.setUuid(UUID.randomUUID());
            paymentRepo.save(payment);
            return PaymentMapper.entityToPaymentDto(payment);
        }
        else throw new BaseException("оплата не добавлена, сумма должна быть больше 0", HttpStatus.BAD_REQUEST);
    }

    @Override
    public List<PaymentDto> getAllPaymentByCurrentUser(String jwt) {
        String token = jwt.substring(7); //substring проверка
        if (!jwtService.isTokenExpired(token)) {
            UserEntity userEntity = (UserEntity) userDetailService.loadUserByUsername(jwtService.extractUserName(token));
            List<PaymentService> paymentServices = paymentRepo.getAllPaymentByUserId(userEntity.getId());
            List<PaymentDto> paymentDtos = new ArrayList<>();
            for (PaymentService p : paymentServices) {
                PaymentDto paymentDto = convert(p);
                paymentDtos.add(paymentDto);
            }
            return paymentDtos;
        } else
            return null;
    }
}
