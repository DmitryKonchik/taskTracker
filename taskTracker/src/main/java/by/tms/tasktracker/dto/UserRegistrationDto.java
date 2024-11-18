package by.tms.tasktracker.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegistrationDto {
    private String username;
    private String name;
    private String password;
    private String email;
}
