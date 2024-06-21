package br.unitins.topicos1.service;

import java.util.List;

import br.unitins.topicos1.dto.CartaoCreditoDTO;
import br.unitins.topicos1.dto.PedidoDTO;
import br.unitins.topicos1.dto.PedidoResponseDTO;
import br.unitins.topicos1.dto.PixDTO;
import jakarta.validation.Valid;

public interface PedidoService {

    public PedidoResponseDTO create(@Valid PedidoDTO dto);
    public PedidoResponseDTO findById(Long id);
    public List<PedidoResponseDTO> findAll();
    public List<PedidoResponseDTO> findMyPedidos();
    public List<PedidoResponseDTO> findByCliente(Long idCliente);
    public void PagarPedidoCredito(Long id, CartaoCreditoDTO cartao);
    public void PagarPedidoPix(PixDTO pix);
}