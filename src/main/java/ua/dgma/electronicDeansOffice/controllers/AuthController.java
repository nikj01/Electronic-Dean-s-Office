package ua.dgma.electronicDeansOffice.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.dgma.electronicDeansOffice.security.annotations.AllPerople;

@RestController
public class AuthController {
    @PostMapping("/login")
    @AllPerople
    public void login() {

    };
    @PostMapping("/logout")
    @AllPerople
    public void logout() {};
}
