package lebui.shipserve.practicaljavaexam.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lebui.shipserve.practicaljavaexam.security.jwt.LoggedInUser;
import lebui.shipserve.practicaljavaexam.service.AuthenticationService;

@RestController
public class AuthenticationController {
    
    @Autowired
    private AuthenticationService authenticationService;
    
    @GetMapping(value = "/api/logged-in-user", produces = "application/json")
    public LoggedInUser getLoggedInUser() {
        return this.authenticationService.getLoggedInUser();
    }
    
}
