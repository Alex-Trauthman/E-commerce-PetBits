package br.unitins;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import org.junit.jupiter.api.Test;

import br.unitins.topicos1.dto.PedidoDTO;
import br.unitins.topicos1.repository.PedidoRepository;
import br.unitins.topicos1.service.PedidoService;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.MediaType;

@QuarkusTest
public class PedidoResourceTest {
    
    @Inject
    public PedidoService pedidoService;
    @Inject
    public PedidoRepository pedidoRepository;

    @Test
    @TestSecurity(user = "test", roles = "Admin")
    public void findAllTest() {
        given().when().get("/pedidos")
        .then()
        .statusCode(200);
    }
    
    @Test
    @TestSecurity(user = "test", roles = "Admin")
    public void findById(){
        given().when().get("/pedidos/1")
        .then()
        .statusCode(200)
        .contentType("application/json")
        .body("id", is(1));
    }

    @Test
    @TestSecurity(user = "test", roles = "Admin")
    public void findByCliente(){
        given().when().get("/pedidos/search/cliente/1")
        .then()
        .statusCode(200)
        .contentType("application/json")
        .body("cliente.id", hasItem(1));
    }
}
