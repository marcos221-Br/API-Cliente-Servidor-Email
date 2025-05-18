package api.tcs.email.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import api.tcs.email.models.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario,Integer>{
    
    public Optional<Usuario> findByEmail(String email);
}
