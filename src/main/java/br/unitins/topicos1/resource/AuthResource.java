package br.unitins.topicos1.resource;

import br.unitins.topicos1.dto.AuthUsuarioDTO;
import br.unitins.topicos1.dto.UsuarioResponseDTO;
import br.unitins.topicos1.service.AdminService;
import br.unitins.topicos1.service.ClienteService;
import br.unitins.topicos1.service.HashService;
import br.unitins.topicos1.service.JwtService;
import jakarta.inject.Inject;
import jakarta.validation.ValidationException;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/auth")
public class AuthResource {

    @Inject
    public AdminService adminService;

    @Inject
    public HashService hashService;

    @Inject
    public JwtService jwtService;

    @Inject
    public ClienteService clienteService;

    @POST
    public Response login(AuthUsuarioDTO dto) {
        String hash = hashService.getHashSenha(dto.senha());

        UsuarioResponseDTO usuario = null;
        // perfil 2 para admin e 1 para cliente
        if (dto.perfil() == 1) {
            usuario = clienteService.login(dto.username(), hash);
            
        } else if (dto.perfil() == 2) {
            usuario = adminService.login(dto.username(), hash);
            
        } else {
            throw new ValidationException("Perfil inválido");
        }
        return Response.ok(usuario)
            .header("Authorization", jwtService.generateJwt(usuario))
            .build();
    }

}