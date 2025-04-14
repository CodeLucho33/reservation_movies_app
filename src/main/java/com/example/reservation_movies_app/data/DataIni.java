package com.example.reservation_movies_app.data;


import com.example.reservation_movies_app.enums.Role;
import com.example.reservation_movies_app.model.User;
import com.example.reservation_movies_app.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;



@Transactional
@Component
@RequiredArgsConstructor
public class DataIni implements ApplicationListener<ApplicationReadyEvent> {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Override

    public void onApplicationEvent(ApplicationReadyEvent event) {
        createDefaultUserIfNotExists();
        createDefaultAdminIfNotExists();
    }
    private void createDefaultUserIfNotExists() {
        for (int i = 1; i == 1; i++) {
            String defaultEmail = "user" + i + "@gmail.com";
            if (userRepository.existsByEmail(defaultEmail)) {
                continue;
            }
            User user = new User();
            user.setFirstName("User" + i);
            user.setLastName("User" + i);
            user.setEmail(defaultEmail);
            user.setPassword(passwordEncoder.encode("123456"));
            user.setRole(Role.ROLE_REGULAR); // Establece el rol de usuario
            userRepository.save(user);
            System.out.println("User " + i + " created");
        }
    }

    private void createDefaultAdminIfNotExists() {
        for (int i = 1; i == 1; i++) {
            String defaultEmail = "admin" + i + "@gmail.com";
            if (userRepository.existsByEmail(defaultEmail)) {
                continue;
            }

            User user = new User();
            user.setFirstName("Admin");
            user.setLastName("Admin" + i);
            user.setEmail(defaultEmail);
            user.setPassword(passwordEncoder.encode("123456"));
            user.setRole(Role.ROLE_ADMIN); // Establece el rol de administrador
            userRepository.save(user);
            System.out.println("Admin " + i + " created");
        }
    }



    @Override
    public boolean supportsAsyncExecution() {
        return ApplicationListener.super.supportsAsyncExecution();
    }
}
