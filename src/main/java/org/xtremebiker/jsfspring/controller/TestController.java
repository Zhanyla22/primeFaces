package org.xtremebiker.jsfspring.controller;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.xtremebiker.jsfspring.dto.response.AllAttendance;
import org.xtremebiker.jsfspring.service.AttendRecordService;

import javax.annotation.PostConstruct;
import java.util.List;


//@RestController
//@RequestMapping("/test")
@Component
@Scope("view")
public class TestController {

    private final AttendRecordService attendRecordService;

    public TestController(AttendRecordService attendRecordService) {
        this.attendRecordService = attendRecordService;
    }

    @PostConstruct
    public void init() {
        System.out.println("start init");
    }

    //    @PostMapping("/add")
//    public ResponseEntity<ResponseDto> add(AddDelayDto addDelayDto) {
//        return constructSuccessResponse(attendRecordService.addDelayMin(addDelayDto));
//    }
//
//    //    @GetMapping("/{id}")
//    public List<DelayUserDto> getAllByUser(Long id) {
//        return attendRecordService.getAllByUser(id);
//    }

    public List<AllAttendance> getAllUsers() {
        return attendRecordService.getAllAttendance();
    }


}
