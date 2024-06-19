package br.unitins.topicos1.dto;

public record PetiscoPedidoDTO(
    Double desconto,
    Integer quantidade,
    Long idPetisco
) {

}