package com.example.blog.serviceTest;

import com.example.blog.model.Role;
import com.example.blog.model.User;
import com.example.blog.repository.RoleRepository;
import com.example.blog.repository.UserRepository;
import com.example.blog.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Test
    public void testSave() {
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        RoleRepository roleRepository = Mockito.mock(RoleRepository.class);
        PasswordEncoder passwordEncoder = Mockito.mock(PasswordEncoder.class);
        UserService userService = new UserService(userRepository, passwordEncoder, roleRepository);

        User userToSave = new User();
        userToSave.setEmail("test@example.com");
        userToSave.setPassword("password");

        Role role = new Role();
        role.setName("ROLE_USER");
        when(roleRepository.findById("ROLE_USER")).thenReturn(Optional.of(role));

        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User savedUser = invocation.getArgument(0);
            savedUser.setId(1L);
            return savedUser;
        });

        when(passwordEncoder.encode("password")).thenReturn("hashedPassword");

        User savedUser = userService.save(userToSave);

        assertEquals(1L, savedUser.getId());
        assertEquals("test@example.com", savedUser.getEmail());
        assertEquals("hashedPassword", savedUser.getPassword());
        assertTrue(savedUser.getRoles().contains(role));
        assertTrue(savedUser.getCreatedAt() != null);
        assertTrue(savedUser.getUpdatedAt() != null);
        verify(userRepository, times(1)).save(userToSave);
    }

    @Test
    public void testFindByEmail() {
        UserRepository userRepository = Mockito.mock(UserRepository.class);

        UserService userService = new UserService(userRepository, null, null);

        User sampleUser = new User();
        sampleUser.setId(1L);
        sampleUser.setEmail("test@example.com");

        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(sampleUser));

        Optional<User> foundUser = userService.findByEmail("test@example.com");

        assertTrue(foundUser.isPresent());
        assertEquals(1L, foundUser.get().getId());
        assertEquals("test@example.com", foundUser.get().getEmail());
        verify(userRepository, times(1)).findByEmail("test@example.com");
    }
}

