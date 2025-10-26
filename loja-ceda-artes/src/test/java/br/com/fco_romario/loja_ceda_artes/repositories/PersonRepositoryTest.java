package br.com.fco_romario.loja_ceda_artes.repositories;

import br.com.fco_romario.loja_ceda_artes.domain.Cliente;
import br.com.fco_romario.loja_ceda_artes.integrations.testcontainers.AbstractIntegrationTest;
import br.com.fco_romario.loja_ceda_artes.unittests.mocks.MockCliente;
import br.com.fco_romario.loja_ceda_artes.unittests.mocks.MockEndereco;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//@ActiveProfiles("test")
public class PersonRepositoryTest extends AbstractIntegrationTest {

    @Autowired
    private ClienteRepository repository;

    private Cliente clienteSalvo;

    @BeforeAll
    static void setUp() {
    }

    @Test
    @Order(1)
    void buscarClientePorEnderecoIdTest() {
        var endereco = new MockEndereco().mockEntity(1);
        var cliente = new MockCliente().mockEntity(1);
        cliente.setId(null);
        endereco.setId(null);

        endereco.setCliente(cliente);
        cliente.getEnderecos().add(endereco);

        clienteSalvo = repository.save(cliente);

        var clienteBuscado = repository.buscarClientePorEnderecoId(
                clienteSalvo.getEnderecos().get(0).getId()
        ).orElse(null);

        assertNotNull(clienteBuscado);
        assertEquals(clienteSalvo.getId(), clienteBuscado.getId());

        assertEquals("Fulano Alves de Lima 1", clienteBuscado.getNome());
        assertEquals(LocalDate.of(1996, 7, 12), clienteBuscado.getDataNascimento());
        assertEquals("test1@gmail.com", clienteBuscado.getEmail());
        assertEquals("22222222222221", clienteBuscado.getCpfOuCnpj());
        assertEquals("85900000001", clienteBuscado.getTelefones().stream().toList().get(0));
        assertEquals("85800000001", clienteBuscado.getTelefones().stream().toList().get(1));
    }
}
