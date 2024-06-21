package br.unitins.topicos1.service;

import java.util.List;

import br.unitins.topicos1.dto.AdminDTO;
import br.unitins.topicos1.dto.AdminResponseDTO;
import br.unitins.topicos1.dto.TelefoneDTO;
import br.unitins.topicos1.dto.TrocaSenhaDTO;
import br.unitins.topicos1.dto.UsuarioResponseDTO;
import br.unitins.topicos1.model.Usuario;
import jakarta.validation.Valid;
import jakarta.ws.rs.core.Response;

public interface AdminService {

    public AdminResponseDTO create(@Valid AdminDTO dto);

    public void delete(Long id);

    public AdminResponseDTO findById(Long id);

    public List<AdminResponseDTO> findAll();

    public List<AdminResponseDTO> findByNome(String nome);

    public UsuarioResponseDTO login(String username, String senha);

    public Usuario findByUsername(String username);

    public Response updateUsername(String username);

    public Response updateSenha(TrocaSenhaDTO trocaSenhaDTO);
    
    public Response updateEmail(String email);
    
    public Response updateListaTelefone(List<TelefoneDTO> listaTelefone);
}