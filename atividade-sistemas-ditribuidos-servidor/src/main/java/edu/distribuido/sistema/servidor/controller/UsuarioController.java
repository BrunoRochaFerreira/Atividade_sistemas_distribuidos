package edu.distribuido.sistema.servidor.controller;

import edu.distribuido.sistema.servidor.dto.UsuarioRepository;
import edu.distribuido.sistema.servidor.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@CrossOrigin
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository repository;

    @PostMapping
    public ResponseEntity<Object> cadastrarUsuario(@RequestBody Usuario usuario) {
        if (Objects.isNull(usuario.getEmail()) ||
            Objects.isNull(usuario.getNome()) ||
            Objects.isNull(usuario.getSenha())) {
            return new ResponseEntity<>("Dados Incompletos!", HttpStatus.BAD_REQUEST);
        }

        if (repository.existsByEmail(usuario.getEmail())) {
            return new ResponseEntity<>("Email já em uso!", HttpStatus.BAD_REQUEST);
        }

        Usuario usuarioSalvo = repository.save(usuario);

        return new ResponseEntity<>(usuarioSalvo, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<Object> autenticar(@RequestBody Usuario usuario) {
        if (repository.existsByEmailAndSenha(usuario.getEmail(), usuario.getSenha())) {
            return new ResponseEntity<>("Usuário Autenticado!", HttpStatus.OK);
        }
        return new ResponseEntity<>("Usuário ou senha inválidos!", HttpStatus.UNAUTHORIZED);
    }

}
