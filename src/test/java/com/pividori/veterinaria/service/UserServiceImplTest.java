package com.pividori.veterinaria.service;

import com.pividori.veterinaria.dtos.ChangePasswordRequest;
import com.pividori.veterinaria.dtos.UserResponse;
import com.pividori.veterinaria.exceptions.PasswordEqualsException;
import com.pividori.veterinaria.exceptions.PasswordIncorrectException;
import com.pividori.veterinaria.exceptions.UserNotFoundException;
import com.pividori.veterinaria.models.User;
import com.pividori.veterinaria.repositorys.UserRepository;
import com.pividori.veterinaria.services.UserServiceImpl;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
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

    @Nested
    class FindByUsernameTest {
        @Test
        void findByUsername_shouldReturnUserResponse_whenUserExist() {

            // Arrange
            String username = "pivissj";
            User user = new User();
            user.setUsername(username);

            given(userRepository.findByUsername(username)).willReturn(Optional.of(user));

            // Act
            UserResponse userResponse = userServiceImpl.findByUsername(username);

            // Assert
            verify(userRepository).findByUsername(username);
            assertNotNull(userResponse);
            assertEquals(username, userResponse.username());
        }

        @Test
        void findByUsername_shouldThrowUserNotFoundException_whenUserNotDoesExist() {

            String username = "pivissj";

            given(userRepository.findByUsername(username))
                    .willReturn(Optional.empty());

            assertThrows(UserNotFoundException.class,
                    () -> userServiceImpl.findByUsername(username));
            verify(userRepository).findByUsername(username);

        }
    }

    @Nested
    class ChangePasswordTest {
        @Test
        void changePassword_shouldChangePassword_whenCurrentIsCorrectAndNewIsDifferent() {
            // Creamos las variables necesarias para el test.
            ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest("oldPass", "newPass");
            User user = new User();
            // Esto lo utilizamos porque no tenemos set en ID en la clase User, con esto seteamos el ID del user solo en el test.
            ReflectionTestUtils.setField(user, "id", 1L);
            user.setUsername("pivissj");
            user.setPassword("oldPassEncoded");
            String oldPassEncoded = user.getPassword();
            String newPassEncoded = "newPassEncoded";


            // El repositorio debe devolver este usuario cuando se lo busque por este ID.
            given(userRepository.findById(user.getId()))
                    .willReturn(Optional.of(user));

            // El encoder debe decir que la currentPassword coincide con la contraseña del usuario.
            given(passwordEncoder.matches(changePasswordRequest.currentPassword(), user.getPassword()))
                    .willReturn(true);

            // Cuando hagamos el encode y le pasemos esa contraseña para codificarla, devuélveme newPassEncoded.
            given(passwordEncoder.encode(changePasswordRequest.newPassword()))
                    .willReturn(newPassEncoded);

            // Cuando haga save de cualquier User class al repository, devuélveme, de la invocación (invocation), el argumento 0.
            given(userRepository.save(any(User.class)))
                    //willAnswer para que ejecute lógica, con willReturn solo devuelve algo.
                    .willAnswer(invocation -> invocation.getArgument(0));

            // Acá ejecutamos el metodo de changePassword con las reglas que le di antes y guardamos el resultado.
            UserResponse userResponse =
                    userServiceImpl.changePassword(user.getId(), changePasswordRequest);

            // Assert: verificamos que haya pasado lo que esperábamos.
            // Con assert miramos el estado de algo en concreto. Verificamos si tal cosa en este momento es tal.
            // userResponse no puede ser null.
            assertNotNull(userResponse);
            // El password del usuario guardado en la base de datos debe ser igual a newPassEncoded.
            assertEquals(newPassEncoded, user.getPassword());
            // Con verify verificamos que estas cosas se hayan hecho al menos una vez, no miramos estado.
            verify(userRepository).save(user);
            verify(passwordEncoder).matches(changePasswordRequest.currentPassword(), oldPassEncoded);
            verify(passwordEncoder).encode(changePasswordRequest.newPassword());
        }

        @Test
        void changePassword_shouldThrowPasswordIncorrectException_whenCurrentIsDifferent() {

            ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest("oldPass", "newPass");
            User user = new User();
            ReflectionTestUtils.setField(user, "id", 1L);
            user.setUsername("pivissj");
            user.setPassword("oldPassEncoded");

            given(userRepository.findById(user.getId()))
                    .willReturn(Optional.of(user));

            // El encoder dice que la contraseña actual NO coincide con la guardada
            given(passwordEncoder.matches(changePasswordRequest.currentPassword(), user.getPassword()))
                    .willReturn(false);

            //Asegúrate que tiras una clase del tipo PasswordIncorrectException, cuando hago changePassword
            assertThrows(PasswordIncorrectException.class,
                    () -> userServiceImpl.changePassword(user.getId(), changePasswordRequest));

            //Verificamos que la contraseña del usuario no haya cambiado.
            assertEquals("oldPassEncoded", user.getPassword());

            verify(userRepository).findById(user.getId());
            verify(passwordEncoder)
                    .matches(changePasswordRequest.currentPassword(), user.getPassword());
            //Verificamos que nunca se haga el encode y el save.
            verify(passwordEncoder, never()).encode(any(String.class));
            verify(userRepository, never()).save(any(User.class));
        }

        @Test
        void changePassword_shouldThrowPasswordEqualsException_NewPasswordEqualsCurrent() {

            //Variables necesarias.
            ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest("oldPass", "oldPass");
            User user = new User();
            ReflectionTestUtils.setField(user, "id", 1L);
            user.setUsername("pivissj");
            user.setPassword("oldPassEncoded");

            //Configuraciónes.
            given(userRepository.findById(user.getId()))
                    .willReturn(Optional.of(user));

            given(passwordEncoder.matches(changePasswordRequest.currentPassword(), user.getPassword()))
                    .willReturn(true);

            //Verificaciones.
            assertThrows(PasswordEqualsException.class,
                    () -> userServiceImpl.changePassword(user.getId(), changePasswordRequest));
            assertEquals("oldPassEncoded", user.getPassword());
            verify(passwordEncoder, never()).encode(any(String.class));
            verify(userRepository, never()).save(any(User.class));
        }
    }
}
