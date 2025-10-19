package br.com.fco_romario.loja_ceda_artes.unittests.mapper;

import br.com.fco_romario.loja_ceda_artes.domain.Cliente;
import br.com.fco_romario.loja_ceda_artes.dtos.ClienteDTO;
import br.com.fco_romario.loja_ceda_artes.enums.TipoCliente;
import br.com.fco_romario.loja_ceda_artes.mapper.ClienteMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import br.com.fco_romario.loja_ceda_artes.unittests.mocks.MockCliente;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
public class ClienteMapperTest {

    MockCliente inputObject;

    @BeforeEach
    public void setUp() {
        inputObject = new MockCliente();
    }

    @Test
    public void parseEntityToDTOTest() {
        ClienteDTO output  = ClienteMapper.toDTO(inputObject.mockEntity());
        assertEquals(0, output.getId());
        assertEquals("Fulano Alves de Lima 0", output.getNome());
        assertEquals(LocalDate.of(1995, 7, 12), output.getDataNascimento());
        assertEquals("test0@gmail.com", output.getEmail());
        assertEquals("11111111110", output.getCpfOuCnpj());
        assertEquals("85800000000", output.getTelefones().stream().toList().get(0));
        assertEquals("85900000000", output.getTelefones().stream().toList().get(1));
        assertEquals(TipoCliente.PESSOA_FISICA, output.getTipo());
    }

    @Test
    public void parseEntityListToDTOListTest() {
        List<ClienteDTO> outputList = inputObject.mockEntityList()
                .stream()
                .map(ClienteMapper::toDTO)
                .toList();

        ClienteDTO outputOne = outputList.get(0);

        assertEquals(0, outputOne.getId());
        assertEquals("Fulano Alves de Lima 0", outputOne.getNome());
        assertEquals(LocalDate.of(1995, 7, 12), outputOne.getDataNascimento());
        assertEquals("test0@gmail.com", outputOne.getEmail());
        assertEquals("11111111110", outputOne.getCpfOuCnpj());
        assertEquals("85800000000", outputOne.getTelefones().stream().toList().get(0));
        assertEquals("85900000000", outputOne.getTelefones().stream().toList().get(1));
        assertEquals(TipoCliente.PESSOA_FISICA, outputOne.getTipo());

        ClienteDTO outputFour = outputList.get(4);

        assertEquals(4, outputFour.getId());
        assertEquals("Fulano Alves de Lima 4", outputFour.getNome());
        assertEquals(LocalDate.of(1999, 7, 12), outputFour.getDataNascimento());
        assertEquals("test4@gmail.com", outputFour.getEmail());
        assertEquals("11111111114", outputFour.getCpfOuCnpj());
        assertEquals("85800000004", outputFour.getTelefones().stream().toList().get(0));
        assertEquals("85900000004", outputFour.getTelefones().stream().toList().get(1));
        assertEquals(TipoCliente.PESSOA_FISICA, outputFour.getTipo());

        ClienteDTO outputSeven = outputList.get(7);

        assertEquals(7, outputSeven.getId());
        assertEquals("Fulano Alves de Lima 7", outputSeven.getNome());
        assertEquals(LocalDate.of(2002, 7, 12), outputSeven.getDataNascimento());
        assertEquals("test7@gmail.com", outputSeven.getEmail());
        assertEquals("22222222222227", outputSeven.getCpfOuCnpj());
        assertEquals("85800000007", outputSeven.getTelefones().stream().toList().get(0));
        assertEquals("85900000007", outputSeven.getTelefones().stream().toList().get(1));
        assertEquals(TipoCliente.PESSOA_JURIDICA, outputSeven.getTipo());
    }

    @Test
    public void parseDTOToEntityTest() {
        Cliente output  = ClienteMapper.toEntity(inputObject.mockDTO());
        assertEquals(0, output.getId());
        assertEquals("Fulano Alves de Lima 0", output.getNome());
        assertEquals(LocalDate.of(1995, 7, 12), output.getDataNascimento());
        assertEquals("test0@gmail.com", output.getEmail());
        assertEquals("11111111110", output.getCpfOuCnpj());
        assertEquals("85800000000", output.getTelefones().stream().toList().get(0));
        assertEquals("85900000000", output.getTelefones().stream().toList().get(1));
        assertEquals(TipoCliente.PESSOA_FISICA, output.getTipo());
    }

    @Test
    public void parserDTOListToEntityListTest() {
        List<Cliente> outputList = inputObject.mockDTOList()
                .stream()
                .map(ClienteMapper::toEntity)
                .toList();

        Cliente outputOne = outputList.get(0);

        assertEquals(0, outputOne.getId());
        assertEquals("Fulano Alves de Lima 0", outputOne.getNome());
        assertEquals(LocalDate.of(1995, 7, 12), outputOne.getDataNascimento());
        assertEquals("test0@gmail.com", outputOne.getEmail());
        assertEquals("11111111110", outputOne.getCpfOuCnpj());
        assertEquals("85800000000", outputOne.getTelefones().stream().toList().get(0));
        assertEquals("85900000000", outputOne.getTelefones().stream().toList().get(1));
        assertEquals(TipoCliente.PESSOA_FISICA, outputOne.getTipo());

        Cliente outputFour = outputList.get(4);

        assertEquals(4, outputFour.getId());
        assertEquals("Fulano Alves de Lima 4", outputFour.getNome());
        assertEquals(LocalDate.of(1999, 7, 12), outputFour.getDataNascimento());
        assertEquals("test4@gmail.com", outputFour.getEmail());
        assertEquals("11111111114", outputFour.getCpfOuCnpj());
        assertEquals("85800000004", outputFour.getTelefones().stream().toList().get(0));
        assertEquals("85900000004", outputFour.getTelefones().stream().toList().get(1));
        assertEquals(TipoCliente.PESSOA_FISICA, outputFour.getTipo());

        Cliente outputSeven = outputList.get(7);

        assertEquals(7, outputSeven.getId());
        assertEquals("Fulano Alves de Lima 7", outputSeven.getNome());
        assertEquals(LocalDate.of(2002, 7, 12), outputSeven.getDataNascimento());
        assertEquals("test7@gmail.com", outputSeven.getEmail());
        assertEquals("22222222222227", outputSeven.getCpfOuCnpj());
        assertEquals("85800000007", outputSeven.getTelefones().stream().toList().get(0));
        assertEquals("85900000007", outputSeven.getTelefones().stream().toList().get(1));
        assertEquals(TipoCliente.PESSOA_JURIDICA, outputSeven.getTipo());
    }
}
