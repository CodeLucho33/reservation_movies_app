# 🎬 Reservation Movies App

Este es un backend para un sistema de reservación de películas que permite a los usuarios registrarse, iniciar sesión, navegar por películas, reservar asientos para funciones específicas y gestionar sus reservas. También cuenta con funcionalidades administrativas para manejar películas, horarios y reportes.

## 🚀 Características

- 🔐 Autenticación y autorización con JWT  
- 👤 Gestión de usuarios con roles (`ADMIN`, `USER`)  
- 🎥 CRUD completo de películas y funciones (solo para admins)  
- 🎟️ Reservación de asientos (solo para usuarios regulares)  
- 📊 Reportes de reservas, capacidad y ganancias (solo para admins)  
- 📚 Documentación de la API con Swagger/OpenAPI  

---

## 🧱 Tecnologías

- Java 21  
- Spring Boot 3.4.4  
- Spring Security + JWT  
- Spring Data JPA (Hibernate)  
- MySQL  
- Maven  
- ModelMapper  
- Dotenv para variables de entorno  
- Swagger para documentación  

---

## 📦 Dependencias principales (`pom.xml`)

- `spring-boot-starter-web`  
- `spring-boot-starter-data-jpa`  
- `spring-boot-starter-security`  
- `jjwt` para autenticación JWT  
- `lombok` para reducir código repetitivo  
- `springdoc-openapi` para documentación  

---

## ⚙️ Requisitos

- Java 21  
- MySQL 8+  
- Maven 3.8+  

---

## 🧪 Instalación

1. **Clona el repositorio**

```bash
git clone https://github.com/CodeLucho33/reservation_movies_app.git
cd reservation_movies_app

2. **Configuración BD**

```bash
DB_URL=jdbc:mysql://localhost:3306/reservation_movies
DB_USERNAME=tu_usuario
DB_PASSWORD=tu_contraseña
JWT_SECRET=app_jwtSecret
---
3. **Estructura del proyecto**
```bash
src/
├── config/              # Configuración de seguridad, Swagger, etc.
├── controller/          # Controladores REST (Movie, User, ShowTime, etc.)
├── dto/                 # Data Transfer Objects
├── model/               # Entidades (Movie, Room, Seat, Reservation, User)
├── repository/          # Interfaces JPA
├── service/             # Lógica de negocio
└── ReservationMoviesApp.java  # Clase principal


---
---
4 . **🛡️ Roles y Acceso**
ADMIN puede:

Crear/editar/eliminar películas, salas y funciones

Crear usuarios con cualquier rol

USER puede:

Ver películas y funciones disponibles

Reservar asientos
---
---
5. Accede a la documentación Swagger
---

