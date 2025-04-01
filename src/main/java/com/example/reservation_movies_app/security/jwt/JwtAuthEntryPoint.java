package com.example.reservation_movies_app.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * JwtAuthEntryPoint es un componente que maneja los errores de autenticación cuando un usuario intenta acceder a recursos
 * protegidos sin estar autenticado. Implementa la interfaz AuthenticationEntryPoint de Spring Security, que es responsable
 * de responder con un mensaje de error adecuado cuando ocurre un fallo de autenticación.
 *
 * Funcionalidad:
 * - **commence**: Este método es invocado cuando se produce una excepción de autenticación (por ejemplo, cuando un usuario
 *   intenta acceder a un recurso protegido sin proporcionar un token JWT válido).
 *   - Establece el tipo de contenido de la respuesta como JSON.
 *   - Establece el estado de la respuesta HTTP a **401 Unauthorized**.
 *   - Construye un cuerpo de respuesta con un mensaje de error que indica que el usuario no está autorizado.
 *   - Utiliza la clase `ObjectMapper` para convertir el mapa de errores en formato JSON y escribirlo en el flujo de salida de la respuesta.
 *
 * Este componente es útil para manejar errores de autorización y proporcionar respuestas claras y estandarizadas en
 * formato JSON cuando un usuario no está autenticado.
 */

@Component
public class JwtAuthEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException)
            throws IOException, ServletException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        final Map<String, Object> body = new HashMap<>();
        body.put("error", "Unauthorized");
        body.put("message", "You may need to be logged in and to access this page");

        final ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), body);

    }
}