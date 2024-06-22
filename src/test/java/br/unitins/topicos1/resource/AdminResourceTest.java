package br.unitins.topicos1.resource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import br.unitins.topicos1.dto.AdminDTO;
import br.unitins.topicos1.repository.UsuarioRepository;
import br.unitins.topicos1.service.AdminService;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.MediaType;

@QuarkusTest
public class AdminResourceTest {
    @Inject
    public AdminService adminService;
    @Inject
    public UsuarioRepository usuarioRepository;

    @Test
    @TestSecurity(user = "test", roles = "Admin")
    public void findAllTest() {
        given().when().get("/admins")
        .then()
        .statusCode(200)
        .body("nome", hasItem(("Jonata Santos de Oliveira")));
    }
    @Test
    @TestSecurity(user = "test", roles = "Admin")
    public void findByNome(){
        given().when().get("/admins/search/nome/Jonata")
        .then()
        .statusCode(200)
        .body("nome", hasItem(containsString("Jonata")));
    }
    @Test
    @TestSecurity(user = "test", roles = "Admin")
    public void findById(){
        given().when().get("/admins/1")
        .then()
        .statusCode(200)
        .body("nome", containsString("Jonata Santos de Oliveira"));
    } 
    @Test
    @TestSecurity(user = "test", roles = "Admin")
    public void create(){
        List telefone = new ArrayList<>();
        AdminDTO admin = new AdminDTO("adminTest","adminTest@gmail","adminTest",1000.0,"cargoTest","senhaTeste",telefone);
          given()
            .contentType(MediaType.APPLICATION_JSON)
            .body(admin)
            .when()
            .post("/admins")
            .then()          
            .statusCode(201)
            .body ("nome", containsString("adminTest"))
            .body ("email", containsString("adminTest@gmail"))
            .body ("cargo", containsString("cargoTest"))
            .body ("salario", is(1000.0f));
    }
    @Test
    @TestSecurity(user = "test", roles = "Admin")
    public void delete(){
        given().when().delete("/admins/2")
        .then()
        .statusCode(204);
    }
}
