package br.unitins.topicos1.dto;

import java.util.List;

public record PedidoDTO(
    List<RacaoPedidoDTO> racao,
    List<BrinquedoPedidoDTO> brinquedo,
    List<RemedioPedidoDTO> remedio,
    List<PetiscoPedidoDTO> petisco
) {
} 
