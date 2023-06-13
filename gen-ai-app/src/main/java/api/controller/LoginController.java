package api.controller;

import api.model.LoginRequest;
import api.model.LoginResponse;
import api.model.User;
import api.repositories.UserRepository;
import api.service.JwtUtil;
import api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class LoginController {
    private final UserService userService;
    private final JwtUtil jwtUtil;

    @Autowired
    public LoginController(UserRepository userRepository, UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        // Authenticate user
        User user = userService.authenticateUser(loginRequest.getEmail(), loginRequest.getPassword());

        if (user == null) {
            // Unauthorized access
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid credentials");
        }

        // Generate token
        String token = jwtUtil.generateToken(user.getId().toString(), loginRequest.getEmail());

        // Create login response
        LoginResponse response = new LoginResponse("User logged in successfully", token);

        return ResponseEntity.ok(response);
    }
}
