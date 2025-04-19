package org.flintcore.notestack_bkd.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    // Use Web socket

    @PostMapping("/me")
    public void registerMeData() {

    }
}
