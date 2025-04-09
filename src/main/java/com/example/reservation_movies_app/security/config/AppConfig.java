package com.example.reservation_movies_app.security.config;

import com.example.reservation_movies_app.security.jwt.AuthTokenFilter;
import com.example.reservation_movies_app.security.jwt.JwtAuthEntryPoint;
import com.example.reservation_movies_app.security.user.MovieUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;
/**
 * AppConfig es la clase de configuración principal de seguridad para la aplicación, donde se define la configuración de
 * autenticación y autorización utilizando Spring Security. Configura los filtros de seguridad, la autenticación de usuarios
 * y los proveedores de contraseñas, además de habilitar la protección de las rutas de la aplicación.*
 * Funcionalidad:
 * - **@RequiredArgsConstructor**: Genera automáticamente un constructor con los campos finales, facilitando la inyección
 *   de dependencias para las clases `JwtAuthEntryPoint` y `MovieUserDetailsService`.
 * - **@EnableWebSecurity**: Habilita la configuración de seguridad de Spring, permitiendo configurar el acceso a los
 *   recursos de la aplicación.
 * - **@EnableMethodSecurity(prePostEnabled = true)**: Permite el uso de anotaciones de seguridad basadas en métodos
 *   (como `@PreAuthorize` o `@PostAuthorize`) para controlar el acceso a métodos específicos.
 * - **ModelMapper bean**: Configura un bean `ModelMapper` para la conversión de objetos entre diferentes capas de la aplicación.
 * - **AuthTokenFilter bean**: Configura el filtro de seguridad `AuthTokenFilter`, que valida los tokens JWT en las solicitudes.
 * - **PasswordEncoder bean**: Configura un `PasswordEncoder` con el algoritmo BCrypt para codificar las contraseñas de los usuarios.
 * - **AuthenticationManager bean**: Configura un `AuthenticationManager` que se usa para gestionar la autenticación de usuarios.
 * - **DaoAuthenticationProvider bean**: Configura el proveedor de autenticación para usar `MovieUserDetailsService` y
 *   `PasswordEncoder` para la autenticación basada en la base de datos.
 * - **filterChain bean**: Configura las reglas de seguridad, deshabilitando CSRF, gestionando excepciones de autenticación y
 *   definiendo la política de sesiones sin estado (stateless). Además, configura el acceso a las URL protegidas y las
 *   URL públicas. Añade el filtro `AuthTokenFilter` antes del filtro de autenticación por defecto de Spring Security.*
 * Esta configuración permite que los usuarios accedan a los recursos protegidos solo si están autenticados correctamente
 * mediante un token JWT. También se proporciona flexibilidad para definir permisos más específicos a través de anotaciones
 * de seguridad.
 */

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class AppConfig {


    private final JwtAuthEntryPoint jwtAuthEntryPoint;
    private final MovieUserDetailsService movieUserDetailsService;

    private static final List<String> SECURED_URLS =
            List.of("/api/v1/users/**", "/api/v1/movies/**", "/api/v1/show/**");

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
    @Bean
    public AuthTokenFilter authTokenFilter() {
        return new AuthTokenFilter();
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig)  throws Exception{
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        var authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(movieUserDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling(exception->exception.authenticationEntryPoint(jwtAuthEntryPoint))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorizeRequests ->authorizeRequests.requestMatchers(SECURED_URLS.toArray(String[]::new)).authenticated()
                        .anyRequest().permitAll());
        http.authenticationProvider(daoAuthenticationProvider());
        http.addFilterBefore(authTokenFilter(),  UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
