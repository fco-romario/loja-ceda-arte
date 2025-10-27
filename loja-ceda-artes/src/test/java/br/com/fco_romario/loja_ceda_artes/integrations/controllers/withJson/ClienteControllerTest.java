package br.com.fco_romario.loja_ceda_artes.integrations.controllers.withJson;

import br.com.fco_romario.loja_ceda_artes.config.TestConfigs;
import br.com.fco_romario.loja_ceda_artes.dto.wrapper.json.WrapperClienteDTO;
import br.com.fco_romario.loja_ceda_artes.dtos.ClienteDTO;
import br.com.fco_romario.loja_ceda_artes.dtos.EnderecoDTO;
import br.com.fco_romario.loja_ceda_artes.enums.TipoCliente;
import br.com.fco_romario.loja_ceda_artes.integrations.testcontainers.AbstractIntegrationTest;
import br.com.fco_romario.loja_ceda_artes.unittests.mocks.MockCliente;
import br.com.fco_romario.loja_ceda_artes.unittests.mocks.MockEndereco;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;

import io.restassured.builder.RequestSpecBuilder;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Links;
import org.springframework.hateoas.mediatype.hal.Jackson2HalModule;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//@ActiveProfiles("test")
public class ClienteControllerTest extends AbstractIntegrationTest {

    private static RequestSpecification specification;
    private static ObjectMapper objectMapper;

    private static ClienteDTO clienteDTO;
    private static EnderecoDTO enderecoDTO;

    @BeforeAll
    static void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.registerModule(new Jackson2HalModule());
        objectMapper.findAndRegisterModules();
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        clienteDTO = new ClienteDTO();
        enderecoDTO = new EnderecoDTO();

        var inputCliente = new MockCliente();
        var inputEndereco = new MockEndereco();

        clienteDTO = inputCliente.mockDTO(1);
        clienteDTO.setId(null);
        enderecoDTO = inputEndereco.mockDTO(1);
        enderecoDTO.setId(null);

        clienteDTO.getEnderecos().add(enderecoDTO);
        enderecoDTO.setCliente(clienteDTO);

