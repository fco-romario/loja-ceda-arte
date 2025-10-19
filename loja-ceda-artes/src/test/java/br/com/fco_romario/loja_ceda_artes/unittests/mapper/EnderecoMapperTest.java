package br.com.fco_romario.loja_ceda_artes.unittests.mapper;

import br.com.fco_romario.loja_ceda_artes.domain.Endereco;
import br.com.fco_romario.loja_ceda_artes.dtos.EnderecoDTO;
import br.com.fco_romario.loja_ceda_artes.mapper.EnderecoMapper;
import br.com.fco_romario.loja_ceda_artes.unittests.mocks.MockEndereco;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EnderecoMapperTest {

    MockEndereco inputObject;

    @BeforeEach
    public void setUp() {
        inputObject = new MockEndereco();
    }

    @Test
    public void parseEntityToDTOTest() {
        EnderecoDTO output  = EnderecoMapper.toDTO(inputObject.mockEntity());
        assertEquals(0, output.getId());
        assertEquals("Rua Oscar França0", output.getLogradouro());
        assertEquals("15180", output.getNumero());
        assertEquals("Próximo à Padaria0", output.getComplemento());
        assertEquals("Bom jardim0", output.getBairro());
        assertEquals("12345670", output.getCep());
    }

    @Test
    public void parseEntityListToDTOListTest() {
        List<EnderecoDTO> outputList = inputObject.mockEntityList()
                .stream()
                .map(EnderecoMapper::toDTO)
                .toList();

        EnderecoDTO outputOne = outputList.get(0);

        assertEquals(0, outputOne.getId());
        assertEquals("Rua Oscar França0", outputOne.getLogradouro());
        assertEquals("15180", outputOne.getNumero());
        assertEquals("Próximo à Padaria0", outputOne.getComplemento());
        assertEquals("Bom jardim0", outputOne.getBairro());
        assertEquals("12345670", outputOne.getCep());

        EnderecoDTO outputFour = outputList.get(4);

        assertEquals(4, outputFour.getId());
        assertEquals("Rua Oscar França4", outputFour.getLogradouro());
        assertEquals("15184", outputFour.getNumero());
        assertEquals("Próximo à Padaria4", outputFour.getComplemento());
        assertEquals("Bom jardim4", outputFour.getBairro());
        assertEquals("12345674", outputFour.getCep());

        EnderecoDTO outputSeven = outputList.get(7);

        assertEquals(7, outputSeven.getId());
        assertEquals("Rua Oscar França7", outputSeven.getLogradouro());
        assertEquals("15187", outputSeven.getNumero());
        assertEquals("Próximo à Padaria7", outputSeven.getComplemento());
        assertEquals("Bom jardim7", outputSeven.getBairro());
        assertEquals("12345677", outputSeven.getCep());
    }

    @Test
    public void parseDTOToEntityTest() {
        Endereco output  = EnderecoMapper.toEntity(inputObject.mockDTO());
        assertEquals(0, output.getId());
        assertEquals("Rua Oscar França0", output.getLogradouro());
        assertEquals("15180", output.getNumero());
        assertEquals("Próximo à Padaria0", output.getComplemento());
        assertEquals("Bom jardim0", output.getBairro());
        assertEquals("12345670", output.getCep());
    }

    @Test
    public void parserDTOListToEntityListTest() {
        List<Endereco> outputList = inputObject.mockDTOList()
                .stream()
                .map(EnderecoMapper::toEntity)
                .toList();

        Endereco outputOne = outputList.get(0);

        assertEquals(0, outputOne.getId());
        assertEquals("Rua Oscar França0", outputOne.getLogradouro());
        assertEquals("15180", outputOne.getNumero());
        assertEquals("Próximo à Padaria0", outputOne.getComplemento());
        assertEquals("Bom jardim0", outputOne.getBairro());
        assertEquals("12345670", outputOne.getCep());

        Endereco outputFour = outputList.get(4);

        assertEquals(4, outputFour.getId());
        assertEquals("Rua Oscar França4", outputFour.getLogradouro());
        assertEquals("15184", outputFour.getNumero());
        assertEquals("Próximo à Padaria4", outputFour.getComplemento());
        assertEquals("Bom jardim4", outputFour.getBairro());
        assertEquals("12345674", outputFour.getCep());

        Endereco outputSeven = outputList.get(7);

        assertEquals(7, outputSeven.getId());
        assertEquals("Rua Oscar França7", outputSeven.getLogradouro());
        assertEquals("15187", outputSeven.getNumero());
        assertEquals("Próximo à Padaria7", outputSeven.getComplemento());
        assertEquals("Bom jardim7", outputSeven.getBairro());
        assertEquals("12345677", outputSeven.getCep());
    }
}
