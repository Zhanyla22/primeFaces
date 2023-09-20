package org.xtremebiker.jsfspring.controllerRest;

import lombok.RequiredArgsConstructor;
import org.xtremebiker.jsfspring.controllerRest.base.BaseController;
import org.xtremebiker.jsfspring.dto.request.AddNewPayment;
import org.xtremebiker.jsfspring.entity.UserEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.xtremebiker.jsfspring.dto.response.ResponseDto;
import org.xtremebiker.jsfspring.service.AttendRecordService;
import org.xtremebiker.jsfspring.service.PaymentServiceService;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentRestController extends BaseController {

    private final PaymentServiceService paymentServiceService;

    private final AttendRecordService attendRecordService;

    @GetMapping("/all")
    public ResponseEntity<ResponseDto> getAllRestPayment() {
        return constructSuccessResponse(paymentServiceService.getAllPayment());
    }

    @PostMapping("add")
    public ResponseEntity<ResponseDto> addNewRestPayment(@RequestBody AddNewPayment addNewPayment) {
        return constructSuccessResponse(paymentServiceService.addNewPayment(addNewPayment));
    }

    @GetMapping("/get-all-sum-payment")
    public ResponseEntity<ResponseDto> getAllSumPayment() {
        return constructSuccessResponse(attendRecordService.allBalanceSum());
    }

    @GetMapping("/get-sum-loan")
    public ResponseEntity<ResponseDto> getSumLeft() {
        return constructSuccessResponse(attendRecordService.getSumRestLeft());
    }

    @GetMapping("/get-current-user-payments")
    public ResponseEntity<ResponseDto> getAllPaymentByUser(HttpServletRequest request) {
        return constructSuccessResponse(paymentServiceService.getAllPaymentByCurrentUser(request));
    }
}
