package com.example.backend.domain.usuario

import com.example.backend.dto.LoginRequest
import com.example.backend.dto.LoginResponse // Nuevo import
import com.example.backend.dto.RegistroRequest
import com.example.backend.dto.UsuarioResponse
import com.example.backend.security.JwtTokenProvider // Nuevo import
import org.springframework.security.authentication.AuthenticationManager // Nuevo import
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken // Nuevo import
import org.springframework.security.core.context.SecurityContextHolder // Nuevo import
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.format.DateTimeParseException

@Service
class UsuarioService(
    private val usuarioRepository: UsuarioRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtTokenProvider: JwtTokenProvider, // Inyectar
    private val authenticationManager: AuthenticationManager, // Inyectar
    private val imagenRepo: UsuarioImagenRepository
) {

    fun registrar(req: RegistroRequest): UsuarioResponse {
        if (usuarioRepository.existsByUsername(req.username)) {
            throw IllegalArgumentException("El nombre de usuario '${req.username}' ya está en uso.")
        }
        if (usuarioRepository.existsByEmail(req.email)) {
            throw IllegalArgumentException("El email '${req.email}' ya está registrado.")
        }

        val fechaNacimiento: LocalDate? = try {
            LocalDate.parse(req.birthDate)
        } catch (e: DateTimeParseException) {
            throw IllegalArgumentException("Formato de fecha inválido. Usar YYYY-MM-DD.")
        }

        val usuario = Usuario(
            username = req.username,
            email = req.email,
            passwordHash = passwordEncoder.encode(req.password),
            name = req.name,
            lastName = req.lastName,
            birthDate = fechaNacimiento,
            roles = "USER"
        )
        val usuarioGuardado = usuarioRepository.save(usuario)
        val profile = imagenRepo.findByUsuarioIdAndProfileTrue(usuarioGuardado.id!!)
        val profileBase64 = profile?.let { "data:${it.contentType};base64:${java.util.Base64.getEncoder().encodeToString(it.data)}" }
        return UsuarioResponse(
            id = usuarioGuardado.id!!,
            username = usuarioGuardado.username,
            email = usuarioGuardado.email,
            roles = usuarioGuardado.roles,
            name = usuarioGuardado.name,
            lastName = usuarioGuardado.lastName,
            birthDate = usuarioGuardado.birthDate,
            profileImageBase64 = profileBase64
        )
    }

    fun login(req: LoginRequest): LoginResponse {
        // 1. Spring Security autentica
        val authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                req.email,
                req.password
            )
        )

        SecurityContextHolder.getContext().authentication = authentication

        val userDetails = authentication.principal as org.springframework.security.core.userdetails.User

        val token = jwtTokenProvider.generateToken(userDetails)

        val usuario = usuarioRepository.findByEmail(req.email).get()
        val profile = imagenRepo.findByUsuarioIdAndProfileTrue(usuario.id!!)
        val profileBase64 = profile?.let { "data:${it.contentType};base64:${java.util.Base64.getEncoder().encodeToString(it.data)}" }

        val usuarioResp = UsuarioResponse(
            id = usuario.id!!,
            username = usuario.username,
            email = usuario.email,
            roles = usuario.roles,
            name = usuario.name,
            lastName = usuario.lastName,
            birthDate = usuario.birthDate,
            profileImageBase64 = profileBase64
        )

        return LoginResponse(token = token, usuario = usuarioResp)
    }
}

// Función de extensión simple para convertir Entidad -> DTO

fun Usuario.toResponse(): UsuarioResponse {
    return UsuarioResponse(
        id = this.id!!,
        username = this.username,
        email = this.email,
        roles = this.roles,

        name = this.name,
        lastName = this.lastName,
        birthDate = this.birthDate,
        profileImageBase64 = null
    )
}