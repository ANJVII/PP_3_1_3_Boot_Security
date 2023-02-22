package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Collection;
import java.util.List;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile")
    public String get(Principal principal, Model model) {
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("profile", user);
        return "profile";
    }

    @GetMapping(value = "admin/users")
    public String showAllUsers(Model model) {
        List<User> allUsers = userService.findAllUsers();
        model.addAttribute("users", allUsers);
        return "all-users";
    }

    @GetMapping(value = "admin/addNewUser")
    public String addNewUser(Model model) {
        User user = new User();
        Collection<Role> roles = userService.getRoles();
        model.addAttribute("user", user);
        model.addAttribute("access", roles);
        return "newUser";
    }

    @PostMapping(value = "admin/saveUser")
    public String saveUser(@Valid @ModelAttribute("user") User user, BindingResult bindingResult) {
        System.out.println(user.getRoles());
        userService.saveUser(user);
        return bindingResult.hasErrors() ? "newUser" : "redirect:/admin/users";
    }

    @GetMapping(value = "admin/updateInfo-{id}")
    public String showOldUser(@PathVariable("id") Long id, Model model) {
        User user = userService.getUserById(id);
        Collection<Role> roles = userService.getRoles();
        model.addAttribute("user", user);
        model.addAttribute("access", roles);
        return "oldUser";
    }


    @PatchMapping(value = "admin/updateUser-{id}")
    public String updateUser(@ModelAttribute("user") User user) {
        userService.updateUser(user);
        return "redirect:/admin/users";
    }

    @DeleteMapping(value = "admin/deleteUser")
    public String deleteUser(@RequestParam(value = "id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin/users";
    }
}
