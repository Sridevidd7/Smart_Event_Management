package com.college.eventclub.api;

import com.college.eventclub.User;
import com.college.eventclub.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Authentication API Controller
 */
@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private UserDAO userDAO;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            User user = userDAO.getUserByEmail(request.getEmail());
            
            if (user == null) {
                return ResponseEntity.badRequest().body(Map.of("error", "User not found"));
            }
            
            if (!user.getPassword().equals(request.getPassword())) {
                return ResponseEntity.badRequest().body(Map.of("error", "Invalid credentials"));
            }
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("userId", user.getUserId());
            response.put("name", user.getName());
            response.put("email", user.getEmail());
            response.put("role", user.getRole());
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "Login failed: " + e.getMessage()));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        try {
            User existingUser = userDAO.getUserByEmail(request.getEmail());
            if (existingUser != null) {
                return ResponseEntity.badRequest().body(Map.of("error", "Email already exists"));
            }
            
            User newUser = new User();
            newUser.setName(request.getName());
            newUser.setEmail(request.getEmail());
            newUser.setPassword(request.getPassword());
            newUser.setRole("STUDENT");
            
            userDAO.addUser(newUser);
            
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "Registration successful. Please login."
            ));
            
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "Registration failed: " + e.getMessage()));
        }
    }

    static class LoginRequest {
        public String email;
        public String password;
        
        public String getEmail() { return email; }
        public String getPassword() { return password; }
    }

    static class RegisterRequest {
        public String name;
        public String email;
        public String password;
        
        public String getName() { return name; }
        public String getEmail() { return email; }
        public String getPassword() { return password; }
    }
}
