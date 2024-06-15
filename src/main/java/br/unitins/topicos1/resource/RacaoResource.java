package br.unitins.topicos1.resource;

import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import br.unitins.topicos1.dto.RacaoDTO;
import br.unitins.topicos1.form.ImageForm;
import br.unitins.topicos1.service.RacaoFileServiceImpl;
import br.unitins.topicos1.service.RacaoService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.ResponseBuilder;
import jakarta.ws.rs.core.Response.Status;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/racoes")
public class RacaoResource {
    
    @Inject
    public RacaoService racaoService;

    @Inject
    public RacaoFileServiceImpl fileService;

    @GET

    @Path("/{id}")
    @RolesAllowed("Admin")
    public Response findById(@PathParam("id") Long id) {
        return Response.ok(racaoService.findById(id)).build();
    }

    @GET
    public Response findAll() {
        return Response.ok(racaoService.findAll()).build();
    }
    @GET
    @Path("/search/pesoProduto/{pesoProduto}")
    public Response findByPesoProduto(@PathParam ("pesoProduto")Integer idPesoProduto){
        return Response.ok(racaoService.findByPesoProduto(idPesoProduto)).build();
    }
    @GET 
    @Path("/search/sabor/{sabor}")
        public Response findBySabor(@PathParam("sabor") Long sabor){
            return Response.ok(racaoService.findBySabor(sabor)).build();
        }
    @GET
    @Path("/search/idade/{idade}")
    public Response findByIdade(@PathParam("idade") Integer idade){
        return Response.ok(racaoService.findByIdade(idade)).build();
    }

    @GET
    @Path("/search/nome/{nome}")
    public Response findByNome(@PathParam("nome") String nome) {
        return Response.ok(racaoService.findByNome(nome)).build();
    }
    @GET
    @Path("/search/animal/{animal}")
    public Response findByAnimal(@PathParam("animal") String animal) {
        return Response.ok(racaoService.findByAnimal(animal)).build();
    }
    @GET
    @Path("/search/descricao/{descricao}")
    public Response findByDescricao(@PathParam("descricao") String descricao){
        return Response.ok(racaoService.findByDescricao(descricao)).build();
    }

    @GET
    @Path("/search/marca/{marca}")
    public Response findByIdMarca(@PathParam("marca") Long idMarca) {
        return Response.ok(racaoService.findByIdMarca(idMarca)).build();
    }

    @POST
    @RolesAllowed("Admin")
    public Response create(RacaoDTO dto) {
        return Response.status(Status.CREATED).entity(racaoService.create(dto)).build();
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed("Admin")
    public Response update(@PathParam("id") Long id, RacaoDTO dto) {
        racaoService.update(id, dto);
        return Response.status(Status.NO_CONTENT).build();
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed("Admin")
    public Response delete(@PathParam("id") Long id) {
        racaoService.delete(id);
        return Response.status(Status.NO_CONTENT).build();
    }
    @PATCH
    @Path("/{id}/image/upload")
    @RolesAllowed("Admin")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response upload(@PathParam("id") Long id, @MultipartForm ImageForm form) {
        fileService.salvar(id, form.getNomeImagem(), form.getImagem());
        return Response.noContent().build();
    }

    @GET
    @Path("/image/download/{nomeImagem}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response download(@PathParam("nomeImagem") String nomeImagem) {
        ResponseBuilder response = Response.ok(fileService.download(nomeImagem));
        response.header("Content-Disposition", "attachment;filename=" + nomeImagem);
        return response.build();
    }   

}
