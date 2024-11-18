package by.tms.tasktracker.controller;

import by.tms.tasktracker.dto.UserLoginDto;
import by.tms.tasktracker.security.JwtUtil;
import by.tms.tasktracker.servise.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class LoginController {


    @Autowired
    private final AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserLoginDto requestLoginDto) {
        UserDetails userDetails = userService.loadUserByUsername(requestLoginDto.getUsername());

        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userDetails.getUsername(), requestLoginDto.getPassword(), userDetails.getAuthorities())
        );

        String s = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(s);
    }

}
