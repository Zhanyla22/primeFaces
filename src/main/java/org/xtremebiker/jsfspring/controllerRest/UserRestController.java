package org.xtremebiker.jsfspring.controllerRest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xtremebiker.jsfspring.controllerRest.base.BaseController;
import org.xtremebiker.jsfspring.dto.response.ResponseDto;
import org.xtremebiker.jsfspring.service.UserService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserRestController extends BaseController {

    private final UserService userService;

    @GetMapping("/all")
    public ResponseEntity<ResponseDto> getAllUsers(){
        return constructSuccessResponse(userService.getAllUsers());
    }
}

