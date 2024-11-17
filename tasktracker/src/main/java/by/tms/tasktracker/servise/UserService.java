package by.tms.tasktracker.servise;
import by.tms.tasktracker.dto.UserLoginDto;
import by.tms.tasktracker.dto.UserRegistrationDto;
import by.tms.tasktracker.entity.User;
import by.tms.tasktracker.exeption.UserNotFoundException;
import by.tms.tasktracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;


    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserRegistrationDto register(UserRegistrationDto userRegistrationDto) {
        User user = new User();
        user.setName(userRegistrationDto.getName());
        user.setEmail(userRegistrationDto.getEmail());
        user.setPassword(userRegistrationDto.getPassword());
        user.setUsername(userRegistrationDto.getUsername());
        userRepository.save(user);
        return userRegistrationDto;
    }

    public User login(UserLoginDto userLoginDto) {
        User user = userRepository.findByUsername(userLoginDto.getUsername()).orElseThrow(() -> new UserNotFoundException("User not found"));
        if (!user.getPassword().equals(userLoginDto.getPassword())) {
            throw new RuntimeException("Wrong password");
        }
        else return user;
    }

    public User updateUser (Long userId, User user) {
        User newUser = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));
        newUser.setName(user.getName());
        newUser.setEmail(user.getEmail());
        newUser.setPassword(user.getPassword());
        newUser.setUsername(user.getUsername());
        newUser.setRole(user.getRole());
        return userRepository.save(newUser);
    }


    public List<User> findAll() {
        return userRepository.findAll();
    }

    public void delete(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));
        userRepository.delete(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> byUsername = userRepository.findByUsername(username);
        if (byUsername.isPresent()) {
            User user = byUsername.get();
            return user;
        }
        throw new UsernameNotFoundException(username);
    }
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        return (UserDetails) userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("Not found"));
//    }
}


