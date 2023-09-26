package org.com.jsfspring.mapper;

import org.com.jsfspring.dto.response.AddPaymentResponse;
import org.com.jsfspring.entity.Payment;
import org.springframework.stereotype.Service;

/**
 * маппер для оброботки записей оплаты
 */
@Service
public class PaymentMapper {

    /**
     * @param payment
     * @return AddPaymentResponse
     */
    public static AddPaymentResponse entityToPaymentDto(Payment payment) {
        return AddPaymentResponse.builder()
                .id(payment.getId())
                .uuid(payment.getUuid().toString())
                .date(payment.getCreatedDate().toLocalDate())
                .sum(payment.getSum())
                .userFirstName(payment.getUserEntity().getFirstName())
                .build();
    }
}
