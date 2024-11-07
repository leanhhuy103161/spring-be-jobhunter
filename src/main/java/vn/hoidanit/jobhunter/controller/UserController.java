package vn.hoidanit.jobhunter.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import vn.hoidanit.jobhunter.domain.User;
import vn.hoidanit.jobhunter.service.UserService;
import vn.hoidanit.jobhunter.util.error.IdInvalidException;

import java.util.List;

@RestController
@RequestMapping("api")
public class UserController {

  private final UserService userService;
  private final PasswordEncoder passwordEncoder;

  public UserController(UserService userService, PasswordEncoder passwordEncoder) {
    this.userService = userService;
    this.passwordEncoder = passwordEncoder;
  }

  @PostMapping("/users")
  public ResponseEntity<User> createUser(@RequestBody User user) {
    String hashedPassword = passwordEncoder.encode(user.getPassword());
    user.setPassword(hashedPassword);
    User CreatedUser = userService.handleCreateUser(user);

    return ResponseEntity.status(HttpStatus.CREATED).body(CreatedUser);
  }

  @DeleteMapping("/users/{id}")
  public ResponseEntity<String> deleteUserById(@PathVariable("id") long id) {
    userService.handleDeleteUser(id);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  @GetMapping("/users")
  public ResponseEntity<List<User>> getAllUsers() {
    List<User> users = userService.handleGetAllUsers();
    return ResponseEntity.ok(users);
  }

  @GetMapping("/users/{id}")
  public ResponseEntity<User> getUserById(@PathVariable("id") long id) throws IdInvalidException {
    if (id == 200) {
      throw new IdInvalidException("Error id 200");
    }

    User user = userService.handleGetUser(id);
    return ResponseEntity.ok(user);
  }

  @PutMapping("/users")
  public ResponseEntity<User> updateUser(@RequestBody User user) {
    User returendUser = userService.handleUpdateUser(user);
    return ResponseEntity.ok(returendUser);
  }
}
