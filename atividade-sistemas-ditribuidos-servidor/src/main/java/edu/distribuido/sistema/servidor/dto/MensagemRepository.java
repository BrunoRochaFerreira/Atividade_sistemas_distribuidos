package edu.distribuido.sistema.servidor.dto;

import edu.distribuido.sistema.servidor.model.Mensagem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MensagemRepository extends JpaRepository<Mensagem, Long> {
    List<Mensagem> findAllByEmissor_Email(String emissorEmail);
    List<Mensagem> findAllByReceptor_Email(String receptorEmail);
}
