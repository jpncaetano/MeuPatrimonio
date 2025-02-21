package br.com.api.cadastrousuario.controller;

import br.com.api.cadastrousuario.model.Usuario;
import br.com.api.cadastrousuario.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // GET - Buscar todos os usuários
    @GetMapping
    public ResponseEntity<List<Usuario>> listarTodosUsuarios() {
        List<Usuario> usuarios = usuarioService.findAll();
        return ResponseEntity.ok(usuarios);
    }

    // GET - Buscar usuário por ID
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarUsuarioPorId(@PathVariable Long id) {
        Usuario usuario = usuarioService.buscarUsuarioPorId(id);
        return ResponseEntity.ok(usuario);
    }

    // POST - Criar novo usuário
    @PostMapping
    public ResponseEntity<Usuario> criaNovoUsuario(@Valid @RequestBody Usuario usuario) {
        Usuario novoUsuario = usuarioService.criaNovoUsuario(usuario);

        // Criar URI para Location Header
        URI location = URI.create("/usuarios/" + novoUsuario.getId());

        // Retornar 201 CREATED com Location Header
        return ResponseEntity.created(location).body(novoUsuario);
    }


    // PUT - Alterar Nome
    @PutMapping("/{id}/nome")
    public ResponseEntity<Usuario> alterarNome(@PathVariable Long id, @RequestBody Map<String, String> body) {
        String novoNome = body.get("novoNome");
        Usuario usuarioAtualizado = usuarioService.alterarNome(id, novoNome);
        return ResponseEntity.ok(usuarioAtualizado);
    }

    // PUT - Alterar CPF
    @PutMapping("/{id}/cpf")
    public ResponseEntity<Usuario> alterarCpf(@PathVariable Long id, @RequestBody Map<String, String> body) {
        String novoCpf = body.get("novoCpf");
        Usuario usuarioAtualizado = usuarioService.alterarCpf(id, novoCpf);
        return ResponseEntity.ok(usuarioAtualizado);
    }


    // PUT - Alterar Data de Nascimento
    @PutMapping("/{id}/dataNascimento")
    public ResponseEntity<Usuario> alterarDataNascimento(@PathVariable Long id, @RequestBody Map<String, String> body) {
        LocalDate novaDataNascimento = LocalDate.parse(body.get("novaDataNascimento"));
        Usuario usuarioAtualizado = usuarioService.alterarDataNascimento(id, novaDataNascimento);
        return ResponseEntity.ok(usuarioAtualizado);
    }

    // PUT - Alterar todos os Dados do Cadastro
    @PutMapping("/{id}/cadastro")
    public ResponseEntity<Usuario> atualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuarioRecebido) {
        Usuario usuarioAtualizado = usuarioService.alterarTodosOsDados(id, usuarioRecebido);
        return ResponseEntity.ok(usuarioAtualizado);
    }

    // DELETE - Deleta usuário
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable Long id) {
        usuarioService.deletarUsuario(id);
        return ResponseEntity.noContent().build(); // Retorna 204 No Content
    }
}
