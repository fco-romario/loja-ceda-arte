package unittests.mocks;

import br.com.fco_romario.loja_ceda_artes.domain.Cliente;
import br.com.fco_romario.loja_ceda_artes.dtos.ClienteDTO;
import br.com.fco_romario.loja_ceda_artes.enums.TipoCliente;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MockCliente {

    public Cliente mockEntity() {
        return mockEntity(0);
    }

    public ClienteDTO mockDTO() {
        return mockDTO(0);
    }

    public List<Cliente> mockEntityList(){
        List<Cliente> clientes = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            clientes.add(mockEntity(i));
        }
        return clientes;
    }

    public List<ClienteDTO> mockDTOList(){
        List<ClienteDTO> clientes = new ArrayList();
        for (int i = 0; i < 10; i++) {
            clientes.add(mockDTO(i));
        }
        return clientes;
    }

    public Cliente mockEntity(Integer number) {
        Cliente cliente = new Cliente();
        cliente.setId(number);
        cliente.setNome("Fulano Alves de Lima " + number);
        cliente.setDataNascimento(LocalDate.of(1995+number, 7, 12));
        cliente.setEmail("test"+number+"@gmail.com");
        cliente.setCpfOuCnpj(number % 2 == 0 ? "1111111111"+ number : "2222222222222"+ number);
        cliente.getTelefones().addAll(Arrays.asList("8590000000"+number, "8580000000"+number));
        cliente.setTipo(TipoCliente.toEnum(number % 2 == 0 ? 1 : 2));

        return cliente;
    }

    public ClienteDTO mockDTO(Integer number) {
        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setId(number);
        clienteDTO.setNome("Fulano Alves de Lima " + number);
        clienteDTO.setDataNascimento(LocalDate.of(1995+number, 7, 12));
        clienteDTO.setEmail("test"+number+"@gmail.com");
        clienteDTO.setCpfOuCnpj(number % 2 == 0 ? "1111111111"+ number : "2222222222222"+ number);
        clienteDTO.getTelefones().addAll(Arrays.asList("8590000000"+number, "8580000000"+number));
        clienteDTO.setTipo(TipoCliente.toEnum(number % 2 == 0 ? 1 : 2));

        return clienteDTO;
    }
}