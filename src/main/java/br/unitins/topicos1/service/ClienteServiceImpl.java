package br.unitins.topicos1.service;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.microprofile.jwt.JsonWebToken;

import br.unitins.topicos1.dto.ClienteDTO;
import br.unitins.topicos1.dto.ClienteResponseDTO;
import br.unitins.topicos1.dto.TelefoneDTO;
import br.unitins.topicos1.dto.UsuarioResponseDTO;
import br.unitins.topicos1.model.Cliente;
import br.unitins.topicos1.model.Endereco;
import br.unitins.topicos1.model.Telefone;
import br.unitins.topicos1.model.Usuario;
import br.unitins.topicos1.repository.ClienteRepository;
import br.unitins.topicos1.repository.UsuarioRepository;
import br.unitins.topicos1.validation.ValidationError;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
public class ClienteServiceImpl implements ClienteService {

    @Inject
    JsonWebToken jsonWebToken;

    @Inject
    public ClienteRepository clienteRepository;

    @Inject
    public UsuarioRepository usuarioRepository;

    @Inject
    public HashService hashService;


    @Override
    @Transactional
    public ClienteResponseDTO create(@Valid ClienteDTO dto) {

        Usuario usuario = new Usuario();
        usuario.setUsername(dto.username());
        repeatedUsername(dto.username());
        usuario.setSenha(hashService.getHashSenha(dto.senha()));

        // salvando o usuario
        usuarioRepository.persist(usuario);
      
        Cliente cliente = new Cliente();
        cliente.setNome(dto.nome());
        cliente.setEmail(dto.email());
        cliente.setCpf(dto.email());
        cliente.setListaTelefone(new ArrayList<Telefone>());
        cliente.setUsuario(usuario);

        for (TelefoneDTO tel : dto.telefones()) {
            Telefone t = new Telefone();
            t.setCodigoArea(tel.codigoArea());
            t.setNumero(tel.numero());
            cliente.getListaTelefone().add(t);
        }
        

        Endereco endereco = new Endereco();
        endereco.setRua(dto.endereco().getRua());
        endereco.setNumero(dto.endereco().getNumero());
        endereco.setCidade(dto.endereco().getCidade());
        endereco.setEstado(dto.endereco().getEstado());
        endereco.setCep(dto.endereco().getCep());
        cliente.setEndereco(endereco);
        clienteRepository.persist(cliente);

        return ClienteResponseDTO.valueOf(cliente);
    }

    public ValidationError repeatedUsername(String username){
        if(usuarioRepository.findByUsername(username) != null){
            ValidationError error = new ValidationError("409", "Username já cadastrado");
            error.addFieldError("username", "Username já cadastrado");
            return error;
        }
        return null;
    }
    

    @Override
    @Transactional
    public void delete(Long id) {
        clienteRepository.deleteById(id);
    }

    @Override
    public ClienteResponseDTO findById(Long id) {
        return ClienteResponseDTO.valueOf(clienteRepository.findById(id));
    }

    @Override
    public List<ClienteResponseDTO> findAll() {
        return clienteRepository
        .listAll()
        .stream()
        .map(e -> ClienteResponseDTO.valueOf(e)).toList();
    }

    @Override
    public List<ClienteResponseDTO> findByNome(String nome) {
        return clienteRepository.findByNome(nome).stream()
        .map(e -> ClienteResponseDTO.valueOf(e)).toList();
   }

   @Override
   public Usuario findByUsername(String username){
        Usuario usuario = usuarioRepository.findByUsername(username);
        return usuario; 
   }

    public UsuarioResponseDTO login(String username, String senha) {
        Cliente cliente = clienteRepository.findByUsernameAndSenha(username, senha);
        return UsuarioResponseDTO.valueOfCliente(cliente);
    }

    public Usuario findLoggedUser(){
        return usuarioRepository.findByUsername(jsonWebToken.getName());
    }

    public Response updateSenha(String senhaAtual, String novaSenha){
        Usuario usuario = usuarioRepository.findByUsername(jsonWebToken.getName());
        if(hashService.getHashSenha(senhaAtual).equals(usuario.getSenha())){
            usuario.setSenha(hashService.getHashSenha(novaSenha));
            usuarioRepository.persist(usuario);
            UsuarioResponseDTO user = UsuarioResponseDTO.valueOf(usuario);
            return Response.ok(user).build();
        }
        Cliente cliente = clienteRepository.findByUsername(findLoggedUser().getUsername());
        cliente.setUsuario(usuario);
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }
    public Response updateUsername(String username){
        repeatedUsername(username);
        Usuario usuario = findLoggedUser();
        usuario.setUsername(username);
        UsuarioResponseDTO user = UsuarioResponseDTO.valueOf(usuario);
        Cliente cliente = clienteRepository.findByUsername(findLoggedUser().getUsername());
        cliente.setUsuario(usuario);
        return Response.ok(user).build();
    }
    public Response updateEmail(String email){
        Cliente cliente = clienteRepository.findByUsername(findLoggedUser().getUsername());
        cliente.setEmail(email);
        ClienteResponseDTO user = ClienteResponseDTO.valueOf(cliente);
        return Response.ok(user).build();
    }

    public Response updateEndereco(String rua, String numero, String cidade, String estado, String cep){
        Cliente cliente = clienteRepository.findByUsername(findLoggedUser().getUsername());
        Endereco endereco = new Endereco();
        endereco.setRua(rua);   
        endereco.setNumero(numero); 
        endereco.setCidade(cidade);
        endereco.setEstado(estado);
        endereco.setCep(cep);
        cliente.setEndereco(endereco);
        ClienteResponseDTO user = ClienteResponseDTO.valueOf(cliente);
        return Response.ok(user).build();
    }
    public Response updateListaTelefone(List<TelefoneDTO> telefones){
        Cliente cliente = clienteRepository.findByUsername(findLoggedUser().getUsername());
        cliente.setListaTelefone(new ArrayList<Telefone>());
        for (TelefoneDTO tel : telefones) {
            Telefone t = new Telefone();
            t.setCodigoArea(tel.codigoArea());
            t.setNumero(tel.numero());
            cliente.getListaTelefone().add(t);
        }
        ClienteResponseDTO user = ClienteResponseDTO.valueOf(cliente);
        return Response.ok(user).build();
    }

}