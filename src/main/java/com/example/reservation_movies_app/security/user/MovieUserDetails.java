package com.example.reservation_movies_app.security.user;

import com.example.reservation_movies_app.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;


/**
 * Esta clase implementa la interfaz UserDetails de Spring Security, permitiendo que el sistema
 * maneje la autenticación y autorización de usuarios de manera estándar.
 *
 * MovieUserDetails almacena información esencial del usuario, incluyendo:
 * - ID del usuario.
 * - Correo electrónico (usado como nombre de usuario en la autenticación).
 * - Contraseña encriptada.
 * - Lista de roles/autorizaciones del usuario.
 *
 * La clase también proporciona un método estático buildUserDetails(User user),
 * que transforma un objeto User de la base de datos en una instancia de MovieUserDetails.
 * Esto permite que Spring Security pueda validar y gestionar usuarios correctamente.
 *
 * Además, implementa métodos de UserDetails para verificar el estado de la cuenta,
 * como si está bloqueada, expirada o habilitada.
 *
 * Esta implementación está diseñada para manejar un único rol por usuario.
 * Si en el futuro se requiere soportar múltiples roles, se deberá modificar
 * la conversión de roles en el método buildUserDetails.
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovieUserDetails implements UserDetails {
    private Long id;
    private String email;
    private String password;
    private  Collection<GrantedAuthority> authorities;

    public static MovieUserDetails buildUserDetails(User user) {
        List<GrantedAuthority> authorities = List.of(
                new SimpleGrantedAuthority(user.getRole().toString())
        );

        return new MovieUserDetails(
                user.getId(),
                user.getEmail(),
                user.getPassword(),
                authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }

}
