package com.nowoczesnyjunior.financialapp.usermodule.service;

import com.nowoczesnyjunior.financialapp.openapi.model.TokenDto;
import com.nowoczesnyjunior.financialapp.usermodule.exception.UserCreationException;
import com.nowoczesnyjunior.financialapp.usermodule.model.AppUser;
import com.nowoczesnyjunior.financialapp.usermodule.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private UserRepository userRepository;
    @Mock
    private BCryptPasswordEncoder passwordEncoder;
    UserService userService;

    private final long TOKEN_EXP_TIME = 3600000L;
    private final String TOKEN_SECRET = "testSecret";

    @BeforeEach
    public void setUp() {
        userService = new UserService(authenticationManager, userRepository, TOKEN_EXP_TIME, TOKEN_SECRET, passwordEncoder);
    }

    @Test
    public void authenticateShouldReturnTokenWhenValidCredentials() {
        // GIVEN
        String username = "testUser";
        String password = "testPassword";
        UserDetails userDetails = User.withUsername(username).password(password).build();
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, password);

        Mockito.when(authenticationManager.authenticate(any())).thenReturn(authentication);

        // WHEN
        TokenDto tokenDto = userService.authenticate(username, password);

        // ASSERT
        assertNotNull(tokenDto);
    }

    @Test
    public void authenticateShouldThrowExceptionWhenInvalidCredentials() {
        // GIVEN
        String username = "invalidUser";
        String password = "invalidPassword";
        when(authenticationManager.authenticate(any())).thenThrow(UsernameNotFoundException.class);

        // WHEN & ASSERT
        assertThrows(UsernameNotFoundException.class, () -> userService.authenticate(username, password));
    }

    @Test
    public void createNewUserShouldSaveUserWhenValidInput() {
        // GIVEN
        String username = "newUser";
        String password = "newPassword";
        String repeatedPassword = "newPassword";
        when(userRepository.existsByUsername(username)).thenReturn(false);

        // WHEN
        userService.createNewUser(username, password, repeatedPassword);

        // ASSERT
        verify(userRepository, times(1)).save(any(AppUser.class));
    }

    @Test
    public void createNewUserShouldThrowExceptionWhenPasswordsDoNotMatch() {
        // GIVEN
        String username = "testUser";
        String password = "password1";
        String repeatedPassword = "password2";

        // WHEN & ASSERT
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.createNewUser(username, password, repeatedPassword);
        });
        assertEquals("The passwords do not match.", exception.getMessage());
    }

    @Test
    public void createNewUserShouldThrowExceptionWhenUsernameAlreadyExists() {
        // GIVEN
        String username = "existingUser";
        String password = "password";
        String repeatedPassword = "password";
        when(userRepository.existsByUsername(username)).thenReturn(true);

        // WHEN & ASSERT
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.createNewUser(username, password, repeatedPassword);
        });
        assertEquals("The given user name is already in the database", exception.getMessage());
    }

    @Test
    public void createNewUserShouldThrowExceptionWhenSaveUserFails() {
        // GIVEN
        String username = "testUser";
        String password = "password";
        String repeatedPassword = "password";
        when(userRepository.existsByUsername(username)).thenReturn(false);
        doThrow(RuntimeException.class).when(userRepository).save(any(AppUser.class));

        // WHEN & ASSERT
        assertThrows(UserCreationException.class, () -> userService.createNewUser(username, password, repeatedPassword));
    }
}
