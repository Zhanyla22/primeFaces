package org.com.jsfspring.service.impl;

import lombok.RequiredArgsConstructor;
import org.com.jsfspring.dto.request.AddNewPaymentRequest;
import org.com.jsfspring.dto.response.AddPaymentResponse;
import org.com.jsfspring.dto.response.PaymentResponse;
import org.com.jsfspring.dto.response.TokenValidResponse;
import org.com.jsfspring.entity.Payment;
import org.com.jsfspring.entity.UserEntity;
import org.com.jsfspring.exceptions.BaseException;
import org.com.jsfspring.mapper.PaymentMapper;
import org.com.jsfspring.repo.PaymentRepo;
import org.com.jsfspring.repo.UserRepo;
import org.com.jsfspring.security.jwt.JWTService;
import org.com.jsfspring.service.PaymentService;
import org.com.jsfspring.service.PaymentServiceService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class PaymentServiceServiceImpl implements PaymentServiceService {

    private final PaymentRepo paymentRepo;
    private final UserRepo userRepo;
    private final JWTService jwtService;

    /**
     *    Конвертация сервиса в дто
     */
    public static PaymentResponse convert(PaymentService paymentService) {
        return new PaymentResponse(
                paymentService.getId(),
                paymentService.getFirstName(),
                paymentService.getCreatedDate(),
                paymentService.getSum()
        );
    }

    /**
     * получение всех оплат сотрудников
     * @return лист PaymentResponse
     */
    @Override
    public List<PaymentResponse> getAllPayment() {
        List<PaymentService> paymentServices = paymentRepo.getAllPayment();
        List<PaymentResponse> paymentResponses = new ArrayList<>();
        for (PaymentService p : paymentServices) {
            PaymentResponse paymentResponse = convert(p);
            paymentResponses.add(paymentResponse);
        }
        return paymentResponses;
    }

    /**
     * добавление новой оплаты
     * @param addNewPaymentRequest
     * @return AddPaymentResponse
     */
    @Override
    public AddPaymentResponse addNewPayment(AddNewPaymentRequest addNewPaymentRequest) {
        UserEntity userEntity = userRepo.findById(addNewPaymentRequest.getUserId()).orElseThrow(
                () -> new BaseException("пользователь с id = " + addNewPaymentRequest.getUserId() + " не найден", HttpStatus.NOT_FOUND)
        );
        if (addNewPaymentRequest.getMoney() > 0) {
            Payment payment = new Payment();
            payment.setUserEntity(userEntity);
            payment.setSum(addNewPaymentRequest.getMoney());
            payment.setUuid(UUID.randomUUID());
            paymentRepo.save(payment);
            return PaymentMapper.entityToPaymentDto(payment);
        } else throw new BaseException("оплата не добавлена, сумма должна быть больше 0", HttpStatus.BAD_REQUEST);
    }

    /**
     * получение всех оплат сотрудника по jwt
     * @param jwt
     * @return лист PaymentResponse
     */
    @Override
    public List<PaymentResponse> getAllPaymentByCurrentUser(String jwt) {
        TokenValidResponse token = jwtService.validToken(jwt);
        UserEntity userEntity = userRepo.findByUserName(token.getUserName()).orElseThrow(
                () -> new BaseException("пользрватель с username =" + token.getUserName() + "не найден ", HttpStatus.NOT_FOUND)
        );
        List<PaymentService> paymentServices = paymentRepo.getAllPaymentByUserId(userEntity.getId());
        List<PaymentResponse> paymentResponses = new ArrayList<>();
        for (PaymentService p : paymentServices) {
            PaymentResponse paymentResponse = convert(p);
            paymentResponses.add(paymentResponse);
        }
        return paymentResponses;
    }
}