        specification = new RequestSpecBuilder()
                .addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_EXEMPLO)
                //.addHeader(TestConfigs.HEADER_PARAM_AUTHORIZATION, "Bearer " + tokenDTO.getRefreshToken())
                .setBasePath("/api/v1/clientes")
                .setPort(TestConfigs.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();
    }

    @Test
    @Order(1)
    void buscarTodosTest() throws JsonProcessingException {
        var content = given(specification)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .queryParam("page", 0, "size", 3, "direction", "asc")//todo: so tinha 3 itens persistido por isso o size foi 3. ideal seria 12
                .when()
                .get()
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .extract()
                .body()
                .asString();

        WrapperClienteDTO wrapper = objectMapper.readValue(content, WrapperClienteDTO.class);
        var clientes = wrapper.getEmbedded().getClientes();

        assertEquals(3, clientes.size());

        ClienteDTO clienteTres = clientes.get(0);
        assertNotNull(clienteTres.getId());
        assertEquals("Felipe de Sousa", clienteTres.getNome());
        assertEquals(LocalDate.of(1995, 7, 12), clienteTres.getDataNascimento());
        assertEquals("test2@gmail.com", clienteTres.getEmail());
        assertEquals("73144480002", clienteTres.getCpfOuCnpj());
        assertEquals("85900000000", clienteTres.getTelefones().stream().toList().get(0));
        assertEquals("85988888888", clienteTres.getTelefones().stream().toList().get(1));
        assertEquals(TipoCliente.PESSOA_JURIDICA, clienteTres.getTipo());

        assertNotNull(clienteTres.getLinks());
        assertLinkExists(clienteTres.getLinks(), "self", "api/v1/clientes/"+clienteTres.getId().toString(), "GET");
        assertLinkExists(clienteTres.getLinks(), "buscarTodos", "api/v1/clientes?page=0&size=12&direction=asc", "GET");
        assertLinkExists(clienteTres.getLinks(), "criar", "api/v1/clientes", "POST");
        assertLinkExists(clienteTres.getLinks(), "atualizar", "api/v1/clientes", "PUT");
        assertLinkExists(clienteTres.getLinks(), "deletar", "api/v1/clientes/"+clienteTres.getId().toString(), "DELETE");

        ClienteDTO clienteUm = clientes.get(1);
        assertNotNull(clienteUm.getId());
        assertEquals("Fulano Alves de Lima 1", clienteUm.getNome());
        assertEquals(LocalDate.of(1996, 7, 12), clienteUm.getDataNascimento());
        assertEquals("test1@gmail.com", clienteUm.getEmail());
        assertEquals("22222222222221", clienteUm.getCpfOuCnpj());
        assertEquals("85900000001", clienteUm.getTelefones().stream().toList().get(0));
        assertEquals("85800000001", clienteUm.getTelefones().stream().toList().get(1));
        assertEquals(TipoCliente.PESSOA_JURIDICA, clienteUm.getTipo());

        assertNotNull(clienteUm.getLinks());
        assertLinkExists(clienteUm.getLinks(), "self", "api/v1/clientes/"+clienteUm.getId().toString(), "GET");
        assertLinkExists(clienteUm.getLinks(), "buscarTodos", "api/v1/clientes?page=0&size=12&direction=asc", "GET");
        assertLinkExists(clienteUm.getLinks(), "criar", "api/v1/clientes", "POST");
        assertLinkExists(clienteUm.getLinks(), "atualizar", "api/v1/clientes", "PUT");
        assertLinkExists(clienteUm.getLinks(), "deletar", "api/v1/clientes/"+clienteUm.getId().toString(), "DELETE");

        ClienteDTO clienteDois = clientes.get(2);
        assertNotNull(clienteDois.getId());
        assertEquals("Maria Alves", clienteDois.getNome());
        assertEquals(LocalDate.of(1995, 7, 12), clienteDois.getDataNascimento());
        assertEquals("test@gmail.com", clienteDois.getEmail());
        assertEquals("50144808021", clienteDois.getCpfOuCnpj());
        assertEquals("85900000000", clienteDois.getTelefones().stream().toList().get(0));
        assertEquals("85988888888", clienteDois.getTelefones().stream().toList().get(1));
        assertEquals(TipoCliente.PESSOA_FISICA, clienteDois.getTipo());

        assertNotNull(clienteDois.getLinks());
        assertLinkExists(clienteDois.getLinks(), "self", "api/v1/clientes/"+clienteDois.getId().toString(), "GET");
        assertLinkExists(clienteDois.getLinks(), "buscarTodos", "api/v1/clientes?page=0&size=12&direction=asc", "GET");
        assertLinkExists(clienteDois.getLinks(), "criar", "api/v1/clientes", "POST");
        assertLinkExists(clienteDois.getLinks(), "atualizar", "api/v1/clientes", "PUT");
        assertLinkExists(clienteDois.getLinks(), "deletar", "api/v1/clientes/"+clienteDois.getId().toString(), "DELETE");
    }

    @Test
    @Order(1)
    void criarTest() throws JsonProcessingException {
        ClienteDTO dto = clienteDTO;

        var content = given(specification)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(dto)
                .when()
                .post()
                .then()
                .statusCode(201)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .extract()
                .body()
                .asString();

        ClienteDTO clienteCriado = objectMapper.readValue(content, ClienteDTO.class);
        clienteDTO = clienteCriado;

        assertNotNull(clienteCriado);
        assertNotNull(clienteCriado.getId());

        assertEquals("Fulano Alves de Lima 1", clienteCriado.getNome());
        assertEquals(LocalDate.of(1996, 7, 12), clienteCriado.getDataNascimento());
        assertEquals("test1@gmail.com", clienteCriado.getEmail());
        assertEquals("22222222222221", clienteCriado.getCpfOuCnpj());
        assertEquals("85900000001", clienteCriado.getTelefones().stream().toList().get(0));
        assertEquals("85800000001", clienteCriado.getTelefones().stream().toList().get(1));
        assertEquals(TipoCliente.PESSOA_JURIDICA, clienteCriado.getTipo());

        assertNotNull(clienteCriado.getLinks());
        assertLinkExists(clienteCriado.getLinks(), "self", "api/v1/clientes/"+clienteCriado.getId().toString(), "GET");
        assertLinkExists(clienteCriado.getLinks(), "buscarTodos", "api/v1/clientes?page=0&size=12&direction=asc", "GET");
        assertLinkExists(clienteCriado.getLinks(), "criar", "api/v1/clientes", "POST");
        assertLinkExists(clienteCriado.getLinks(), "atualizar", "api/v1/clientes", "PUT");
        assertLinkExists(clienteCriado.getLinks(), "deletar", "api/v1/clientes/"+clienteCriado.getId().toString(), "DELETE");

    }

    @Test
    @Order(2)
    void atualizarTest() throws JsonProcessingException {
        clienteDTO.setNome("Fulano Alves de Lima 1 - atualizado");

        var content = given(specification)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(clienteDTO)
                .when()
                .put()
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .extract()
                .body()
                .asString();

        ClienteDTO clienteParaAtualizado = objectMapper.readValue(content, ClienteDTO.class);

        assertNotNull(clienteParaAtualizado);
        assertNotNull(clienteParaAtualizado.getId());

        assertEquals("Fulano Alves de Lima 1 - atualizado", clienteParaAtualizado.getNome());
        assertEquals(LocalDate.of(1996, 7, 12), clienteParaAtualizado.getDataNascimento());
        assertEquals("test1@gmail.com", clienteParaAtualizado.getEmail());
        assertEquals("22222222222221", clienteParaAtualizado.getCpfOuCnpj());
        assertEquals("85900000001", clienteParaAtualizado.getTelefones().stream().toList().get(0));
        assertEquals("85800000001", clienteParaAtualizado.getTelefones().stream().toList().get(1));
        assertEquals(TipoCliente.PESSOA_JURIDICA, clienteParaAtualizado.getTipo());

        assertNotNull(clienteParaAtualizado.getLinks());
        assertLinkExists(clienteParaAtualizado.getLinks(), "self", "api/v1/clientes/"+clienteParaAtualizado.getId().toString(), "GET");
        assertLinkExists(clienteParaAtualizado.getLinks(), "buscarTodos", "api/v1/clientes?page=0&size=12&direction=asc", "GET");
        assertLinkExists(clienteParaAtualizado.getLinks(), "criar", "api/v1/clientes", "POST");
        assertLinkExists(clienteParaAtualizado.getLinks(), "atualizar", "api/v1/clientes", "PUT");
        assertLinkExists(clienteParaAtualizado.getLinks(), "deletar", "api/v1/clientes/"+clienteParaAtualizado.getId().toString(), "DELETE");

    }

    @Test
    @Order(3)
    void buscarPorIdTest() throws JsonProcessingException {

        var content = given(specification)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .pathParams("id",clienteDTO.getId())
                .when()
                .get("{id}")
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .extract()
                .body()
                .asString();

        ClienteDTO clienteRetornado = objectMapper.readValue(content, ClienteDTO.class);

        assertNotNull(clienteRetornado);
        assertNotNull(clienteRetornado.getId());

        assertEquals("Fulano Alves de Lima 1 - atualizado", clienteRetornado.getNome());
        assertEquals(LocalDate.of(1996, 7, 12), clienteRetornado.getDataNascimento());
        assertEquals("test1@gmail.com", clienteRetornado.getEmail());
        assertEquals("22222222222221", clienteRetornado.getCpfOuCnpj());
        assertEquals("85900000001", clienteRetornado.getTelefones().stream().toList().get(0));
        assertEquals("85800000001", clienteRetornado.getTelefones().stream().toList().get(1));
        assertEquals(TipoCliente.PESSOA_JURIDICA, clienteRetornado.getTipo());

        assertNotNull(clienteRetornado.getLinks());
        assertLinkExists(clienteRetornado.getLinks(), "self", "api/v1/clientes/"+clienteRetornado.getId().toString(), "GET");
        assertLinkExists(clienteRetornado.getLinks(), "buscarTodos", "api/v1/clientes?page=0&size=12&direction=asc", "GET");
        assertLinkExists(clienteRetornado.getLinks(), "criar", "api/v1/clientes", "POST");
        assertLinkExists(clienteRetornado.getLinks(), "atualizar", "api/v1/clientes", "PUT");
        assertLinkExists(clienteRetornado.getLinks(), "deletar", "api/v1/clientes/"+clienteRetornado.getId().toString(), "DELETE");
    }

    @Test
    @Order(4)
    void deletarTest() throws JsonProcessingException {

        given(specification)
                .pathParam("id", clienteDTO.getId())
                .when()
                .delete("{id}")
                .then()
                .statusCode(204);
    }

    private void assertLinkExists(Links links, String rel, String hrefEndsWith, String type) {
        Link link = links.stream()
                .filter(l -> l.getRel().value().equals(rel))
                .findFirst()
                .orElse(null);

        assertNotNull(link);
        assertEquals(type, link.getType());
        assertTrue(link.getHref().endsWith(hrefEndsWith));
    }
}
