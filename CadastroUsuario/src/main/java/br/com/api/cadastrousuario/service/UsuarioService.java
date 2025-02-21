package br.com.api.cadastrousuario.service;

import br.com.api.cadastrousuario.exception.UsuarioNaoEncontradoException;
import br.com.api.cadastrousuario.model.Usuario;
import br.com.api.cadastrousuario.repository.UsuarioRepository;
import br.com.api.cadastrousuario.util.ValidaUsuarioUtil;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    // Criar novo usuário
    public Usuario criaNovoUsuario(Usuario usuario) {
        ValidaUsuarioUtil.validarNome(usuario.getNome());
        ValidaUsuarioUtil.validarDataNascimento(usuario.getDataNascimento());
        ValidaUsuarioUtil.validarCpf(usuario.getCpf(), null, usuarioRepository);

        usuario.setCpf(usuario.getCpf().replaceAll("[^0-9]", ""));
        return usuarioRepository.save(usuario);
    }

    // Buscar todos os usuários
    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    // Buscar usuário por ID
    public Optional<Usuario> buscarPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    // Méthodo reutilizável para buscar usuário por ID
    public Usuario buscarUsuarioPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário com ID " + id + " não encontrado!"));
    }

    // Alterar Nome
    public Usuario alterarNome(Long id, String novoNome) {
        ValidaUsuarioUtil.validarNome(novoNome);

        Usuario usuario = buscarUsuarioPorId(id);
        usuario.setNome(novoNome);
        return usuarioRepository.save(usuario);
    }

    // Alterar CPF
    public Usuario alterarCpf(Long id, String novoCpf) {
        Usuario usuario = buscarUsuarioPorId(id);
        ValidaUsuarioUtil.validarCpf(novoCpf, id, usuarioRepository);

        usuario.setCpf(novoCpf.replaceAll("[^0-9]", ""));
        return usuarioRepository.save(usuario);
    }

    // Alterar Data de Nascimento
    public Usuario alterarDataNascimento(Long id, LocalDate novaDataNascimento) {
        ValidaUsuarioUtil.validarDataNascimento(novaDataNascimento);

        Usuario usuario = buscarUsuarioPorId(id);
        usuario.setDataNascimento(novaDataNascimento);
        return usuarioRepository.save(usuario);
    }

    public Usuario alterarTodosOsDados(Long id, Usuario usuarioRecebido) {
        // Busca o usuário no banco pelo ID da URL
        Usuario usuario = buscarUsuarioPorId(id);

        // Se o JSON enviar um ID diferente, ele será ignorado.
        if (usuarioRecebido.getNome() != null) {
            ValidaUsuarioUtil.validarNome(usuarioRecebido.getNome());
            usuario.setNome(usuarioRecebido.getNome());
        }

        if (usuarioRecebido.getCpf() != null) {
            ValidaUsuarioUtil.validarCpf(usuarioRecebido.getCpf(), id, usuarioRepository);
            usuario.setCpf(usuarioRecebido.getCpf().replaceAll("[^0-9]", ""));
        }

        if (usuarioRecebido.getDataNascimento() != null) {
            ValidaUsuarioUtil.validarDataNascimento(usuarioRecebido.getDataNascimento());
            usuario.setDataNascimento(usuarioRecebido.getDataNascimento());
        }

        return usuarioRepository.save(usuario);
    }

    // Deleta usuário por ID
    public void deletarUsuario(Long id) {
        Usuario usuario = buscarUsuarioPorId(id);
        usuarioRepository.delete(usuario);
    }
}
