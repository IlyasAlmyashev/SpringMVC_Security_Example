package jm.security.example.controller;

import jm.security.example.model.Role;
import jm.security.example.model.User;
import jm.security.example.service.RoleService;
import jm.security.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
//@RequestMapping("")
public class UserController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping(value = "/")
    public String getHomePage() {
        return "redirect:/hello";
    }

    @GetMapping(value = "/hello")
    public String printWelcome(ModelMap model) {
        List<String> messages = new ArrayList<>();
        messages.add("Hello!");
        messages.add("This is my first Spring Security app");
        messages.add("by Ilyas Almyashev ");
        model.addAttribute("messages", messages);
        return "hello";
    }

    @GetMapping(value = "/user")
    public String userInfo(@AuthenticationPrincipal User user, Model model){
        model.addAttribute("user", user);
        model.addAttribute("roles", user.getRoles());
        return "userpage";
    }

    @GetMapping(value = "/admin")
    public String listUsers(Model model) {
        model.addAttribute("allUsers", userService.getAllUsers());
        return "adminpage";
    }

    @GetMapping(value = "/admin/new")
    public String newUser(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleService.getAllRoles());
        return "new";
    }

    @PostMapping(value = "/admin/add-user")
    public String addUser(@ModelAttribute User user, @RequestParam(value = "checkBoxRoles") String[] checkBoxRoles) {
        Set<Role> roleSet = new HashSet<>();
        for (String role : checkBoxRoles) {
            roleSet.add(roleService.getRoleByName(role));
        }
        user.setRoles(roleSet);
        userService.addUser(user);
        return "redirect:/admin";
    }

    @GetMapping(value = "/edit/{id}")
    public String editUserForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        model.addAttribute("roles", roleService.getAllRoles());
        return "edit";
    }

    @PostMapping(value = "/edit")
    public String editUser(@ModelAttribute User user, @RequestParam(value = "checkBoxRoles") String[] checkBoxRoles) {
        Set<Role> roleSet = new HashSet<>();
        for (String roles : checkBoxRoles) {
            roleSet.add(roleService.getRoleByName(roles));
        }
        user.setRoles(roleSet);
        userService.updateUser(user);
        return "redirect:/admin";
    }

    @GetMapping(value = "/remove/{id}")
    public String removeUser(@PathVariable("id") long id) {
        userService.removeUserById(id);
        return "redirect:/admin";
    }
}
