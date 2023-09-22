package org.com.jsfspring.service;

import org.com.jsfspring.dto.request.AddNewPayment;
import org.com.jsfspring.dto.response.AddPaymentResponse;
import org.com.jsfspring.dto.response.PaymentDto;

import java.util.List;

public interface PaymentServiceService {

    List<PaymentDto> getAllPayment();

    AddPaymentResponse addNewPayment(AddNewPayment addNewPayment);

    List<PaymentDto> getAllPaymentByCurrentUser(String token);
}
