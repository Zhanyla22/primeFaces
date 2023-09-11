package org.xtremebiker.jsfspring.controllerRest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.xtremebiker.jsfspring.controllerRest.base.BaseController;
import org.xtremebiker.jsfspring.dto.request.AddDelayDto;
import org.xtremebiker.jsfspring.dto.request.UpdateAttendance;
import org.xtremebiker.jsfspring.dto.response.ResponseDto;
import org.xtremebiker.jsfspring.enums.Status;
import org.xtremebiker.jsfspring.service.AttendRecordService;

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
    public ResponseEntity<ResponseDto> addNewAttendance(@RequestBody AddDelayDto addDelayDto){
        return constructSuccessResponse(attendRecordService.addDelayMin(addDelayDto));
    }

    @PostMapping("/delete/{userId}")
    public ResponseEntity<ResponseDto> deleteAttendance(@PathVariable Long userId){
        return constructSuccessResponse(attendRecordService.deleteById(userId));
    }

    @PostMapping("/update")
    public ResponseEntity<ResponseDto> updateAttendance(@RequestBody UpdateAttendance updateAttendance){
        return constructSuccessResponse(attendRecordService.updateAttendanceById(updateAttendance));
    }

    @GetMapping("/get-sum-loan-history")
    public ResponseEntity<ResponseDto> getAllSumPaymentHistory(){
        return constructSuccessResponse(attendRecordService.getAllLoanHistory());
    }

    @GetMapping("/get-sum-attendance")
    public ResponseEntity<ResponseDto> getAllSumAttendanceMoney(){
        return constructSuccessResponse(attendRecordService.getAllSum());
    }

}
