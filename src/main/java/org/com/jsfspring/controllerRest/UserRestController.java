package org.com.jsfspring.controllerRest;

import lombok.RequiredArgsConstructor;
import org.com.jsfspring.controllerRest.base.BaseController;
import org.com.jsfspring.dto.request.AddUserRequest;
import org.com.jsfspring.dto.request.AuthRequest;
import org.com.jsfspring.dto.request.UpdatePassRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.com.jsfspring.dto.response.ResponseDto;
import org.com.jsfspring.service.UserService;
import org.com.jsfspring.util.AESUtil;

/**
 * RestController users
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserRestController extends BaseController {

    private final UserService userService;

    private final AESUtil aesUtil;

    private final PasswordEncoder passwordEncoder;
    //    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<ResponseDto> getAllUsers() {
        return constructSuccessResponse(userService.getAllUsers());
    }

    @PostMapping("/auth")
    public ResponseEntity<ResponseDto> auth(@RequestBody AuthRequest authRequest) {
        return constructSuccessResponse(userService.auth(authRequest));
    }

    @PostMapping("/encrypt")
    public ResponseEntity<ResponseDto> auth2(@RequestParam String pass) {
        return constructSuccessResponse(aesUtil.encrypt(pass));
    }

    @GetMapping("/refresh")
    public ResponseEntity<ResponseDto> refreshToken(@RequestHeader("Authorization") String token) {
        return constructSuccessResponse(userService.refreshToken(token));
    }

    @PostMapping("/add-user")
    public ResponseEntity<ResponseDto> addNewUser(@RequestBody AddUserRequest addUserRequest){
        return constructSuccessResponse(userService.addNewUser(addUserRequest));
    }

    @PostMapping("/update-pass")
    public ResponseEntity<ResponseDto> updatePassword(@RequestBody UpdatePassRequest updatePassRequest, @RequestHeader("Authorization") String token ) {
        return constructSuccessResponse(userService.updatePass(updatePassRequest, token));
    }
}

