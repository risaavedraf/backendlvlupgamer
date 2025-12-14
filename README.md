# LVLUP GAMER - Backend

## Descripción General

Este proyecto es el backend para la tienda de comercio electrónico "LVLUP GAMER". Proporciona una API RESTful para gestionar productos, categorías, usuarios, pedidos, y más. Está construido con Kotlin y Spring Boot.

## Características Principales

-   Gestión de catálogos: Productos y categorías con operaciones CRUD.
-   Autenticación y Autorización: Basada en JWT con roles (ADMIN, USER).
-   Gestión de Usuarios: Registro, inicio de sesión, y perfiles de usuario.
-   Carrito de Compras: Añadir, actualizar, y eliminar productos del carrito.
-   Pedidos (Checkout): Creación de pedidos a partir del carrito.
-   Gestión de Pedidos: Visualización y actualización del estado de los pedidos para administradores.
-   Gestión de Direcciones: CRUD para las direcciones de envío del usuario.
-   Reseñas de Productos: Permite a los usuarios añadir reseñas a los productos.
-   Eventos: Gestión de eventos de la tienda.
-   Imágenes: Subida y recuperación de imágenes para productos, eventos y usuarios.

## Tecnologías Utilizadas

-   **Lenguaje:** Kotlin
-   **Framework:** Spring Boot
-   **Base de Datos:** PostgreSQL
-   **Autenticación:** Spring Security con JSON Web Tokens (JWT)
-   **ORM:** Spring Data JPA
-   **Build Tool:** Maven
-   **Documentación API:** SpringDoc (OpenAPI 3 / Swagger)

## Requisitos Previos

-   JDK 17 o superior.
-   Maven 3.6 o superior.
-   Una instancia de PostgreSQL en ejecución.

## Instalación y Ejecución

1.  **Clonar el repositorio:**
    ```bash
    git clone <URL_DEL_REPOSITORIO>
    cd AppMovilTiendaLVLUPGAMER/backendlvlupgamer/backend
    ```

2.  **Configurar la base de datos:**
    -   Abre el archivo `src/main/resources/application-dev.properties`.
    -   Modifica las siguientes propiedades para que coincidan con tu configuración de PostgreSQL:
        ```properties
        spring.datasource.url=jdbc:postgresql://localhost:5432/lvlupgamer
        spring.datasource.username=user
        spring.datasource.password=password
        ```

3.  **Ejecutar la aplicación:**
    Puedes ejecutar la aplicación utilizando el wrapper de Maven incluido.

    En Windows:
    ```bash
    mvnw.cmd spring-boot:run
    ```

    En macOS/Linux:
    ```bash
    ./mvnw spring-boot:run
    ```

    La aplicación se iniciará en `http://localhost:8080`.

## Configuración

-   **Puerto de la aplicación:** El puerto por defecto es `8080`, se puede cambiar en `application.properties`.
-   **Configuración de JWT:** El secreto y la expiración del token JWT se encuentran en `application-dev.properties` (`app.jwt.secret` y `app.jwt.expiration-in-ms`).
-   **Perfiles de Spring:** El perfil por defecto es `dev`. Puedes cambiarlo en `application.properties` (`spring.profiles.active`).

## Documentación de la API (Swagger)

Una vez que la aplicación está en ejecución, puedes acceder a la documentación interactiva de la API a través de Swagger UI en la siguiente URL:

