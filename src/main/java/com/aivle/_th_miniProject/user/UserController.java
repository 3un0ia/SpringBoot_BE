package com.aivle._th_miniProject.user;

import com.aivle._th_miniProject.user.dtos.*;
import com.aivle._th_miniProject.user.jwt.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/user/signup")
    public ResponseEntity<User> signup(@RequestBody SignupRequest request) {
        User user = userService.signup(request);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/user/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        LoginResponse response = userService.login(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/auth/refresh")
    public ResponseEntity<?> refresh(@RequestBody RefreshTokenRequest request){
        TokenResponse tokens = userService.reissue(request.getRefreshToken());
        return ResponseEntity.ok(tokens);
    }

    @PostMapping("/user/logout")
    public ResponseEntity<?> logout() {
        String email = SecurityUtil.getLoginEmail();
        userService.logout(email);
        return ResponseEntity.ok("로그아웃 완료");
    }

    @GetMapping("/user/book/{userId}")
    public void getBooks(@RequestParam("userId")Long id) {

    }

}
