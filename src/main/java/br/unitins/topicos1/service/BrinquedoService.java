package br.unitins.topicos1.service;

import java.util.List;

import br.unitins.topicos1.dto.BrinquedoDTO;
import br.unitins.topicos1.dto.BrinquedoResponseDTO;
import jakarta.validation.Valid;

public interface BrinquedoService {

    public BrinquedoResponseDTO create(@Valid BrinquedoDTO dto);
    public void update(Long id, @Valid BrinquedoDTO dto);
    public void delete(Long id);
    public BrinquedoResponseDTO findById(Long id);
    public List<BrinquedoResponseDTO> findAll();
    public List<BrinquedoResponseDTO> findByNome(String nome);
    public List<BrinquedoResponseDTO> findByAnimal(String animal);
    public List<BrinquedoResponseDTO> findByDescricao(String descricao);
    public List<BrinquedoResponseDTO> findByIdMarca(Long idMarca);
    public List<BrinquedoResponseDTO> findByIdTipoBrinquedo(Integer idTipo);
}
