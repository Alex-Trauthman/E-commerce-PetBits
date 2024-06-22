package br.unitins.topicos1.resource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasItem;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import br.unitins.topicos1.dto.ClienteDTO;
import br.unitins.topicos1.dto.TelefoneDTO;
import br.unitins.topicos1.model.Endereco;
import br.unitins.topicos1.repository.ClienteRepository;
import br.unitins.topicos1.service.ClienteService;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.MediaType;

@QuarkusTest
public class ClienteResourceTest {
    @Inject
    public ClienteService clienteService;
    @Inject
    public ClienteRepository clienteRepository;

    @Test
    @TestSecurity(user = "test", roles = "Admin")
    public void findAllTest() {
        given().when().get("/clientes")
        .then()
        .statusCode(200)
        .body("nome", hasItem(containsString("cleiton")));
    }
    @Test
    @TestSecurity(user = "test", roles = "Admin")
    public void findByNome(){
        given().when().get("/clientes/search/nome/cleiton")
        .then()
        .statusCode(200)
        .body("nome", hasItem(containsString("cleiton")));
    }
    @Test
    @TestSecurity(user = "test", roles = "Admin")
    public void findById(){
        given().when().get("/clientes/1")
        .then()
        .statusCode(200)
        .body("nome", containsString("cleiton"));
    }
    @Test
    @TestSecurity(user = "test", roles = "Admin")
    public void create(){
        Endereco endereco = new Endereco();
        List<TelefoneDTO> telefone = new ArrayList<>();
        ClienteDTO cliente = new ClienteDTO("21321323", "teste", "teste1211", "teste@gmail", endereco,"senhaTeste", telefone);
            given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(cliente)
                .when()
                .post("/clientes")
                .then()          
                .statusCode(201)
                .body("nome", containsString("teste"))
                .body("email", containsString("teste@gmail"))
                .body("cpf", containsString("21321323"));
    }
    @Test
    @TestSecurity(user = "test", roles = "Admin")
    public void delete(){
        given().when().delete("/clientes/2")
        .then()
        .statusCode(204);
    }
}
