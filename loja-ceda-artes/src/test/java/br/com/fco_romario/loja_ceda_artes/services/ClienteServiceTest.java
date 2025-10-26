package br.com.fco_romario.loja_ceda_artes.services;

import br.com.fco_romario.loja_ceda_artes.domain.Cliente;
import br.com.fco_romario.loja_ceda_artes.domain.Endereco;
import br.com.fco_romario.loja_ceda_artes.dtos.ClienteDTO;
import br.com.fco_romario.loja_ceda_artes.dtos.EnderecoDTO;
import br.com.fco_romario.loja_ceda_artes.enums.TipoCliente;
import br.com.fco_romario.loja_ceda_artes.exception.IllegalArgumentException;
import br.com.fco_romario.loja_ceda_artes.repositories.ClienteRepository;
import br.com.fco_romario.loja_ceda_artes.unittests.mocks.MockCliente;
import br.com.fco_romario.loja_ceda_artes.unittests.mocks.MockEndereco;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Links;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

    MockCliente inputCliente;
    MockEndereco inputEndereco;

    @Mock
    ModelMapper modelMapper;

    @InjectMocks
    ClienteService service;

    @Mock
    ClienteRepository repository;

    @BeforeEach
    void setUp() {
        inputCliente = new MockCliente();
        inputEndereco = new MockEndereco();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void buscarPorId() {
        Cliente cliente = inputCliente.mockEntity(1);

        when(repository.findById(1)).thenReturn(Optional.of(cliente));

        var result = service.buscarPorId(1);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getLinks());
        assertLinkExists(result.getLinks(), "self", "api/v1/clientes/1", "GET");
        assertLinkExists(result.getLinks(), "buscarTodos", "api/v1/clientes", "GET");
        assertLinkExists(result.getLinks(), "criar", "api/v1/clientes", "POST");
        assertLinkExists(result.getLinks(), "atualizar", "api/v1/clientes", "PUT");
        assertLinkExists(result.getLinks(), "deletar", "api/v1/clientes/1", "DELETE");

        assertEquals(1, result.getId());
        assertEquals("Fulano Alves de Lima 1", result.getNome());
        assertEquals(LocalDate.of(1996, 7, 12), result.getDataNascimento());
        assertEquals("test1@gmail.com", result.getEmail());
        assertEquals("22222222222221", result.getCpfOuCnpj());
        assertEquals("85900000001", result.getTelefones().stream().toList().get(0));
        assertEquals("85800000001", result.getTelefones().stream().toList().get(1));
        assertEquals(TipoCliente.PESSOA_JURIDICA, result.getTipo());
    }

    @Test
    @Disabled("MOTIVO: Aguardando refatoração para Paginação")
    void buscarTodos() {

    }

    @Test
    void criar() {
        Cliente entity = inputCliente.mockEntity(1);
        Endereco entityEnderecoUm = inputEndereco.mockEntity(1);
        Endereco entityEnderecoDois = inputEndereco.mockEntity(2);

        entity.getEnderecos().addAll(Arrays.asList(entityEnderecoUm, entityEnderecoDois));

        when(repository.save(any(Cliente.class))).thenReturn(entity);

        ClienteDTO dto = inputCliente.mockDTO(1);
        EnderecoDTO dtoEnderecoUm = inputEndereco.mockDTO(1);
        EnderecoDTO dtoEnderecoDois = inputEndereco.mockDTO(2);
        dto.setId(null);
        dtoEnderecoUm.setId(null);
        dtoEnderecoDois.setId(null);
        dto.getEnderecos().addAll(Arrays.asList(dtoEnderecoUm,dtoEnderecoDois));

        var result = service.criar(dto);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getLinks());
        assertLinkExists(result.getLinks(), "self", "api/v1/clientes/1", "GET");
        assertLinkExists(result.getLinks(), "buscarTodos", "api/v1/clientes", "GET");
        assertLinkExists(result.getLinks(), "criar", "api/v1/clientes", "POST");
        assertLinkExists(result.getLinks(), "atualizar", "api/v1/clientes", "PUT");
        assertLinkExists(result.getLinks(), "deletar", "api/v1/clientes/1", "DELETE");

        assertEquals(1, result.getId());
        assertEquals("Fulano Alves de Lima 1", result.getNome());
        assertEquals(LocalDate.of(1996, 7, 12), result.getDataNascimento());
        assertEquals("test1@gmail.com", result.getEmail());
        assertEquals("22222222222221", result.getCpfOuCnpj());
        assertEquals("85900000001", result.getTelefones().stream().toList().get(0));
        assertEquals("85800000001", result.getTelefones().stream().toList().get(1));
        assertEquals(TipoCliente.PESSOA_JURIDICA, result.getTipo());


        assertNotNull(result.getEnderecos());
        assertEquals(2, result.getEnderecos().size());
        assertNotNull(result.getEnderecos().getFirst().getId());

        //Endereço Um
        assertEquals(1,result.getEnderecos().getFirst().getId());
        assertEquals("Rua Oscar França1", result.getEnderecos().getFirst().getLogradouro());
        assertEquals("15181", result.getEnderecos().getFirst().getNumero());
        assertEquals("Próximo à Padaria1", result.getEnderecos().getFirst().getComplemento());
        assertEquals("Bom jardim1", result.getEnderecos().getFirst().getBairro());
        assertEquals("12345671", result.getEnderecos().getFirst().getCep());
        //Endereci Dois
        assertEquals(2,result.getEnderecos().getLast().getId());
        assertEquals("Rua Oscar França2", result.getEnderecos().getLast().getLogradouro());
        assertEquals("15182", result.getEnderecos().getLast().getNumero());
        assertEquals("Próximo à Padaria2", result.getEnderecos().getLast().getComplemento());
        assertEquals("Bom jardim2", result.getEnderecos().getLast().getBairro());
        assertEquals("12345672", result.getEnderecos().getLast().getCep());
    }

    @Test
    void testCriandoPessoaComId() {
        ClienteDTO clienteDTO = inputCliente.mockDTO(1);

        Exception exception =  assertThrows(IllegalArgumentException.class, () -> {
            service.criar(clienteDTO);
        });

        String mensagemEsperada = "O ID do Cliente deve ser nulo na criação.";
        String mensagemRecebida = exception.getMessage();
        assertEquals(mensagemEsperada,mensagemRecebida);
    }

    @Test
    void testCriandoPessoaSemEndereco() {
        ClienteDTO clienteDTO = inputCliente.mockDTO(1);
        clienteDTO.setId(null);

        Exception exception =  assertThrows(IllegalArgumentException.class, () -> {
            service.criar(clienteDTO);
        });

        String mensagemEsperada = "Ao criar um Cliente é necessário ao menos um endereço.";
        String mensagemRecebida = exception.getMessage();
        assertEquals(mensagemEsperada,mensagemRecebida);
    }

    @Test
    void testCriandoPessoaComIdEndereco() {
        ClienteDTO clienteDTO = inputCliente.mockDTO(1);
        clienteDTO.setId(null);
        EnderecoDTO enderecoDTO = inputEndereco.mockDTO(1);
        clienteDTO.getEnderecos().add(enderecoDTO);

        Exception exception =  assertThrows(IllegalArgumentException.class, () -> {
            service.criar(clienteDTO);
        });

        String mensagemEsperada = "Os endereços não devem conter ID na criação do Cliente.";
        String mensagemRecebida = exception.getMessage();
        assertEquals(mensagemEsperada,mensagemRecebida);
    }

    @Test
    void atualizar() {
        Cliente entity = inputCliente.mockEntity(1);
        Endereco entityEnderecoUm = inputEndereco.mockEntity(1);
        Endereco entityEnderecoDois = inputEndereco.mockEntity(2);
        entity.getEnderecos().addAll(Arrays.asList(entityEnderecoUm,entityEnderecoDois));

        Cliente entityEditado = inputCliente.mockEntity(1);
        Endereco entityEnderecoSalvoUm = inputEndereco.mockEntity(1);
        Endereco entityEnderecoSalvaDois = inputEndereco.mockEntity(2);
        entityEditado.getEnderecos().addAll(Arrays.asList(entityEnderecoSalvoUm, entityEnderecoSalvaDois));

        entityEditado.setNome("Fulano Alves de Lima");
        entityEditado.setDataNascimento(LocalDate.of(1994, 7, 12));
        entityEditado.setEmail("test@gmail.com");
        entityEditado.setCpfOuCnpj("11111111111");


        ClienteDTO dto = inputCliente.mockDTO(1);
        EnderecoDTO EnderecoSalvoUm = inputEndereco.mockDTO(1);
        EnderecoDTO EnderecoSalvoDois = inputEndereco.mockDTO(2);
        dto.getEnderecos().addAll(Arrays.asList(EnderecoSalvoUm, EnderecoSalvoDois));

        dto.setNome("Fulano Alves de Lima");
        dto.setDataNascimento(LocalDate.of(1994, 7, 12));
        dto.setEmail("test@gmail.com");
        dto.setCpfOuCnpj("11111111111");

        when(repository.findById(1)).thenReturn(Optional.of(entity));
        when(repository.save(any(Cliente.class))).thenReturn(entityEditado);

        var result = service.atualizar(dto);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getLinks());
        assertLinkExists(result.getLinks(), "self", "api/v1/clientes/1", "GET");
        assertLinkExists(result.getLinks(), "buscarTodos", "api/v1/clientes", "GET");
        assertLinkExists(result.getLinks(), "criar", "api/v1/clientes", "POST");
        assertLinkExists(result.getLinks(), "atualizar", "api/v1/clientes", "PUT");
        assertLinkExists(result.getLinks(), "deletar", "api/v1/clientes/1", "DELETE");

        assertEquals(1, result.getId());
        assertEquals("Fulano Alves de Lima", result.getNome());
        assertEquals(LocalDate.of(1994, 7, 12), result.getDataNascimento());
        assertEquals("test@gmail.com", result.getEmail());
        assertEquals("11111111111", result.getCpfOuCnpj());
        assertEquals("85900000001", result.getTelefones().stream().toList().get(0));
        assertEquals("85800000001", result.getTelefones().stream().toList().get(1));
        assertEquals(TipoCliente.PESSOA_JURIDICA, result.getTipo());

        assertNotNull(result.getEnderecos());
        assertEquals(2, result.getEnderecos().size());
        assertNotNull(result.getEnderecos().getFirst().getId());

        //Endereço Um
        assertEquals(1,result.getEnderecos().getFirst().getId());
        assertEquals("Rua Oscar França1", result.getEnderecos().getFirst().getLogradouro());
        assertEquals("15181", result.getEnderecos().getFirst().getNumero());
        assertEquals("Próximo à Padaria1", result.getEnderecos().getFirst().getComplemento());
        assertEquals("Bom jardim1", result.getEnderecos().getFirst().getBairro());
        assertEquals("12345671", result.getEnderecos().getFirst().getCep());
        //Endereci Dois
        assertEquals(2,result.getEnderecos().getLast().getId());
        assertEquals("Rua Oscar França2", result.getEnderecos().getLast().getLogradouro());
        assertEquals("15182", result.getEnderecos().getLast().getNumero());
        assertEquals("Próximo à Padaria2", result.getEnderecos().getLast().getComplemento());
        assertEquals("Bom jardim2", result.getEnderecos().getLast().getBairro());
        assertEquals("12345672", result.getEnderecos().getLast().getCep());
    }

    @Test
    void deletar() {
        Cliente entity = inputCliente.mockEntity(1);

        when(repository.findById(1)).thenReturn(Optional.of(entity));

        service.deletar(1);
        verify(repository, times(1)).findById(1);
        verify(repository, times(1)).delete(entity);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void buscarClientePorEnClientederecoId() {
         Cliente entityCliente = inputCliente.mockEntity(1);
         Endereco entityEnderecoUm = inputEndereco.mockEntity(1);
         Endereco entityEnderecoDois = inputEndereco.mockEntity(2);
         entityCliente.getEnderecos().addAll(Arrays.asList(entityEnderecoUm, entityEnderecoDois));

        when(repository.buscarClientePorEnderecoId(1)).thenReturn(Optional.of(entityCliente));

        var result = service.buscarClientePorEnderecoId(1);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals(1, result.getId());
        assertEquals(2, result.getEnderecos().size());

        assertEquals("Fulano Alves de Lima 1", result.getNome());
        assertEquals(LocalDate.of(1996, 7, 12), result.getDataNascimento());
        assertEquals("test1@gmail.com", result.getEmail());
        assertEquals("22222222222221", result.getCpfOuCnpj());
        assertEquals("85900000001", result.getTelefones().stream().toList().get(0));
        assertEquals("85800000001", result.getTelefones().stream().toList().get(1));
        assertEquals(TipoCliente.PESSOA_JURIDICA, result.getTipo());

        assertNotNull(result.getLinks());
        assertLinkExists(result.getLinks(), "self", "api/v1/clientes/1", "GET");
        assertLinkExists(result.getLinks(), "buscarTodos", "api/v1/clientes", "GET");
        assertLinkExists(result.getLinks(), "criar", "api/v1/clientes", "POST");
        assertLinkExists(result.getLinks(), "atualizar", "api/v1/clientes", "PUT");
        assertLinkExists(result.getLinks(), "deletar", "api/v1/clientes/1", "DELETE");

        //Endereço Um
        assertEquals(1,result.getEnderecos().getFirst().getId());
        assertEquals("Rua Oscar França1", result.getEnderecos().getFirst().getLogradouro());
        assertEquals("15181", result.getEnderecos().getFirst().getNumero());
        assertEquals("Próximo à Padaria1", result.getEnderecos().getFirst().getComplemento());
        assertEquals("Bom jardim1", result.getEnderecos().getFirst().getBairro());
        assertEquals("12345671", result.getEnderecos().getFirst().getCep());
        //Endereci Dois
        assertEquals(2,result.getEnderecos().getLast().getId());
        assertEquals("Rua Oscar França2", result.getEnderecos().getLast().getLogradouro());
        assertEquals("15182", result.getEnderecos().getLast().getNumero());
        assertEquals("Próximo à Padaria2", result.getEnderecos().getLast().getComplemento());
        assertEquals("Bom jardim2", result.getEnderecos().getLast().getBairro());
        assertEquals("12345672", result.getEnderecos().getLast().getCep());
    }

    @Test
    void inativarCliente() {
        Cliente entityAtiva = inputCliente.mockEntity(1);
        Cliente entityInativa = inputCliente.mockEntity(1);
        entityInativa.setAtivo(false);

        when(repository.findById(1))
                .thenReturn(Optional.of(entityAtiva))
                .thenReturn(Optional.of(entityInativa));

        ClienteDTO entidadeInativa = service.inativarCliente(1);

        assertFalse(entidadeInativa.getAtivo());
        verify(repository, times(2)).findById(1);
        verify(repository, times(1)).inativarCliente(1);
        verifyNoMoreInteractions(repository);
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