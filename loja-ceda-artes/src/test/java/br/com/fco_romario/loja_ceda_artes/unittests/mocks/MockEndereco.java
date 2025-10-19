package br.com.fco_romario.loja_ceda_artes.unittests.mocks;

import br.com.fco_romario.loja_ceda_artes.domain.Endereco;
import br.com.fco_romario.loja_ceda_artes.dtos.EnderecoDTO;

import java.util.ArrayList;
import java.util.List;

public class MockEndereco {

    public Endereco mockEntity() {
        return mockEntity(0);
    }

    public EnderecoDTO mockDTO() {
        return mockDTO(0);
    }

    public List<Endereco> mockEntityList(){
        List<Endereco> enderecos = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            enderecos.add(mockEntity(i));
        }
        return enderecos;
    }

    public List<EnderecoDTO> mockDTOList(){
        List<EnderecoDTO> enderecos = new ArrayList();
        for (int i = 0; i < 10; i++) {
            enderecos.add(mockDTO(i));
        }
        return enderecos;
    }
    public Endereco mockEntity(Integer number) {
        Endereco endereco = new Endereco();
        endereco.setId(number);
        endereco.setLogradouro("Rua Oscar França"+number);
        endereco.setNumero("1518"+number);
        endereco.setComplemento("Próximo à Padaria"+number);
        endereco.setBairro("Bom jardim"+number);
        endereco.setCep("1234567"+number);

        return endereco;
    }

    public EnderecoDTO mockDTO(Integer number) {
        EnderecoDTO endereco = new EnderecoDTO();
        endereco.setId(number);
        endereco.setLogradouro("Rua Oscar França"+number);
        endereco.setNumero("1518"+number);
        endereco.setComplemento("Próximo à Padaria"+number);
        endereco.setBairro("Bom jardim"+number);
        endereco.setCep("1234567"+number);

        return endereco;
    }
}