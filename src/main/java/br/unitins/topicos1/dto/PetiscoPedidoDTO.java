package br.unitins.topicos1.dto;

import jakarta.validation.constraints.NotNull;

public record PetiscoPedidoDTO(
    Double desconto,
    @NotNull(message = "A quantidade não pode ser nula")
    Integer quantidade,
    @NotNull(message = "O id do petisco não pode ser nulo")
    Long idPetisco
) {

}