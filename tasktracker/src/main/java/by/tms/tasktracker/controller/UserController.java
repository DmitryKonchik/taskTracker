package by.tms.tasktracker.controller;


import by.tms.tasktracker.entity.User;
import by.tms.tasktracker.servise.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> findAllUsers() {
        return ResponseEntity.ok(userService.findAll());
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId) {
        userService.delete(userId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{userId}")
    public ResponseEntity<User> updateUser(@RequestBody User user, @PathVariable Long userId) {
        return ResponseEntity.ok(userService.updateUser(userId, user));
    }

}
