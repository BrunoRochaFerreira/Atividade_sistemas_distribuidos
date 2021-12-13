package edu.distribuido.sistema.servidor.dto;

import edu.distribuido.sistema.servidor.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    boolean existsByEmail(String email);

    boolean existsByEmailAndSenha(String email, String senha);

    Optional<Usuario> findByEmail(String email);
}
