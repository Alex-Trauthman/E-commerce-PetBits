package br.unitins.topicos1.resource;

import org.jboss.logging.Logger;

import br.unitins.topicos1.dto.CartaoCreditoDTO;
import br.unitins.topicos1.dto.PedidoDTO;
import br.unitins.topicos1.dto.PixDTO;
import br.unitins.topicos1.service.PedidoService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/pedidos")
public class PedidoResource {

    @Inject
    public PedidoService pedidoService;

    private static final Logger LOGGER = Logger.getLogger(PedidoResource.class); 
    @GET
    @Path("/{id}")
    @RolesAllowed("Admin")
    public Response findById(@PathParam("id") Long id) {
        LOGGER.info("Finding pedido by id: " + id);
        return Response.ok(pedidoService.findById(id)).build();
    }

    @GET
    @RolesAllowed("Admin")
    public Response findAll() {
        LOGGER.info("Finding all pedidos");
        return Response.ok(pedidoService.findAll()).build();
    }

    @GET
    @Path("/search/cliente/{id}")
    @RolesAllowed("Admin")
    public Response findByCliente(@PathParam("id") Long id) {
        LOGGER.info("Finding pedido by cliente: " + id);
        return Response.ok(pedidoService.findByCliente(id)).build();
    }

    @GET
    @Path("/search/my")
    @RolesAllowed("Cliente")
    public Response findMyPedidos() {
        LOGGER.info("Finding my pedidos");
        return Response.ok(pedidoService.findMyPedidos()).build();
    }
    
    @POST
    @RolesAllowed("Cliente")
    public Response create(PedidoDTO dto) {
        LOGGER.info("Creating pedido");
        return Response.status(Status.CREATED).entity(pedidoService.create(dto)).build();
    }
    @POST
    @Path("/pagar/cartao/{id}")
    @RolesAllowed("Cliente")
    public Response pagarCartao(@PathParam("id") Long id, CartaoCreditoDTO dto) {
        LOGGER.info("Paying pedido with credit card");
        pedidoService.PagarPedidoCredito(id, dto);
        return Response.noContent().build();
    }
    @POST
    @Path("/pagar/pix")
    @RolesAllowed("Cliente")
    public Response pagarPix(PixDTO dto) {
        LOGGER.info("Paying pedido with pix");
        pedidoService.PagarPedidoPix(dto);
        return Response.status(Status.CREATED).build();
    }


}
