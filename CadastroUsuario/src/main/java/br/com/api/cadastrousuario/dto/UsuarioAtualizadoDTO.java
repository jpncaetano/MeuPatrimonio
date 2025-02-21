package br.com.api.cadastrousuario.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UsuarioAtualizadoDTO {
    
    @NotBlank(message = "O nome não pode ser vazio.")
    private String nome;

    @NotBlank(message = "O CPF não pode ser vazio.")
    private String cpf;

    @NotNull(message = "A data de nascimento não pode ser vazia.")
    private LocalDate dataNascimento;
}
