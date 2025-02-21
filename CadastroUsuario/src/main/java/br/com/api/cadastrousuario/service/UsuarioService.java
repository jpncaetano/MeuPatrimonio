package br.com.api.cadastrousuario.service;

import br.com.api.cadastrousuario.dto.UsuarioAtualizadoDTO;
import br.com.api.cadastrousuario.exception.CpfInvalidoException;
import br.com.api.cadastrousuario.exception.DataNascimentoInvalidaException;
import br.com.api.cadastrousuario.exception.UsuarioJaCadastradoException;
import br.com.api.cadastrousuario.exception.UsuarioNaoEncontradoException;
import br.com.api.cadastrousuario.model.Usuario;
import br.com.api.cadastrousuario.repository.UsuarioRepository;
import br.com.api.cadastrousuario.util.CPFUtil;
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
        // Remove caracteres especiais do CPF
        String cpfLimpo = usuario.getCpf().replaceAll("[^0-9]", "");

        // Valida CPF
        if (!CPFUtil.isValidCPF(cpfLimpo)) {
            throw new CpfInvalidoException("CPF inválido!");
        }

        // Verifica se já existe um usuário com esse CPF no banco
        if (usuarioRepository.existsByCpf(cpfLimpo)) {
            throw new UsuarioJaCadastradoException("Já existe um usuário com esse CPF.");
        }

        // Valida a data de nascimento (não pode ser futura)
        if (usuario.getDataNascimento().isAfter(LocalDate.now())) {
            throw new DataNascimentoInvalidaException("A data de nascimento não pode ser no futuro.");
        }

        // Salva o CPF formatado corretamente
        usuario.setCpf(cpfLimpo);
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

    // Méthodo reutilizável para buscar usuário por Id
    public Usuario buscarUsuarioPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário com ID " + id + " não encontrado!"));
    }

    // Alterar Nome
    public Usuario alterarNome(Long id, String novoNome) {
        Usuario usuario = buscarUsuarioPorId(id);
        usuario.setNome(novoNome);
        return usuarioRepository.save(usuario);
    }

    // Alterar Data de Nascimento
    public Usuario alterarDataNascimento(Long id, LocalDate novaDataNascimento) {
        Usuario usuario = buscarUsuarioPorId(id);
        usuario.setDataNascimento(novaDataNascimento);
        return usuarioRepository.save(usuario);
    }

    // Alterar todos os Dados do Cadastro
    public Usuario alterarTodosOsDados(Long id, UsuarioAtualizadoDTO dto) {
        Usuario usuario = buscarUsuarioPorId(id);

        // Atualiza os dados
        usuario.setNome(dto.getNome());
        usuario.setDataNascimento(dto.getDataNascimento());

        return usuarioRepository.save(usuario);
    }

    // Deleta usuário por Id
    public void deletarUsuario(Long id) {
        Usuario usuario = buscarUsuarioPorId(id);
        usuarioRepository.delete(usuario);
    }

}
