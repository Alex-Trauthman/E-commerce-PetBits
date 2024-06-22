package br.unitins.topicos1.service;

import java.util.List;

import br.unitins.topicos1.dto.ClienteDTO;
import br.unitins.topicos1.dto.ClienteResponseDTO;
import br.unitins.topicos1.dto.TelefoneDTO;
import br.unitins.topicos1.dto.TrocaSenhaDTO;
import br.unitins.topicos1.dto.UsuarioResponseDTO;
import br.unitins.topicos1.model.Usuario;
import jakarta.validation.Valid;
import jakarta.ws.rs.core.Response;

public interface ClienteService {

    public ClienteResponseDTO create(@Valid ClienteDTO dto);

    public ClienteResponseDTO cadastrar(@Valid ClienteDTO dto);

    public void delete(Long id);

    public ClienteResponseDTO findById(Long id);

    public List<ClienteResponseDTO> findAll();

    public List<ClienteResponseDTO> findByNome(String nome);

    public UsuarioResponseDTO login(String username, String senha);

    public Usuario findByUsername(String username);

    public Response updateUsername(String username);

    public Response updateSenha(@Valid TrocaSenhaDTO dto);

    public Response updateEmail(String email);

    public Response updateEndereco(String rua, String numero, String cidade, String estado, String cep);

    public Response updateListaTelefone(List<TelefoneDTO> telefones);

    public Response updateCliente(Long id, ClienteDTO clienteDTO);
}