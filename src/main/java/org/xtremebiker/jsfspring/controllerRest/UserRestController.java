package org.xtremebiker.jsfspring.controllerRest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.xtremebiker.jsfspring.controllerRest.base.BaseController;
import org.xtremebiker.jsfspring.dto.request.AddUserDto;
import org.xtremebiker.jsfspring.dto.request.UpdatePassRequest;
import org.xtremebiker.jsfspring.dto.response.ResponseDto;
import org.xtremebiker.jsfspring.service.UserService;
import org.xtremebiker.jsfspring.util.AESUtil;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

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
    public ResponseEntity<ResponseDto> auth(@RequestParam String userName, @RequestParam String pass) {
        return constructSuccessResponse(userService.auth(userName, pass));
    }

    @PostMapping("/encrypt")
    public ResponseEntity<ResponseDto> auth2(@RequestParam String pass) {
        return constructSuccessResponse(aesUtil.encrypt(pass));
    }

    @GetMapping("/refresh")
    public ResponseEntity<ResponseDto> refreshToken() {
        return constructSuccessResponse(userService.refreshToken());
    }

    @PostMapping("/add-user")
    public ResponseEntity<ResponseDto> addNewUser(@RequestBody AddUserDto addUserDto) throws NoSuchAlgorithmException, InvalidKeySpecException {
        return constructSuccessResponse(userService.addNewUser(addUserDto));
    }

    @PostMapping("/update-pass")
    public ResponseEntity<ResponseDto> updatePassword(@RequestBody UpdatePassRequest updatePassRequest) {
        return constructSuccessResponse(userService.updatePass(updatePassRequest));
    }
}
//get post разница AUthApi Example
