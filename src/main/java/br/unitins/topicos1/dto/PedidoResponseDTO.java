package br.unitins.topicos1.dto;

import java.time.LocalDateTime;
import java.util.List;

import br.unitins.topicos1.model.Pedido;

public record PedidoResponseDTO(
    String status,
    Long id,
    ClienteResponseDTO cliente,
    Double total,
    LocalDateTime dataExpiracao,
    List<RacaoPedidoResponseDTO> racao,
    List<BrinquedoPedidoResponseDTO> brinquedo,
    List<PetiscoPedidoResponseDTO> petisco,
    List<RemedioPedidoResponseDTO> remedio


) {
    public static PedidoResponseDTO valueOf(Pedido pedido) {
        List<RacaoPedidoResponseDTO> listaRacao = pedido.getRacao()
                                            .stream()
                                            .map(RacaoPedidoResponseDTO::valueOf)
                                            .toList();
        List<BrinquedoPedidoResponseDTO> listaBrinquedo = pedido.getBrinquedo()
                                            .stream()
                                            .map(BrinquedoPedidoResponseDTO::valueOf)
                                            .toList();                                    
        List<PetiscoPedidoResponseDTO> listaPetisco = pedido.getPetisco()
                                            .stream()
                                            .map(PetiscoPedidoResponseDTO::valueOf)
                                            .toList();                                    
        List<RemedioPedidoResponseDTO> listaRemedio = pedido.getRemedio()
                                            .stream()
                                            .map(RemedioPedidoResponseDTO::valueOf)
                                            .toList();                                    
        return new PedidoResponseDTO(
            pedido.getStatus(),
            pedido.getId(), 
            ClienteResponseDTO.valueOf(pedido.getCliente()),
            pedido.getTotal(),
            pedido.getDataExpiracao(),
            listaRacao,
            listaBrinquedo,
            listaPetisco,
            listaRemedio);
    }
    
}