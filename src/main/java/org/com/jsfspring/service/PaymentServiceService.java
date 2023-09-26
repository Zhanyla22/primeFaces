package org.com.jsfspring.service;

import org.com.jsfspring.dto.request.AddNewPaymentRequest;
import org.com.jsfspring.dto.response.AddPaymentResponse;
import org.com.jsfspring.dto.response.PaymentResponse;

import java.util.List;

public interface PaymentServiceService {

    List<PaymentResponse> getAllPayment();

    AddPaymentResponse addNewPayment(AddNewPaymentRequest addNewPaymentRequest);

    List<PaymentResponse> getAllPaymentByCurrentUser(String token);
}
