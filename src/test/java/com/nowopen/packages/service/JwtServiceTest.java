package com.nowopen.packages.service;

import com.nowopen.packages.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class JwtServiceTest {

    @Mock
    private User user;

    @InjectMocks
    private JwtService jwtService;

    @Test
    public void testGenerateToken() {
        // Mock user details
        when(user.getId()).thenReturn(1L);
        when(user.getRealUsername()).thenReturn("testUser");
        when(user.getUserType()).thenReturn("USER");

        // Generate token
        String token = jwtService.generateToken(user);

        // Validate token is not null or empty
        assertEquals(false, token.isEmpty());
    }


}
