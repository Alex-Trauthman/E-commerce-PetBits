package br.unitins.topicos1.resource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import br.unitins.topicos1.dto.MarcaDTO;
import br.unitins.topicos1.dto.MarcaResponseDTO;
import br.unitins.topicos1.service.MarcaService;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.MediaType;

@QuarkusTest
public class MarcaResourceTest {

    @Inject
    public MarcaService marcaService;

    @Test
    public void findAllTest() {
        given()
            .when()
            .get("/marcas")
            .then()
            .statusCode(200)
            .body("nome", hasItem("Pedigree")); 
    }

    @Test
    @TestSecurity(user = "admin", roles = "Admin")
    public void findByIdTest() {
        given()
            .when()
            .get("/marcas/1")
            .then()
            .statusCode(200)
            .body("id", is(1));
    }

    @Test
    @TestSecurity(user = "admin", roles = "Admin")
    public void createTest() {
        MarcaDTO dto = new MarcaDTO("TestMarca", "test@test.com", "12345678901234");

        given()
            .contentType(MediaType.APPLICATION_JSON)
            .body(dto)
            .when()
            .post("/marcas")
            .then()
            .statusCode(201)
            .body("nome", is("TestMarca"))
            .body("cnpj", is("12345678901234"))
            .body("emailContato", is("test@test.com"));
    }

    @Test
    @TestSecurity(user = "admin", roles = "Admin")
    public void updateTest() {
        MarcaDTO dto = new MarcaDTO("UpdatedMarca", "98765432109876", "updated@test.com");

        given()
            .contentType(MediaType.APPLICATION_JSON)
            .body(dto)
            .when()
            .pathParam("id", 2)
            .put("/marcas/{id}")
            .then()
            .statusCode(204);
    }

    @Test
    @TestSecurity(user = "admin", roles = "Admin")
    public void deleteTest() {
        MarcaResponseDTO response = marcaService.create(new MarcaDTO("TestDelete", "99999999999999", "delete@test.com"));
        assertNotNull(response);

        given()
            .pathParam("id", response.id())
            .when()
            .delete("/marcas/{id}")
            .then()
            .statusCode(204);

        assertNull(marcaService.findById(response.id()));
    }
    @Test
    public void findByNomeTest() {
        given()
            .when()
            .get("/marcas/search/nome/Pedigree")
            .then()
            .statusCode(200)
            .body("nome", hasItem("Pedigree"));
    }
}
