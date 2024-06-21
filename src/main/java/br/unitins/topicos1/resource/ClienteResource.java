package br.unitins.topicos1.resource;

import java.util.List;

import org.jboss.logging.Logger;

import br.unitins.topicos1.dto.ClienteDTO;
import br.unitins.topicos1.dto.TelefoneDTO;
import br.unitins.topicos1.dto.TrocaSenhaDTO;
import br.unitins.topicos1.service.ClienteService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/clientes")
public class ClienteResource {
    
    @Inject
    public ClienteService clienteService;

    private static final Logger LOGGER = Logger.getLogger(ClienteResource.class);
    @GET
    @RolesAllowed("Admin")
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        LOGGER.info("Finding cliente by id: " + id);
        return Response.ok(clienteService.findById(id)).build();
    }
    
    @RolesAllowed("Admin")
    @GET
    public Response findAll() {
        LOGGER.info("Finding all clientes");    
        return Response.ok(clienteService.findAll()).build();
    }
    
    @GET
    @Path("/search/nome/{nome}")
    public Response findByNome(@PathParam("nome") String nome) {
        LOGGER.info("Finding cliente by name: " + nome);
         return Response.ok(clienteService.findByNome(nome)).build();
    }
    
    @POST
    public Response create(ClienteDTO dto) {
        LOGGER.info("Creating cliente");
        return Response.status(Status.CREATED).entity(clienteService.create(dto)).build();
    }

    
    @RolesAllowed("Admin")
    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        LOGGER.info("Deleting cliente by id: " + id);
        clienteService.delete(id);
        return Response.status(Status.NO_CONTENT).build();
    }
    
    @PATCH
    @RolesAllowed("Cliente")
    @Path("/updateUsername/{username}")
    public Response updateUsername(@PathParam("username") String username) {
        LOGGER.info("Updating username: " + username);
        return clienteService.updateUsername(username);
    }
    
    @PATCH
    @RolesAllowed("Cliente")
    @Path("/updateSenha")
    public Response updateSenha(TrocaSenhaDTO trocaSenhaDTO) {
        LOGGER.info("Updating senha");
        return clienteService.updateSenha(trocaSenhaDTO);
    }
    
    @PATCH
    @RolesAllowed("Cliente")
    @Path("/updateEmail/{email}")
    public Response updateEmail(@PathParam("email") String email) {
        LOGGER.info("Updating email: " + email);
        return clienteService.updateEmail(email);
    }
    
    @PATCH
    @RolesAllowed("Cliente")
    @Path("/updateListaTelefone")
    public Response updateListaTelefone(List<TelefoneDTO> listaTelefone) {
        LOGGER.info("Updating lista de telefone");
        return clienteService.updateListaTelefone(listaTelefone);
    }
    
    @PATCH
    @RolesAllowed("Cliente")
    @Path("/updateEndereco/{rua}/{numero}/{cidade}/{estado}/{cep}")
    public Response updateEndereco(@PathParam("rua") String rua, @PathParam("numero") String numero,  @PathParam("cidade") String cidade, @PathParam("estado") String estado, @PathParam("cep") String cep) {
        LOGGER.info("Updating endereco");
        return clienteService.updateEndereco(rua, numero, cidade, estado, cep);
    }
}
