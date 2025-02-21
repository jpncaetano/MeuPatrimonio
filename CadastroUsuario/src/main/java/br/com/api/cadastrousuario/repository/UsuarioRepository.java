package br.com.api.cadastrousuario.repository;

import br.com.api.cadastrousuario.exception.UsuarioJaCadastradoException;
import br.com.api.cadastrousuario.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Verifica se cpf já está cadastrado
    boolean existsByCpf(String cpf);

    // Busca usuário por CPF
    Optional<Usuario> findByCpf(String cpf);

}
