package br.unitins.topicos1.dto;

import java.util.List;

import br.unitins.topicos1.model.Endereco;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ClienteDTO (
    @NotBlank(message = "O CPF não pode ser nulo ou vazio")
    @Size(min = 8, max = 13, message = "O tamanho do CPF deve ser 11 caracteres.")
    String cpf,
    @NotBlank(message = "O nome não pode ser nulo ou vazio")
    @Size(min = 4, max = 60, message = "O tamanho do nome deve ser entre 4 e 60 caracteres.")
    String nome,
    @NotBlank(message = "O username não pode ser nulo ou vazio")
    String username,
    @NotBlank(message = "O email não pode ser nulo ou vazio")
    @Size(min = 6, max = 60, message = "O tamanho do email deve ser entre 6 e 60 caracteres.")
    String email,
    Endereco endereco,
    @NotBlank(message = "A senha não pode ser nula ou vazia")
    String senha,
    List<TelefoneDTO> telefones) 
{ }