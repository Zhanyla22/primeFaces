package org.xtremebiker.jsfspring.service;

import org.xtremebiker.jsfspring.dto.request.AddNewPayment;
import org.xtremebiker.jsfspring.dto.response.PaymentDto;

import java.util.List;

public interface PaymentServiceService {

    List<PaymentDto> getAllPayment();

    String addNewPayment(AddNewPayment addNewPayment);

    List<PaymentDto> getAllPaymentByUserId(Long userId);
}
