package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserServiceImp;

import java.security.Principal;

@Controller
@RequestMapping("/")
public class UserController {

    @Autowired
    public UserController(UserServiceImp userService) {
        this.userService = userService;
    }

    private final UserServiceImp userService;

    @GetMapping("/admin/users")
    public String getAllUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "/admin/users/users";
    }

    @GetMapping("/admin/users/new")
    public String newUser(@ModelAttribute("user") User user) {
        return "admin/users/new";
    }

    @PostMapping("/admin/users/new")
    public String create(@ModelAttribute("user") User user) {
        userService.createUser(user);
        return "redirect:/admin/users";
    }

    @GetMapping("/admin/users/{id}")
    public String index(@PathVariable("id") long id, Model model) {
        model.addAttribute("user", userService.index(id));
        return "/admin/users/index";
    }

    @GetMapping("/admin/users/{id}/edit")
    public String editUserForm(@PathVariable("id") long id, Model model) {
        User user = userService.findById(id);
        model.addAttribute("user", user);
        return "/admin/users/edit";
    }

    @PostMapping("/admin/users/{id}/edit")
    public String edit(@PathVariable("id") long id, @ModelAttribute("user") User user) {
        userService.updateUser(id, user);
        return "redirect:/admin/users";
    }

    @GetMapping("/admin/users/{id}/delete")
    public String delete(@PathVariable("id") long id) {
        userService.deleteUser(id);
        return "redirect:/admin/users";
    }

    @GetMapping("/user")
    public String user(Principal principal, Model model) {
        User user = userService.findByUsername(principal.getName());
        long id = user.getId();
        model.addAttribute("user", userService.index(id));
        return "user";
    }
}