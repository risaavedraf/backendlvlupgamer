package com.example.backend.config

import com.example.backend.domain.catalogo.Categoria
import com.example.backend.domain.catalogo.CategoriaRepository
import com.example.backend.domain.catalogo.Producto
import com.example.backend.domain.catalogo.ProductoRepository
import com.example.backend.domain.direccion.Direccion
import com.example.backend.domain.direccion.DireccionRepository
import com.example.backend.domain.evento.Evento
import com.example.backend.domain.evento.EventoRepository
import com.example.backend.domain.pedido.EstadoPedido
import com.example.backend.domain.pedido.EstadoPedidoRepository
import com.example.backend.domain.rol.Rol
import com.example.backend.domain.rol.RolRepository
import com.example.backend.domain.usuario.Usuario
import com.example.backend.domain.usuario.UsuarioRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.security.crypto.password.PasswordEncoder
import java.time.LocalDate
import java.time.LocalDateTime

@Configuration
@Profile("dev")
class DataInitializer(
    private val rolRepository: RolRepository,
    private val usuarioRepository: UsuarioRepository,
    private val passwordEncoder: PasswordEncoder,
    private val categoriaRepository: CategoriaRepository,
    private val productoRepository: ProductoRepository,
    private val eventoRepository: EventoRepository,
    private val estadoPedidoRepository: EstadoPedidoRepository,
    private val direccionRepository: DireccionRepository
) {

    @Bean
    fun initDatabase(): CommandLineRunner {
        return CommandLineRunner {
            initRoles()
            initUsuarios()
            initCatalogo()
            initEventos()
            initEstadosPedido()
            initDirecciones()
        }
    }

    private fun initRoles() {
        if (rolRepository.count() == 0L) {
            rolRepository.save(Rol(nombre = "ROLE_USER"))
            rolRepository.save(Rol(nombre = "ROLE_ADMIN"))
        }
    }

    private fun initUsuarios() {
        val userRole = rolRepository.findByNombre("ROLE_USER").get()
        val adminRole = rolRepository.findByNombre("ROLE_ADMIN").get()

        if (!usuarioRepository.existsByEmail("admin@example.com")) {
            val adminUser = Usuario(username = "admin", email = "admin@example.com", passwordHash = passwordEncoder.encode("admin123"), name = "Admin", lastName = "User", roles = mutableSetOf(userRole, adminRole))
            usuarioRepository.save(adminUser)
        }
        if (!usuarioRepository.existsByEmail("optimizado@example.com")) {
            val testUser = Usuario(username = "testoptimizado", email = "optimizado@example.com", passwordHash = passwordEncoder.encode("password123"), name = "Test", lastName = "Optimizado", birthDate = LocalDate.parse("1999-12-31"), roles = mutableSetOf(userRole))
            usuarioRepository.save(testUser)
        }
    }

    private fun initCatalogo() {
        if (categoriaRepository.count() > 0L) return

        // Crear Categorías
        val catJuegos = categoriaRepository.save(Categoria(nombre = "Juegos de Mesa"))
        val catAcc = categoriaRepository.save(Categoria(nombre = "Accesorios"))
        val catConsolas = categoriaRepository.save(Categoria(nombre = "Consolas"))
        val catComp = categoriaRepository.save(Categoria(nombre = "Computadores Gamers"))
        val catSillas = categoriaRepository.save(Categoria(nombre = "Sillas Gamers"))
        val catMouse = categoriaRepository.save(Categoria(nombre = "Mouse"))
        val catMousepad = categoriaRepository.save(Categoria(nombre = "Mousepad"))
        val catPoleras = categoriaRepository.save(Categoria(nombre = "Poleras Personalizadas"))
        val catTeclados = categoriaRepository.save(Categoria(nombre = "Teclados"))
        val catAuriculares = categoriaRepository.save(Categoria(nombre = "Auriculares"))
        val catMonitores = categoriaRepository.save(Categoria(nombre = "Monitores"))

        // Crear Productos (sin especificar el ID)
        productoRepository.saveAll(listOf(
            Producto(categoria = catJuegos, nombre = "Catan", precio = 29990.0, descripcion = "Un clásico juego de estrategia donde los jugadores compiten por colonizar y expandirse en la isla de Catan.", stock = 15),
            Producto(categoria = catJuegos, nombre = "Carcassonne", precio = 24990.0, descripcion = "Un juego de colocación de fichas donde los jugadores construyen el paisaje alrededor de la fortaleza medieval de Carcassonne.", stock = 20),
            Producto(categoria = catAcc, nombre = "Controlador Inalámbrico Xbox Series X", precio = 59990.0, descripcion = "Ofrece una experiencia de juego cómoda con botones mapeables y una respuesta táctil mejorada.", stock = 25),
            Producto(categoria = catAuriculares, nombre = "Auriculares Gamer HyperX Cloud II", precio = 79990.0, descripcion = "Proporcionan un sonido envolvente de calidad con un micrófono desmontable.", stock = 30),
            Producto(categoria = catConsolas, nombre = "PlayStation 5", precio = 549990.0, descripcion = "La consola de última generación de Sony, que ofrece gráficos impresionantes y tiempos de carga ultrarrápidos.", stock = 5),
            Producto(categoria = catComp, nombre = "PC Gamer ASUS ROG Strix", precio = 1299990.0, descripcion = "Un potente equipo diseñado para los gamers más exigentes.", stock = 3),
            Producto(categoria = catSillas, nombre = "Silla Gamer Secretlab Titan", precio = 349990.0, descripcion = "Diseñada para el máximo confort, esta silla ofrece un soporte ergonómico.", stock = 10),
            Producto(categoria = catMouse, nombre = "Mouse Gamer Logitech G502 HERO", precio = 49990.0, descripcion = "Con sensor de alta precisión y botones personalizables.", stock = 40),
            Producto(categoria = catMousepad, nombre = "Mousepad Razer Goliathus Extended Chroma", precio = 29990.0, descripcion = "Ofrece un área de juego amplia con iluminación RGB personalizable.", stock = 50),
            Producto(categoria = catPoleras, nombre = "Polera Gamer Personalizada 'Level-Up'", precio = 14990.0, descripcion = "Una camiseta cómoda y estilizada, con la posibilidad de personalizarla.", stock = 100),
            Producto(categoria = catTeclados, nombre = "Teclado Gamer Redragon", precio = 49.99, descripcion = "Teclado mecánico RGB con retroiluminación personalizable.", stock = 10),
            Producto(categoria = catMouse, nombre = "Mouse Gamer Logitech", precio = 39.99, descripcion = "Mouse ergonómico con alta precisión y botones programables.", stock = 3),
            Producto(categoria = catAuriculares, nombre = "Auriculares Gamer HyperX", precio = 59.99, descripcion = "Auriculares con sonido envolvente y micrófono ajustable.", stock = 0),
            Producto(categoria = catMonitores, nombre = "Monitor Gamer ASUS", precio = 299.99, descripcion = "Monitor 27\" 144Hz con tecnología FreeSync para juegos fluidos.", stock = 6)
        ))
    }

    private fun initEventos() {
        if (eventoRepository.count() > 0L) return

        eventoRepository.saveAll(listOf(
            Evento(
                name = "Torneo de Valorant - Copa Invierno",
                description = "Gran torneo de Valorant con premios en efectivo y periféricos gamer. ¡Inscríbete ya!",
                date = LocalDateTime.of(2025, 12, 15, 18, 0),
                locationName = "Arena Gamer Pro",
                latitude = -33.4489,
                longitude = -70.6693
            ),
            Evento(
                name = "Lanzamiento Cyberpunk 2077 - Phantom Liberty",
                description = "Celebra el lanzamiento de la expansión Phantom Liberty con actividades, sorteos y meet & greet con streamers.",
                date = LocalDateTime.of(2025, 10, 26, 15, 0),
                locationName = "Tienda LvlUp Gamer Central",
                latitude = -33.4500,
                longitude = -70.6700
            ),
            Evento(
                name = "Feria Gamer Chile 2025",
                description = "La feria de videojuegos más grande de Chile. Pruebas de juegos, concursos de cosplay, y mucho más.",
                date = LocalDateTime.of(2025, 11, 20, 10, 0),
                locationName = "Espacio Riesco",
                latitude = -33.3600,
                longitude = -70.6000
            )
        ))
    }

    private fun initEstadosPedido() {
        if (estadoPedidoRepository.count() == 0L) {
            estadoPedidoRepository.saveAll(listOf(
                EstadoPedido(nombre = "PENDIENTE", descripcion = "El pedido ha sido recibido y está esperando confirmación."),
                EstadoPedido(nombre = "PROCESANDO", descripcion = "El pedido está siendo preparado para el envío."),
                EstadoPedido(nombre = "ENVIADO", descripcion = "El pedido ha sido enviado al cliente."),
                EstadoPedido(nombre = "ENTREGADO", descripcion = "El pedido ha sido entregado al cliente."),
                EstadoPedido(nombre = "CANCELADO", descripcion = "El pedido ha sido cancelado.")
            ))
        }
    }

    private fun initDirecciones() {
        if (direccionRepository.count() == 0L) {
            val testUser = usuarioRepository.findByEmail("optimizado@example.com").orElse(null)
            testUser?.let { user ->
                val direccionCasa = Direccion(
                    nombre = "Casa",
                    nombreDestinatario = user.name + " " + user.lastName,
                    calle = "Av. Siempre Viva",
                    numeroCasa = "742",
                    numeroDepartamento = null,
                    comuna = "Springfield",
                    ciudad = "Springfield",
                    region = "O'Higgins",
                    codigoPostal = "1234567",
                    usuario = user
                )
                direccionRepository.save(direccionCasa)
                // Eliminadas las líneas:
                // user.direcciones.add(direccionCasa)
                // usuarioRepository.save(user)
            }
        }
    }
}
