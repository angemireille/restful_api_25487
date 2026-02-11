package auca.ac.rw.bonus_userprofile_api.Controller.Userprofile;

import auca.ac.rw.bonus_userprofile_api.Model.Userprofile.ApiResponse;
import auca.ac.rw.bonus_userprofile_api.Model.Userprofile.userprofile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserProfileController {

    private List<userprofile> users = new ArrayList<>();
    private Long nextUserId = 1L;

    // Initialize with sample users
    public UserProfileController() {
        users.add(new userprofile(nextUserId++, "john_doe", "john@example.com", "John Doe", 25, "USA", "Software developer", true));
        users.add(new userprofile(nextUserId++, "jane_smith", "jane@example.com", "Jane Smith", 30, "Canada", "UX designer", true));
        users.add(new userprofile(nextUserId++, "bob_wilson", "bob@example.com", "Bob Wilson", 22, "UK", "Student", false));
        users.add(new userprofile(nextUserId++, "alice_johnson", "alice@example.com", "Alice Johnson", 28, "USA", "Product manager", true));
        users.add(new userprofile(nextUserId++, "charlie_brown", "charlie@example.com", "Charlie Brown", 35, "Australia", "Engineer", false));
    }

    // GET /api/users - Get all users
    @GetMapping
    public ResponseEntity<ApiResponse<List<userprofile>>> getAllUsers() {
        ApiResponse<List<userprofile>> response = new ApiResponse<>(
            true,
            "Users retrieved successfully",
            users
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // GET /api/users/{userId} - Get user by ID
    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<userprofile>> getUserById(@PathVariable Long userId) {
        Optional<userprofile> user = users.stream()
                .filter(u -> u.getUserId().equals(userId))
                .findFirst();
        
        if (user.isPresent()) {
            ApiResponse<userprofile> response = new ApiResponse<>(
                true,
                "User found",
                user.get()
            );
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            ApiResponse<userprofile> response = new ApiResponse<>(
                false,
                "User not found with id: " + userId,
                null
            );
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    // POST /api/users - Create new user
    @PostMapping
    public ResponseEntity<ApiResponse<userprofile>> createUser(@RequestBody userprofile user) {
        user.setUserId(nextUserId++);
        user.setActive(true);
        users.add(user);
        
        ApiResponse<userprofile> response = new ApiResponse<>(
            true,
            "User profile created successfully",
            user
        );
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // PUT /api/users/{userId} - Update user
    @PutMapping("/{userId}")
    public ResponseEntity<ApiResponse<userprofile>> updateUser(@PathVariable Long userId, @RequestBody userprofile updatedUser) {
        for (int i = 0; i < users.size(); i++) {
            userprofile u = users.get(i);
            if (u.getUserId().equals(userId)) {
                updatedUser.setUserId(userId);
                users.set(i, updatedUser);
                
                ApiResponse<userprofile> response = new ApiResponse<>(
                    true,
                    "User profile updated successfully",
                    updatedUser
                );
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        }
        
        ApiResponse<userprofile> response = new ApiResponse<>(
            false,
            "User not found with id: " + userId,
            null
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    // DELETE /api/users/{userId} - Delete user
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable Long userId) {
        boolean removed = users.removeIf(u -> u.getUserId().equals(userId));
        
        if (removed) {
            ApiResponse<Void> response = new ApiResponse<>(
                true,
                "User deleted successfully",
                null
            );
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            ApiResponse<Void> response = new ApiResponse<>(
                false,
                "User not found with id: " + userId,
                null
            );
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    // ============ SEARCH OPERATIONS ============

    @GetMapping("/search/username")
    public ResponseEntity<ApiResponse<List<userprofile>>> searchByUsername(@RequestParam String username) {
        List<userprofile> result = users.stream()
                .filter(u -> u.getUsername().toLowerCase().contains(username.toLowerCase()))
                .collect(Collectors.toList());
        
        ApiResponse<List<userprofile>> response = new ApiResponse<>(
            true,
            "Found " + result.size() + " user(s)",
            result
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/search/country/{country}")
    public ResponseEntity<ApiResponse<List<userprofile>>> searchByCountry(@PathVariable String country) {
        List<userprofile> result = users.stream()
                .filter(u -> u.getCountry().equalsIgnoreCase(country))
                .collect(Collectors.toList());
        
        ApiResponse<List<userprofile>> response = new ApiResponse<>(
            true,
            "Found " + result.size() + " user(s) in " + country,
            result
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/search/age-range")
    public ResponseEntity<ApiResponse<List<userprofile>>> searchByAgeRange(
            @RequestParam int min,
            @RequestParam int max) {
        List<userprofile> result = users.stream()
                .filter(u -> u.getAge() >= min && u.getAge() <= max)
                .collect(Collectors.toList());
        
        ApiResponse<List<userprofile>> response = new ApiResponse<>(
            true,
            "Found " + result.size() + " user(s) between ages " + min + " and " + max,
            result
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // ============ ACTIVATE/DEACTIVATE ============

    @PatchMapping("/{userId}/activate")
    public ResponseEntity<ApiResponse<userprofile>> activateUser(@PathVariable Long userId) {
        for (userprofile u : users) {
            if (u.getUserId().equals(userId)) {
                u.setActive(true);
                
                ApiResponse<userprofile> response = new ApiResponse<>(
                    true,
                    "User account activated successfully",
                    u
                );
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        }
        
        ApiResponse<userprofile> response = new ApiResponse<>(
            false,
            "User not found with id: " + userId,
            null
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @PatchMapping("/{userId}/deactivate")
    public ResponseEntity<ApiResponse<userprofile>> deactivateUser(@PathVariable Long userId) {
        for (userprofile u : users) {
            if (u.getUserId().equals(userId)) {
                u.setActive(false);
                
                ApiResponse<userprofile> response = new ApiResponse<>(
                    true,
                    "User account deactivated successfully",
                    u
                );
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        }
        
        ApiResponse<userprofile> response = new ApiResponse<>(
            false,
            "User not found with id: " + userId,
            null
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}