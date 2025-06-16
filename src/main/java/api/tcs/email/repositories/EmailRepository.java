package api.tcs.email.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import api.tcs.email.models.Email;

public interface EmailRepository extends JpaRepository<Email,Integer>{
    
    @Query(value = "SELECT * FROM emails WHERE remetente = ? AND status = 0", nativeQuery = true)
    List<Email> getAllRascunhosUsuario(Integer remetente);

    @Query(value = "SELECT * FROM emails WHERE destinatario = ? AND status > 0", nativeQuery = true)
    List<Email> getAllEmailsUsuario(Integer destinatario);
}
