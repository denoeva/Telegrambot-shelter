package pro.sky.telegrambot.shelter.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pro.sky.telegrambot.shelter.model.Users;
import pro.sky.telegrambot.shelter.service.UserService;

import java.util.Collection;

@RestController
@RequestMapping("orders")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("all_orders")
    public ResponseEntity<Collection<Users>> getALlOrders() {
        Collection<Users> users = userService.getAllOrders();
        if (users.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userService.getAllOrders());
    }
}
