package ua.dgma.electronicDeansOffice.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @GetMapping("/login")
    public void login() {};
    @GetMapping("/logout")
    public void logout() {};
}
