package br.com.api.cadastrousuario.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Campo Nome não pode ser vazio.")
    private String nome;

    @NotBlank(message = "Campo CPF não pode ser vazio.")
    @Column(unique = true)
    private String cpf;

    @NotNull(message = "Campo Data de Nascimento não pode ser vazio.")
    private LocalDate dataNascimento;

}
