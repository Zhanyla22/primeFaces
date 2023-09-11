package org.xtremebiker.jsfspring.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.xtremebiker.jsfspring.dto.request.AddNewPayment;
import org.xtremebiker.jsfspring.dto.response.PaymentDto;
import org.xtremebiker.jsfspring.entity.Payment;
import org.xtremebiker.jsfspring.entity.UserEntity;
import org.xtremebiker.jsfspring.exceptions.BaseException;
import org.xtremebiker.jsfspring.repo.PaymentRepo;
import org.xtremebiker.jsfspring.repo.UserRepo;
import org.xtremebiker.jsfspring.service.PaymentService;
import org.xtremebiker.jsfspring.service.PaymentServiceService;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentServiceServiceImpl implements PaymentServiceService {

    private final PaymentRepo paymentRepo;

    private final UserRepo userRepo;

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
            paymentRepo.save(
                    Payment.builder()
                            .userEntity(userEntity)
                            .sum(addNewPayment.getMoney())
                            .build()
            );
        }
        return "null";
    }

    @Override
    public List<PaymentDto> getAllPaymentByUserId(Long userId) {
        List<PaymentService> paymentServices = paymentRepo.getAllPaymentByUserId(userId);
        List<PaymentDto> paymentDtos = new ArrayList<>();
        for (PaymentService p : paymentServices) {
            PaymentDto paymentDto = convert(p);
            paymentDtos.add(paymentDto);
        }
        return paymentDtos;
    }
}
