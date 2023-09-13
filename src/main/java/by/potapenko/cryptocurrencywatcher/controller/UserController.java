package by.potapenko.cryptocurrencywatcher.controller;

import by.potapenko.cryptocurrencywatcher.model.dto.UserDto;
import by.potapenko.cryptocurrencywatcher.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDto>> getAll() {
        return ResponseEntity.ok(userService.getAll());
    }

    @PostMapping("/notify")
    public ResponseEntity<UserDto> create(@RequestParam String username, String symbol) {
        return ResponseEntity.ok(userService.notify(username, symbol));
    }
}
