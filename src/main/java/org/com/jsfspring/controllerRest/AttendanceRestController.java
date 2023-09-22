package org.com.jsfspring.controllerRest;

import lombok.RequiredArgsConstructor;
import org.com.jsfspring.controllerRest.base.BaseController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.com.jsfspring.dto.request.AddDelayDto;
import org.com.jsfspring.dto.request.UpdateAttendance;
import org.com.jsfspring.dto.response.ResponseDto;
import org.com.jsfspring.enums.Status;
import org.com.jsfspring.service.AttendRecordService;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/attendance")
public class AttendanceRestController extends BaseController {

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
    public ResponseEntity<ResponseDto> getAllAttendanceByUser(@RequestHeader("Authorization") String token) {
        return constructSuccessResponse(attendRecordService.getAllAttendanceByUser(token));
    }

    @GetMapping("/get-current-user-loan-sum")
    public ResponseEntity<ResponseDto> getAllSumByUser(@RequestHeader("Authorization") String token) {
        return constructSuccessResponse(attendRecordService.getAllSumByUser(token));
    }

    @GetMapping("/get-current-user-paid-sum")
    public ResponseEntity<ResponseDto> getAllPaidSum(@RequestHeader("Authorization") String token) {
        return constructSuccessResponse(attendRecordService.getBalanceByUser(token));
    }

    @GetMapping("/get-current-user-left")
    public ResponseEntity<ResponseDto> getCurrentUsersLeft(@RequestHeader("Authorization") String token) {
        return constructSuccessResponse(attendRecordService.getCurrentUsersLeft(token));
    }
}
