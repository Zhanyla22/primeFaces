package org.xtremebiker.jsfspring.controllerJsf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.xtremebiker.jsfspring.dto.request.AddNewPayment;
import org.xtremebiker.jsfspring.dto.response.PaymentDto;
import org.xtremebiker.jsfspring.entity.UserEntity;
import org.xtremebiker.jsfspring.service.PaymentServiceService;
import org.xtremebiker.jsfspring.service.impl.UserDetailServiceImpl;

import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@Component
@Scope("view")
public class PaymentController {

    private PaymentDto paymentDto;

    private AddNewPayment addNewPayment;

    @Autowired
    private UserDetailServiceImpl userDetailsService;

    @Autowired
    private PaymentServiceService paymentServiceService;

    @PostConstruct
    public void init() {
        paymentDto = new PaymentDto();
        addNewPayment = new AddNewPayment();
    }

    public PaymentDto getPaymentDto() {
        return paymentDto;
    }

    public AddNewPayment getAddNewPayment() {
        return addNewPayment;
    }

    public List<PaymentDto> getAllPayment() {
        return paymentServiceService.getAllPayment();
    }

    public List<PaymentDto> getAllPaymentById() {
        UserEntity userEntity = userDetailsService.getCurrentUser();
        return paymentServiceService.getAllPaymentByUserId(userEntity.getId());
    }

    public void add() {
        paymentServiceService.addNewPayment(addNewPayment);
    }

    public void reload() throws IOException {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
    }
}
