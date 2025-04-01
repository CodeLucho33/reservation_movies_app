package com.example.reservation_movies_app.security.user;

import com.example.reservation_movies_app.model.User;
import com.example.reservation_movies_app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
/**
 * Esta clase implementa UserDetailsService de Spring Security y es responsable de cargar
 * los datos de un usuario desde la base de datos para la autenticación.
 *
 * Funcionalidad:
 * - Busca un usuario en la base de datos mediante su correo electrónico.
 * - Si el usuario existe, lo convierte en una instancia de MovieUserDetails,
 *   que Spring Security usa para gestionar la autenticación y autorización.
 * - Si el usuario no existe, lanza una excepción UsernameNotFoundException.
 *
 * Este servicio es llamado automáticamente por Spring Security durante el proceso
 * de autenticación cuando un usuario intenta iniciar sesión.
 */

@Service
@RequiredArgsConstructor
public class MovieUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = Optional.ofNullable(userRepository.findByEmail(email))
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return MovieUserDetails.buildUserDetails(user);
    }
}
