package com.example.ProyectoInventario.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ProyectoInventario.entity.Usuario;
import com.example.ProyectoInventario.repository.UsuarioRepository;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "https://invproyec.netlify.app", allowCredentials = "true")
public class AuthController {

    @Autowired
    private UsuarioRepository usuarioRepo;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginData, HttpServletResponse res) {
        String username = loginData.get("username");
        String password = loginData.get("password");

        Usuario user = usuarioRepo.findByUsername(username);
        if (user == null || !user.getPassword().equals(password)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales incorrectas");
        }

        Cookie cookie = new Cookie("rol", user.getRol().name());
        cookie.setPath("/");
        cookie.setMaxAge(3600);
        cookie.setHttpOnly(false);
        res.addCookie(cookie);

        return ResponseEntity.ok(Map.of("usuario", user.getUsername(), "rol", user.getRol()));
    }

    @GetMapping("/rol")
    public ResponseEntity<?> validarRol(@CookieValue(value = "rol", defaultValue = "") String rol) {
        if (rol.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No autenticado");
        }
        return ResponseEntity.ok(Map.of("rol", rol));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse res) {
        Cookie cookie = new Cookie("rol", null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        res.addCookie(cookie);
        return ResponseEntity.ok().build();
    }
}
