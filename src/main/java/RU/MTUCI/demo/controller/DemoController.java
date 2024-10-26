package RU.MTUCI.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import RU.MTUCI.demo.model.Demo;
import RU.MTUCI.demo.model.User;
import RU.MTUCI.demo.repo.UserRepository;

@Controller
@RequiredArgsConstructor
public class DemoController {
    @Autowired
    private final UserRepository userRepository;

    @GetMapping("/")
    @PreAuthorize("hasAnyAuthority('read')")
    public String home(Model model) {
        Iterable<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "home";
    }


    @PostMapping
    public Demo getDemo(@RequestBody Demo demo){
        return demo;
    }
}