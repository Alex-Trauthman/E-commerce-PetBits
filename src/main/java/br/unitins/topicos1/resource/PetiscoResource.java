package br.unitins.topicos1.resource;

import br.unitins.topicos1.dto.PetiscoDTO;
import br.unitins.topicos1.service.PetiscoService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/petiscos")
public class PetiscoResource {
    
    @Inject
    public PetiscoService petiscoService;

    

    @GET
    @Path("/search/animal/{animal}")
    public Response findByAnimal(@PathParam("animal") String animal) {
        return Response.ok(petiscoService.findByAnimal(animal)).build();
    }
    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        return Response.ok(petiscoService.findById(id)).build();
    }

    @GET
    public Response findAll() {
        return Response.ok(petiscoService.findAll()).build();
    }
    @GET
    @Path("/search/pesoProduto/{pesoProduto}")
    public Response findByPesoProduto(@PathParam ("pesoProduto")Integer idPesoProduto){
        return Response.ok(petiscoService.findByPesoProduto(idPesoProduto)).build();
    }
    @GET 
    @Path("/search/sabor/{sabor}")
        public Response findBySabor(@PathParam("sabor") Long sabor){
            return Response.ok(petiscoService.findBySabor(sabor)).build();
        }

    @GET
    @Path("/search/nome/{nome}")
    public Response findByNome(@PathParam("nome") String nome) {
        return Response.ok(petiscoService.findByNome(nome)).build();
    }
    @GET
    @Path("/search/marca/{marca}")
    public Response findByIdMarca(@PathParam("marca") Long idMarca) {
        return Response.ok(petiscoService.findByIdMarca(idMarca)).build();
    }
    @POST
    public Response create(PetiscoDTO dto) {
        return Response.status(Status.CREATED).entity(petiscoService.create(dto)).build();
    }
    @GET
    @Path("/search/descricao/{descricao}")
    public Response findByDescricao(@PathParam("descricao") String descricao){
        return Response.ok(petiscoService.findByDescricao(descricao)).build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, PetiscoDTO dto) {
        petiscoService.update(id, dto);
        return Response.status(Status.NO_CONTENT).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        petiscoService.delete(id);
        return Response.status(Status.NO_CONTENT).build();
    }

}