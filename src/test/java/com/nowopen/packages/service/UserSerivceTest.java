package com.nowopen.packages.service;

import com.nowopen.packages.common.exception.ServiceException;
import com.nowopen.packages.dto.UserDto;
import com.nowopen.packages.entity.User;
import com.nowopen.packages.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

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

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private UserService userService;

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

    @Test
    public void sign_in_success() {
        UserDto dto = UserDto.builder()
                .email("test@test.com")
                .password("123qwe!@")
                .username("Robin")
                .build();
        String hashed = "hashedPassword";

        User user = new User();
        user.setEmail(dto.getEmail());
        user.setPw(hashed);

        Mockito.when(userRepository.findByEmail(dto.getEmail())).thenReturn(Optional.of(user));
        // Mock authentication behavior
        Mockito.when(authenticationManager.authenticate(Mockito.any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(Mockito.mock(UsernamePasswordAuthenticationToken.class));
        // Mock JWT token generation
        Mockito.when(jwtService.generateToken(Mockito.any(User.class))).thenReturn("mockedToken");

        UserDto result = userService.signIn(dto);
        assertEquals(dto.getEmail(), result.getEmail());
    }

    @Test
    public void sign_in_invalid_throw_exception() {
        Mockito.when(userRepository.findByEmail("wrong@example.com")).thenReturn(Optional.empty());

        assertThrows(ServiceException.class, () ->
                userService.signIn(UserDto.builder().email("wrong@example.com").password("password").build()));
    }

}
