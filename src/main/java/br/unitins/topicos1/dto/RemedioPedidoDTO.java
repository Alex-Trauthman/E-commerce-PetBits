package br.unitins.topicos1.dto;

import jakarta.validation.constraints.NotNull;

public record RemedioPedidoDTO(
    Double desconto,
    @NotNull(message = "A quantidade não pode ser nula")
    Integer quantidade,
    @NotNull(message = "O id do remédio não pode ser nulo")
    Long idRemedio
) {

}