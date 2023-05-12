package ua.dgma.electronicDeansOffice.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.dgma.electronicDeansOffice.security.annotations.AllPeople;

@RestController
public class AuthController {
    @PostMapping("/login")
    @AllPeople
    public void login() {

    };
    @PostMapping("/logout")
    @AllPeople
    public void logout() {};
}
