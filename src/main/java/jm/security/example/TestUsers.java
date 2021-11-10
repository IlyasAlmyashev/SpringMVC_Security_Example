package jm.security.example;

import jm.security.example.model.Role;
import jm.security.example.model.User;
import jm.security.example.service.RoleService;
import jm.security.example.service.RoleServiceImpl;
import jm.security.example.service.UserService;
import jm.security.example.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

@Component
public class TestUsers {
    private UserService userService;
    private RoleService roleService;

    @Autowired
    public TestUsers(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @PostConstruct
    public void postConstruct() {
        Role role_admin = new Role("ROLE_ADMIN");
        Role role_user = new Role("ROLE_USER");
        roleService.addRole(role_admin);
        roleService.addRole(role_user);

        Set<Role> adminRolesSet = new HashSet<>();
        adminRolesSet.add(role_admin);
        adminRolesSet.add(role_user);
        User admin = new User("admin", "admin", adminRolesSet);
        userService.addUser(admin);

        Set<Role> userRolesSet = new HashSet<>();
        userRolesSet.add(role_user);
        User user = new User("test", "test", userRolesSet);
        userService.addUser(user);
    }
}
