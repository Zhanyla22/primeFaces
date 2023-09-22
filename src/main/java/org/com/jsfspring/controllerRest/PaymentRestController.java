package org.com.jsfspring.controllerRest;

import lombok.RequiredArgsConstructor;
import org.com.jsfspring.controllerRest.base.BaseController;
import org.com.jsfspring.dto.request.AddNewPayment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.com.jsfspring.dto.response.ResponseDto;
import org.com.jsfspring.service.AttendRecordService;
import org.com.jsfspring.service.PaymentServiceService;

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
    public ResponseEntity<ResponseDto> getAllPaymentByUser(@RequestHeader("Authorization") String token) {
        return constructSuccessResponse(paymentServiceService.getAllPaymentByCurrentUser(token));
    }
}
