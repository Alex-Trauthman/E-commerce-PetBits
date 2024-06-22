package br.unitins.topicos1.dto;

import jakarta.validation.constraints.NotNull;

public record RacaoPedidoDTO(
    Double desconto,
    @NotNull(message = "A quantidade não pode ser nula")
    Integer quantidade,
    @NotNull(message = "O id da ração não pode ser nulo")
    Long idRacao
) {

}