package com.nowopen.packages.service;

import com.nowopen.packages.dto.UserDto;
import com.nowopen.packages.entity.User;
import com.nowopen.packages.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

// MockitoExtension.class is used unit test, using with @Mock, @InjectMocks
// @SpringbootTest is for integration test
@ExtendWith(MockitoExtension.class)
public class UserSerivceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthService userService;

    @Test
    public void sign_up_success(){
        UserDto dto = UserDto.builder()
                .email("test@test.com")
                .password("123qwe!@")
                .username("Robin")
                .build();
        String encoded = "encoded";

        // when specific method is called, define action where and what to return or answer etc..
        Mockito.when(passwordEncoder.encode(dto.getPassword())).thenReturn(encoded);
        Mockito.when(userRepository.save(any(User.class))).thenAnswer(i -> i.getArguments()[0]);

        UserDto signUpUser = userService.signUp(dto);
        assertEquals("test@test.com", signUpUser.getEmail());

    }

//    @Test
//    public void sign_in_success() {
//        String email = "user@example.com";
//        String rawPassword = "1234";
//        String hashedPassword = "hashed";
//
//        User user = new User();
//        user.setEmail(email);
//        user.setPw(hashedPassword);
//
//        Mockito.when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
//        Mockito.when(passwordEncoder.matches(rawPassword, hashedPassword)).thenReturn(true);
//
//        User result = userService.signIn(email, rawPassword);
//        assertEquals(email, result.getEmail());
//    }
//
//    @Test
//    public void sign_in_invalid_throw_exception() {
//        Mockito.when(userRepository.findByEmail("wrong@example.com")).thenReturn(Optional.empty());
//
//        assertThrows(ServiceException.class, () ->
//                userService.signIn("wrong@example.com", "password"));
//    }
//
//    @Test
//    public void sign_in_wrong_password_throw_exception() {
//        User user = new User();
//        user.setEmail("user@example.com");
//        user.setPw("hashed");
//
//        Mockito.when(userRepository.findByEmail("user@example.com")).thenReturn(Optional.of(user));
//        Mockito.when(passwordEncoder.matches("wrong", "hashed")).thenReturn(false);
//
//        assertThrows(ServiceException.class, () ->
//                userService.signIn("user@example.com", "wrong"));
//    }

}
