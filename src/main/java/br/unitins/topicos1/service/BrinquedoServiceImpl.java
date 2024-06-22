package br.unitins.topicos1.service;

import java.util.List;

import br.unitins.topicos1.dto.BrinquedoDTO;
import br.unitins.topicos1.dto.BrinquedoResponseDTO;
import br.unitins.topicos1.model.Brinquedo;
import br.unitins.topicos1.model.TipoBrinquedo;
import br.unitins.topicos1.repository.BrinquedoRepository;
import br.unitins.topicos1.repository.MarcaRepository;
import br.unitins.topicos1.util.Error;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
public class BrinquedoServiceImpl implements BrinquedoService {

    @Inject
    public BrinquedoRepository brinquedoRepository;

    @Inject
    public MarcaRepository marcaRepository;

    @Override
    @Transactional
    public BrinquedoResponseDTO create(@Valid BrinquedoDTO dto) {
        validarMarca(dto.idMarca());
        validarTipoBrinquedo(dto.idTipoBrinquedo());

        Brinquedo brinquedo = new Brinquedo();
        brinquedo.setNome(dto.nome());
        brinquedo.setPreco(dto.preco());
        brinquedo.setDescricao(dto.descricao());
        brinquedo.setMarca(marcaRepository.findById(dto.idMarca()));
        brinquedo.setAnimal(dto.animal());
        brinquedo.setMaterial(dto.material());
        brinquedo.setEstoque(dto.estoque());
        brinquedo.setTipoBrinquedo(TipoBrinquedo.valueOf(dto.idTipoBrinquedo()));


        brinquedoRepository.persist(brinquedo);
        return BrinquedoResponseDTO.valueOf(brinquedo);
    }



    @Override
    @Transactional
    public void update(Long id,@Valid BrinquedoDTO dto) {
        validarId(id);
        validarMarca(dto.idMarca());
        validarTipoBrinquedo(dto.idTipoBrinquedo());
        Brinquedo brinquedoBanco = brinquedoRepository.findById(id);

        brinquedoBanco.setNome(dto.nome());
        brinquedoBanco.setPreco(dto.preco());
        brinquedoBanco.setDescricao(dto.descricao());
        brinquedoBanco.setMarca(marcaRepository.findById(dto.idMarca()));
        brinquedoBanco.setAnimal(dto.animal());
        brinquedoBanco.setMaterial(dto.material());
        brinquedoBanco.setEstoque(dto.estoque());
        brinquedoBanco.setTipoBrinquedo(TipoBrinquedo.valueOf(dto.idTipoBrinquedo()));
    }
    @SuppressWarnings("resource")
    public void validarMarca(long idMarca) {
        if (marcaRepository.findById(idMarca) == null) {
            throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND)
                    .entity(new Error("404", "Marca não encontrada para o ID fornecido: " + idMarca))
                    .build());
        }
    }
    @SuppressWarnings("resource")
    public void validarId(long id){
        if (brinquedoRepository.findById(id)== null) {
            throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND)
                    .entity(new Error("404", "Brinquedo não encontrada para o ID fornecido: " + id))
                    .build());
        }
    }
    @SuppressWarnings("resource")
    public void validarTipoBrinquedo(Integer idTipoBrinquedo){
        if (TipoBrinquedo.valueOf(idTipoBrinquedo)==null) {
            throw new WebApplicationException(Response.status(Response.Status.BAD_REQUEST)
                    .entity(new Error("400", "Tipo de brinquedo inválido: " + idTipoBrinquedo))
                    .build());
        }
    }

    @Override
    @Transactional
    public void delete(Long id) {
        validarId(id);
        brinquedoRepository.deleteById(id);
    }

    @Override
    public BrinquedoResponseDTO findById(Long id) {
        Brinquedo brinquedo = brinquedoRepository.findById(id);
         if (brinquedo == null) {
            return null; 
         }
        return BrinquedoResponseDTO.valueOf(brinquedoRepository.findById(id));
    }

    @Override
    public List<BrinquedoResponseDTO> findAll() {
        return brinquedoRepository
                .listAll()
                .stream()
                .map(e -> BrinquedoResponseDTO.valueOf(e)).toList();
    }

    @Override
    public List<BrinquedoResponseDTO> findByNome(String nome) {
        return brinquedoRepository.findByNome(nome).stream()
                .map(e -> BrinquedoResponseDTO.valueOf(e)).toList();
    }


    @Override
    public List<BrinquedoResponseDTO> findByAnimal(String animal) {
        return brinquedoRepository.findByAnimal(animal).stream()
                .map(e -> BrinquedoResponseDTO.valueOf(e)).toList();
    }


    @Override
    public List<BrinquedoResponseDTO> findByDescricao(String descricao) {
        return brinquedoRepository.findByDescricao(descricao).stream()
                .map(e -> BrinquedoResponseDTO.valueOf(e)).toList();
    }

    public List<BrinquedoResponseDTO> findByIdMarca(Long idMarca) {
        validarMarca(idMarca);
        return brinquedoRepository.findByIdMarca(idMarca).stream()
                .map(e -> BrinquedoResponseDTO.valueOf(e)).toList();
    }
    public List<BrinquedoResponseDTO> findByIdTipoBrinquedo(Integer idTipo){
        validarTipoBrinquedo(idTipo);
        return brinquedoRepository.findByTipoBrinquedo(TipoBrinquedo.valueOf(idTipo)).stream()
                .map(e -> BrinquedoResponseDTO.valueOf(e)).toList();
    }

}
