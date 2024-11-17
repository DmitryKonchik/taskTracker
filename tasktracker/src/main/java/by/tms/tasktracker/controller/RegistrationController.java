package by.tms.tasktracker.controller;

import by.tms.tasktracker.dto.UserRegistrationDto;
import by.tms.tasktracker.servise.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class RegistrationController {

    private final UserService userService;


    @Operation(
            summary = "Add new user",
            description = "Get UserRegistrationDTO and reg new User"
    )
    @PostMapping("/reg")
    public ResponseEntity<UserRegistrationDto> registration(@RequestBody UserRegistrationDto userRegistrationDto) {
        return ResponseEntity.ok(userService.register(userRegistrationDto));
    }
}
