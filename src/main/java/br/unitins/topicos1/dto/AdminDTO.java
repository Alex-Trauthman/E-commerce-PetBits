package br.unitins.topicos1.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


public record AdminDTO (
    @NotBlank(message = "O nome não pode ser nulo ou vazio")
    @Size(min = 4, max = 60, message = "O tamanho do nome deve ser entre 4 e 60 caracteres.")
    String nome,
    @NotBlank(message = "O email não pode ser nulo ou vazio")
    @Size(min = 6, max = 60, message = "O tamanho do email deve ser entre 6 e 60 caracteres.")
    String email,
    @NotBlank(message = "O username não pode ser nulo ou vazio")
    @Size(min = 4, max = 60, message = "O tamanho do username deve ser entre 4 e 60 caracteres.")
    String username,
    Double salario,
    @NotBlank(message = "O cargo não pode ser nulo ou vazio")
    String cargo,
    @NotBlank(message = "A senha não pode ser nula ou vazia")
    String senha,

    List<TelefoneDTO> telefones) 
{ }