package org.com.jsfspring.controllerJsf;

import org.com.jsfspring.dto.request.AddNewPaymentRequest;
import org.com.jsfspring.service.PaymentServiceService;
import org.com.jsfspring.service.impl.UserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.com.jsfspring.dto.response.PaymentResponse;

import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@Component
@Scope("view")
public class PaymentController {

    private PaymentResponse paymentResponse;

    private AddNewPaymentRequest addNewPaymentRequest;

    @Autowired
    private UserDetailServiceImpl userDetailsService;

    @Autowired
    private PaymentServiceService paymentServiceService;

    @PostConstruct
    public void init() {
        paymentResponse = new PaymentResponse();
        addNewPaymentRequest = new AddNewPaymentRequest();
    }

    public PaymentResponse getPaymentDto() {
        return paymentResponse;
    }

    public AddNewPaymentRequest getAddNewPayment() {
        return addNewPaymentRequest;
    }

    public List<PaymentResponse> getAllPayment() {
        return paymentServiceService.getAllPayment();
    }

//    public List<PaymentDto> getAllPaymentById() {
//        UserEntity userEntity = userDetailsService.getCurrentUser();
//        return paymentServiceService.getAllPaymentByCurrentUser();
//    }

    public void add() {
        paymentServiceService.addNewPayment(addNewPaymentRequest);
    }

    public void reload() throws IOException {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
    }
}
