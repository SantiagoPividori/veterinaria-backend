package com.pividori.veterinaria.service;

import com.pividori.veterinaria.dtos.ChangePasswordRequest;
import com.pividori.veterinaria.dtos.UserResponse;
import com.pividori.veterinaria.models.User;
import com.pividori.veterinaria.repositorys.UserRepository;
import com.pividori.veterinaria.services.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

//Esto le dice a JUnit que antes de cada test inicie los mocks y el objeto a testear.
@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    //Creamos un objeto falso de tipo UserRepository para poder simular la base de datos sin tocarla. Nos permite tener todos los métodos y demás de esa clase.
    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    //Es la clase real que vamos a testear e inyecta los mocks anteriores.
    @InjectMocks
    private UserServiceImpl userServiceImpl;

    @Test
    void changePassword_shouldChangePassword_whenCurrentIsCorrectAndNewIsDifferent() {
        //Creamos las variables necesarias para el test.
        Long userId = 1L;
        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest("oldPass", "newPass");
        User user = new User();
        //Esto lo utilizamos porque no tenemos set en ID en la clase User, con esto seteamos el ID del user solo en el test.
        ReflectionTestUtils.setField(user, "id", userId);
        user.setUsername("pivissj");
        user.setPassword("oldPassEncoded");
        String oldPassEncoded = user.getPassword();
        String newPassEncoded = "newPassEncoded";


        //El repositorio debe devolver este usuario cuando se lo busque por este ID.
        given(userRepository.findById(user.getId()))
                .willReturn(Optional.of(user));

        //El encoder debe decir que la currentPassword concide con la contraseña del usuario.
        given(passwordEncoder.matches(changePasswordRequest.currentPassword(), user.getPassword()))
                .willReturn(true);


        given(passwordEncoder.encode(changePasswordRequest.newPassword()))
                .willReturn(newPassEncoded);

        given(userRepository.save(any(User.class)))
                .willAnswer(invocation -> invocation.getArgument(0));


        UserResponse userResponse =
                userServiceImpl.changePassword(user.getId(), changePasswordRequest);

        //Assert: verificamos que haya pasado lo que esperábamos.
        assertNotNull(userResponse);
        assertEquals(newPassEncoded, user.getPassword());
        verify(userRepository).save(user);
        verify(passwordEncoder).matches(changePasswordRequest.currentPassword(), oldPassEncoded);
        verify(passwordEncoder).encode(changePasswordRequest.newPassword());


    }



}
