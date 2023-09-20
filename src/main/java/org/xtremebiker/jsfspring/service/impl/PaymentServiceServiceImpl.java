package org.xtremebiker.jsfspring.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.xtremebiker.jsfspring.dto.request.AddNewPayment;
import org.xtremebiker.jsfspring.entity.UserEntity;
import org.xtremebiker.jsfspring.exceptions.BaseException;
import org.xtremebiker.jsfspring.repo.UserRepo;
import org.xtremebiker.jsfspring.security.jwt.JWTService;
import org.xtremebiker.jsfspring.service.PaymentServiceService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.xtremebiker.jsfspring.dto.response.PaymentDto;
import org.xtremebiker.jsfspring.entity.Payment;
import org.xtremebiker.jsfspring.repo.PaymentRepo;
import org.xtremebiker.jsfspring.service.PaymentService;

import javax.servlet.http.HttpServletRequest;
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
    public String addNewPayment(AddNewPayment addNewPayment) {
        UserEntity userEntity = userRepo.findById(addNewPayment.getUserId()).orElseThrow(
                () -> new BaseException("не найден", HttpStatus.NOT_FOUND)
        );
        if (addNewPayment.getMoney() != 0) {
            Payment payment = new Payment();
            payment.setUserEntity(userEntity);
            payment.setSum(addNewPayment.getMoney());
            payment.setUuid(UUID.randomUUID());
            paymentRepo.save(payment);

        }
        return "null";
    }

    @Override
    public List<PaymentDto> getAllPaymentByCurrentUser(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        String token = authHeader.substring(7);
        if (!jwtService.isTokenExpired(token)){
            UserEntity userEntity = (UserEntity) userDetailService.loadUserByUsername(jwtService.extractUserName(token));
        List<PaymentService> paymentServices = paymentRepo.getAllPaymentByUserId(userEntity.getId());
        List<PaymentDto> paymentDtos = new ArrayList<>();
        for (PaymentService p : paymentServices) {
            PaymentDto paymentDto = convert(p);
            paymentDtos.add(paymentDto);
        }
        return paymentDtos;
        }
        else
            return null;
    }
}
