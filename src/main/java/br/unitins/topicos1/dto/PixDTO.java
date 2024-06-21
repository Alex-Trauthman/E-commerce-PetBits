package br.unitins.topicos1.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PixDTO (
    @NotNull(message = "O id do pedido é obrigatório")
    Long idPedido,
    @NotBlank(message = "A chave PIX é obrigatória")
    String chavePix,
    @NotNull(message = "O valor é obrigatório")
    Double valor
){
    
}