[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

## API Endpoints

A continuación se muestra un resumen de los endpoints disponibles.

---


### Autenticación (`/api/auth`)

-   `POST /register`: Registrar un nuevo usuario.
-   `POST /login`: Iniciar sesión y obtener un token JWT.

---


### Perfil de Usuario (`/api/perfil`)

-   `GET /me`: Obtener el perfil del usuario autenticado.
-   `PUT /me`: Actualizar el perfil del usuario autenticado.

---


### Catálogo - Productos (`/api/productos`)

-   `GET /`: Buscar productos con filtros opcionales.
-   `GET /{id}`: Obtener un producto por su ID.

---


### Catálogo - Categorías (`/api/categorias`)

-   `GET /`: Listar todas las categorías.

---


### Carrito de Compras (`/api/carrito`)

-   `GET /`: Obtener el carrito del usuario.
-   `POST /items`: Añadir un producto al carrito.
-   `PUT /items/{productoId}`: Actualizar la cantidad de un producto.
-   `DELETE /items/{productoId}`: Eliminar un producto del carrito.
-   `DELETE /`: Vaciar el carrito.
-   `POST /cupon`: Aplicar un cupón de descuento.
-   `DELETE /cupon`: Quitar el cupón.

---


### Pedidos (`/api/pedidos`)

-   `POST /checkout`: Realizar un pedido (checkout).
-   `GET /me`: Obtener el historial de pedidos del usuario.

---


### Direcciones (`/api/direcciones`)

-   `GET /`: Listar las direcciones del usuario.
-   `GET /{id}`: Obtener una dirección por ID.
-   `POST /`: Crear una nueva dirección.
-   `PUT /{id}`: Actualizar una dirección.
-   `DELETE /{id}`: Eliminar una dirección.

---


### Reseñas (`/api/productos/{productoId}/reviews`)

-   `GET /`: Listar las reseñas de un producto.
-   `POST /`: Crear una nueva reseña para un producto.

---


### Eventos (`/api/eventos`)

-   `GET /`: Listar todos los eventos.
-   `GET /{id}`: Obtener un evento por ID.

---


### Administración (Requiere Rol ADMIN)

#### Gestión de Productos (`/api/admin/productos`)

-   `GET /`: Listar todos los productos.
-   `GET /{id}`: Obtener un producto por ID.
-   `POST /`: Crear un nuevo producto.
-   `PUT /{id}`: Actualizar un producto.
-   `DELETE /{id}`: Eliminar un producto.

#### Gestión de Categorías (`/api/admin/categorias`)

-   `GET /`: Listar todas las categorías.
-   `POST /`: Crear una nueva categoría.

#### Gestión de Pedidos (`/api/admin/pedidos`)

-   `GET /`: Listar todos los pedidos.
-   `GET /{id}`: Obtener un pedido por ID.
-   `PUT /{id}/estado`: Actualizar el estado de un pedido.

#### Gestión de Usuarios (`/api/admin/usuarios`)

-   `GET /`: Listar todos los usuarios.
-   `POST /{userId}/roles`: Asignar un rol a un usuario.
-   `DELETE /{userId}/roles/{rolId}`: Remover un rol de un usuario.

#### Gestión de Roles (`/api/roles`)

-   `GET /`: Listar todos los roles.
-   `POST /`: Crear un nuevo rol.
-   `DELETE /{id}`: Eliminar un rol.

#### Gestión de Eventos (`/api/admin/eventos`)

-   `GET /`: Listar todos los eventos.
-   `POST /`: Crear un nuevo evento.
-   `PUT /{id}`: Actualizar un evento.

---


### Gestión de Imágenes

-   `/api/productos/{productoId}/imagenes`: CRUD de imágenes para productos.
-   `/api/eventos/{eventoId}/imagenes`: CRUD de imágenes para eventos.
-   `/api/usuarios/{id}/images`: CRUD de imágenes para usuarios.

## Estructura del Proyecto

```
backend/
├── src/
│   ├── main/
│   │   ├── kotlin/
│   │   │   └── com/example/backend/
│   │   │       ├── BackendLvlupgamerApplication.kt  (Punto de entrada)
│   │   │       ├── config/             (Configuración de Spring Security, CORS, etc.)
│   │   │       ├── domain/             (Modelos de dominio, repositorios, servicios, controladores)
│   │   │       ├── dto/                (Data Transfer Objects)
│   │   │       ├── exception/          (Manejo de excepciones personalizadas)
│   │   │       └── security/           (Clases relacionadas con JWT y UserDetails)
│   │   └── resources/
│   │       ├── application.properties  (Configuración principal)
│   │       └── application-dev.properties (Configuración para el perfil de desarrollo)
│   └── test/
│       └── ... (Clases de Test)
├── pom.xml                         (Dependencias y configuración de Maven)
└── mvnw / mvnw.cmd                 (Maven Wrapper)
```
