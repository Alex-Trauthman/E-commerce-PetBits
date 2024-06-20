package br.unitins.topicos1.service;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.microprofile.jwt.JsonWebToken;

import br.unitins.topicos1.dto.AdminDTO;
import br.unitins.topicos1.dto.AdminResponseDTO;
import br.unitins.topicos1.dto.TelefoneDTO;
import br.unitins.topicos1.dto.UsuarioResponseDTO;
import br.unitins.topicos1.model.Admin;
import br.unitins.topicos1.model.Telefone;
import br.unitins.topicos1.model.Usuario;
import br.unitins.topicos1.repository.AdminRepository;
import br.unitins.topicos1.repository.UsuarioRepository;
import br.unitins.topicos1.validation.ValidationError;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
public class AdminServiceImpl implements AdminService {

    @Inject
    JsonWebToken jsonWebToken;

    @Inject
    public AdminRepository adminRepository;

    @Inject
    public UsuarioRepository usuarioRepository;

    @Inject
    public HashService hashService;

    @Override
    @Transactional
    public AdminResponseDTO create(@Valid AdminDTO dto) {

        Usuario usuario = new Usuario();
        usuario.setUsername(dto.username());
        // gerando o hash da senha
        usuario.setSenha(hashService.getHashSenha(dto.senha()));
        repeatedUsername(null);
        // salvando o usuario
        usuarioRepository.persist(usuario);
      
        Admin admin = new Admin();
        admin.setNome(dto.nome());
        admin.setEmail(dto.email());
        admin.setCargo(dto.cargo());
        admin.setSalario(dto.salario());
        admin.setListaTelefone(new ArrayList<Telefone>());
        admin.setUsuario(usuario);

        for (TelefoneDTO tel : dto.telefones()) {
            Telefone t = new Telefone();
            t.setCodigoArea(tel.codigoArea());
            t.setNumero(tel.numero());
            admin.getListaTelefone().add(t);
        }

        adminRepository.persist(admin);

        return AdminResponseDTO.valueOf(admin);
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
        adminRepository.deleteById(id);
    }

    @Override
    public AdminResponseDTO findById(Long id) {
        return AdminResponseDTO.valueOf(adminRepository.findById(id));
    }

    @Override
    public List<AdminResponseDTO> findAll() {
        return adminRepository
        .listAll()
        .stream()
        .map(e -> AdminResponseDTO.valueOf(e)).toList();
    }

    @Override
    public List<AdminResponseDTO> findByNome(String nome) {
        return adminRepository.findByNome(nome).stream()
        .map(e -> AdminResponseDTO.valueOf(e)).toList();
   }
   
    
    public UsuarioResponseDTO login(String username, String senha) {
        Admin admin = adminRepository.findByUsernameAndSenha(username, senha);
        return UsuarioResponseDTO.valueOfAdmin(admin);
    }

    public Usuario findByUsername(String username){
        Usuario usuario = usuarioRepository.findByUsername(username);
        return usuario;
    }
    
    public Usuario findLoggedUser(){
        Usuario usuario = usuarioRepository.findByUsername(jsonWebToken.getName());
        return usuario;
    }
    
    public Response updateSenha(String senhaAtual, String novaSenha){
        Usuario usuario = usuarioRepository.findByUsername(jsonWebToken.getName());
        Admin admin = adminRepository.findByUsername(jsonWebToken.getName());
        if(hashService.getHashSenha(senhaAtual).equals(usuario.getSenha())){
            usuario.setSenha(hashService.getHashSenha(novaSenha));
            usuarioRepository.persist(usuario);
            UsuarioResponseDTO user = UsuarioResponseDTO.valueOf(usuario);
            admin.getUsuario().setSenha(hashService.getHashSenha(novaSenha));
            return Response.ok(user).build();
        }
        
        
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }

    public Response updateUsername(String username){
        repeatedUsername(username);
        Usuario usuario = findLoggedUser();
        usuario.setUsername(username);
        UsuarioResponseDTO user = UsuarioResponseDTO.valueOf(usuario);
        Admin admin = adminRepository.findByUsername(jsonWebToken.getName());
        admin.getUsuario().setUsername(username);
        return Response.ok(user).build();
    }

    @Override
    public Response updateEmail(String email){
        Admin admin = adminRepository.findByUsername(findLoggedUser().getUsername());
        admin.setEmail(email);
        AdminResponseDTO adm = AdminResponseDTO.valueOf(admin);
        return Response.ok(adm).build();
    }

    @Override
    public Response updateListaTelefone(List<TelefoneDTO> telefones){
        Admin admin = adminRepository.findByUsername(findLoggedUser().getUsername());
        admin.getListaTelefone().clear();
        for (TelefoneDTO tel : telefones) {
            Telefone t = new Telefone();
            t.setCodigoArea(tel.codigoArea());
            t.setNumero(tel.numero());
            admin.getListaTelefone().add(t);
        }
        AdminResponseDTO adm = AdminResponseDTO.valueOf(admin);
        return Response.ok(adm).build();

    }
    

}