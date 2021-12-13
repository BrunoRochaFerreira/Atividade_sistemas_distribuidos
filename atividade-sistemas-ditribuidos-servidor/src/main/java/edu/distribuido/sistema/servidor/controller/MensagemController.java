package edu.distribuido.sistema.servidor.controller;

import edu.distribuido.sistema.servidor.dto.MensagemRepository;
import edu.distribuido.sistema.servidor.dto.UsuarioRepository;
import edu.distribuido.sistema.servidor.model.Mensagem;
import edu.distribuido.sistema.servidor.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/mensagens")
public class MensagemController {

    @Autowired
    private MensagemRepository mensagemRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping
    public ResponseEntity<Object> enviar(@RequestBody Mensagem mensagem) {
        String emailUsuarioEmissor = mensagem.getEmissor().getEmail();
        String emailUsuarioReceptor = mensagem.getReceptor().getEmail();

        Optional<Usuario> usuarioEmissor = usuarioRepository.findByEmail(emailUsuarioEmissor);
        Optional<Usuario> usuarioReceptor = usuarioRepository.findByEmail(emailUsuarioReceptor);

        if (usuarioEmissor.isEmpty()) {
            return new ResponseEntity<>("Emissor não encontrado!", HttpStatus.BAD_REQUEST);
        }
        if (usuarioReceptor.isEmpty()) {
            return new ResponseEntity<>("Receptor não encontrado!", HttpStatus.BAD_REQUEST);
        }

        mensagem.setEmissor(usuarioEmissor.get());
        mensagem.setReceptor(usuarioReceptor.get());

        Mensagem mensagemSalva = mensagemRepository.save(mensagem);

        return new ResponseEntity<>(mensagemSalva, HttpStatus.OK);
    }

    @GetMapping("/enviadas/{email}")
    public ResponseEntity<Object> obterEnviados(@PathVariable String email) {
        if (!usuarioRepository.existsByEmail(email)) {
            return new ResponseEntity<>("Usuário não encontrado!", HttpStatus.BAD_REQUEST);
        }

        List<Mensagem> mensagensEnviadas = mensagemRepository.findAllByEmissor_Email(email);

        return new ResponseEntity<>(mensagensEnviadas, HttpStatus.OK);
    }

    @GetMapping("/recebidas/{email}")
    public ResponseEntity<Object> obterRecebidas(@PathVariable String email) {
        if (!usuarioRepository.existsByEmail(email)) {
            return new ResponseEntity<>("Usuário não encontrado!", HttpStatus.BAD_REQUEST);
        }

        List<Mensagem> mensagensRecebidas = mensagemRepository.findAllByReceptor_Email(email);

        return new ResponseEntity<>(mensagensRecebidas, HttpStatus.OK);
    }

}
