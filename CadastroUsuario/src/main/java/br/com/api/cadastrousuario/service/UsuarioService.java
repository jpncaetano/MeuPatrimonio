package br.com.api.cadastrousuario.service;

import br.com.api.cadastrousuario.dto.UsuarioAtualizadoDTO;
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

    // Alterar todos os Dados do Cadastro
    public Usuario alterarTodosOsDados(Long id, UsuarioAtualizadoDTO dto) {
        Usuario usuario = buscarUsuarioPorId(id);

        ValidaUsuarioUtil.validarNome(dto.getNome());
        ValidaUsuarioUtil.validarDataNascimento(dto.getDataNascimento());
        ValidaUsuarioUtil.validarCpf(dto.getCpf(), id, usuarioRepository);

        usuario.setNome(dto.getNome());
        usuario.setCpf(dto.getCpf().replaceAll("[^0-9]", ""));
        usuario.setDataNascimento(dto.getDataNascimento());

        return usuarioRepository.save(usuario);
    }

    // Deleta usuário por ID
    public void deletarUsuario(Long id) {
        Usuario usuario = buscarUsuarioPorId(id);
        usuarioRepository.delete(usuario);
    }
}
