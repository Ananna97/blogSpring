package com.example.blog.controllerTest;

import com.example.blog.controller.RegisterController;
import com.example.blog.model.User;
import com.example.blog.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class RegisterControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private Model model;

    @InjectMocks
    private RegisterController registerController;

    public RegisterControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getRegisterForm() {
        User user = new User();
        when(model.addAttribute("user", user)).thenReturn(model);

        String viewName = registerController.getRegisterForm(model);

        assertEquals("register", viewName);
    }

    @Test
    void registerNewUser() {
        User user = new User();

        String viewName = registerController.registerNewUser(user);

        assertEquals("redirect:/", viewName);
    }
}
