package br.com.api.cadastrousuario.repository;

import br.com.api.cadastrousuario.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    //Verifica se cpd já está cadastrado
    boolean existsByCpf(String cpf);
}
