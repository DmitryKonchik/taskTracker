package by.tms.tasktracker.controller;

import by.tms.tasktracker.dto.UserLoginDto;
import by.tms.tasktracker.entity.User;
import by.tms.tasktracker.servise.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class LoginController {

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody UserLoginDto userLoginDto, HttpServletRequest request) {
        User authenticatedUser = userService.login(userLoginDto);

        HttpSession session = request.getSession();
        session.setAttribute("userId", authenticatedUser.getId());
        session.setAttribute("username", authenticatedUser.getUsername());
        session.setAttribute("name", authenticatedUser.getName());
        session.setAttribute("role", authenticatedUser.getRole());
        return ResponseEntity.ok(authenticatedUser);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return ResponseEntity.ok("Logout successful");
    }
}
