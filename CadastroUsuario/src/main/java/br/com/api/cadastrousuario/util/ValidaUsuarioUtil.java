package br.com.api.cadastrousuario.util;

import br.com.api.cadastrousuario.exception.CpfInvalidoException;
import br.com.api.cadastrousuario.exception.DataNascimentoInvalidaException;
import br.com.api.cadastrousuario.exception.UsuarioJaCadastradoException;
import br.com.api.cadastrousuario.model.Usuario;
import br.com.api.cadastrousuario.repository.UsuarioRepository;

import java.time.LocalDate;
import java.util.Optional;

public final class ValidaUsuarioUtil {

    private ValidaUsuarioUtil() {
        throw new UnsupportedOperationException("Classe utilitária não pode ser instanciada.");
    }

    // Validação de Nome
    public static void validarNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome não pode ser vazio.");
        }
        if (nome.length() < 3 || nome.length() > 100) {
            throw new IllegalArgumentException("O nome deve ter entre 3 e 100 caracteres.");
        }
    }

    // Validação de Data de Nascimento
    public static void validarDataNascimento(LocalDate dataNascimento) {
        if (dataNascimento == null) {
            throw new DataNascimentoInvalidaException("A data de nascimento não pode ser nula.");
        }
        if (dataNascimento.isAfter(LocalDate.now())) {
            throw new DataNascimentoInvalidaException("A data de nascimento não pode ser no futuro.");
        }
    }

    // Validação de CPF
    public static void validarCpf(String cpf, Long idExistente, UsuarioRepository usuarioRepository) {
        String cpfLimpo = cpf.replaceAll("[^0-9]", "");

        // Verifica se o CPF informado é válido matematicamente
        if (!CPFUtil.isValidCPF(cpfLimpo)) {
            throw new CpfInvalidoException("CPF inválido!");
        }

        // Verifica se o CPF já existe no banco de dados
        if (usuarioRepository.existsByCpf(cpfLimpo)) {

            // Busca o usuário associado a esse CPF
            Optional<Usuario> usuarioExistente = usuarioRepository.findByCpf(cpfLimpo);

            // Se o CPF já estiver cadastrado por outro usuário (diferente do que está sendo atualizado), lança exceção
            if (usuarioExistente.isPresent() && !usuarioExistente.get().getId().equals(idExistente)) {
                throw new UsuarioJaCadastradoException("Já existe um usuário com esse CPF.");
            }
        }


    }
}
