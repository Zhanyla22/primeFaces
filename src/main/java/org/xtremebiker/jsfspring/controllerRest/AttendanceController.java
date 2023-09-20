package org.xtremebiker.jsfspring.controllerRest;

import lombok.RequiredArgsConstructor;
import org.xtremebiker.jsfspring.controllerRest.base.BaseController;
import org.xtremebiker.jsfspring.entity.UserEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.xtremebiker.jsfspring.dto.request.AddDelayDto;
import org.xtremebiker.jsfspring.dto.request.UpdateAttendance;
import org.xtremebiker.jsfspring.dto.response.ResponseDto;
import org.xtremebiker.jsfspring.enums.Status;
import org.xtremebiker.jsfspring.service.AttendRecordService;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/attendance")
public class AttendanceController extends BaseController {

    private final AttendRecordService attendRecordService;

    @GetMapping("/all")
    public ResponseEntity<ResponseDto> getAllAttendance() {
        return constructSuccessResponse(attendRecordService.getAllAttendance(Status.ACTIVE));
    }

    @PostMapping("/add")
    public ResponseEntity<ResponseDto> addNewAttendance(@RequestBody AddDelayDto addDelayDto) {
        return constructSuccessResponse(attendRecordService.addDelayMin(addDelayDto));
    }

    @PostMapping("/delete/{uuid}")
    public ResponseEntity<ResponseDto> deleteAttendance(@PathVariable UUID uuid) {
        return constructSuccessResponse(attendRecordService.deleteById(uuid));
    }

    @PostMapping("/update")
    public ResponseEntity<ResponseDto> updateAttendance(@RequestBody UpdateAttendance updateAttendance) {
        return constructSuccessResponse(attendRecordService.updateAttendanceById(updateAttendance));
    }

    @GetMapping("/get-sum-loan-history")
    public ResponseEntity<ResponseDto> getAllSumPaymentHistory() {
        return constructSuccessResponse(attendRecordService.getAllLoanHistory());
    }

    @GetMapping("/get-sum-attendance")
    public ResponseEntity<ResponseDto> getAllSumAttendanceMoney() {
        return constructSuccessResponse(attendRecordService.getAllSum());
    }

    @GetMapping("/get-current-user-attendances")
    public ResponseEntity<ResponseDto> getAllAttendanceByUser() {
        return constructSuccessResponse(attendRecordService.getAllAttendanceByUser());
    }

    @GetMapping("/get-current-user-loan-sum")
    public ResponseEntity<ResponseDto> getAllSumByUser() {
        return constructSuccessResponse(attendRecordService.getAllSumByUser());
    }

    @GetMapping("/get-current-user-paid-sum")
    public ResponseEntity<ResponseDto> getAllPaidSum() {
        return constructSuccessResponse(attendRecordService.getBalanceByUser());
    }

    @GetMapping("/get-current-user-left")
    public ResponseEntity<ResponseDto> getCurrentUsersLeft() {
        return constructSuccessResponse(attendRecordService.getCurrentUsersLeft());
    }
}
