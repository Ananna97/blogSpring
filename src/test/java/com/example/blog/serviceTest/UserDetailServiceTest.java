package com.example.blog.serviceTest;

import com.example.blog.model.Role;
import com.example.blog.model.User;
import com.example.blog.service.UserDetailService;
import com.example.blog.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class UserDetailServiceTest {

    @Test
    public void testLoadUserByUsername() {
        UserService userService = Mockito.mock(UserService.class);
        UserDetailService userDetailService = new UserDetailService(userService);

        User user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");
        user.setPassword("password");

        Role role = new Role();
        role.setName("ROLE_USER");

        user.setRoles(Collections.singleton(role));
        when(userService.findByEmail("test@example.com")).thenReturn(Optional.of(user));

        UserDetails userDetails = userDetailService.loadUserByUsername("test@example.com");

        assertEquals("test@example.com", userDetails.getUsername());
        assertEquals("password", userDetails.getPassword());
        assertEquals(1, userDetails.getAuthorities().size());
        assertEquals("ROLE_USER", userDetails.getAuthorities().iterator().next().getAuthority());

    }

    @Test
    public void testLoadUserByUsernameUserNotFound() {
        UserService userService = Mockito.mock(UserService.class);
        UserDetailService userDetailService = new UserDetailService(userService);

        when(userService.findByEmail("nonexistent@example.com")).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> userDetailService.loadUserByUsername("nonexistent@example.com"));
    }
}