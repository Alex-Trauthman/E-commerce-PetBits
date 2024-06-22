package br.unitins.topicos1.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TrocaSenhaDTO (
    @NotBlank(message = "A senha atual é obrigatória")
    String senhaAtual,
    @NotBlank(message = "A nova senha é obrigatória")
    @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres")
    String novaSenha,
    @NotBlank(message = "A confirmação da nova senha é obrigatória")
    @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres")
    String confirmacao
){
    
}
