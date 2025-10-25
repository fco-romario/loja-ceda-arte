package br.com.fco_romario.loja_ceda_artes.controllers;

import br.com.fco_romario.loja_ceda_artes.controllers.docs.ClienteControllerDoc;
import br.com.fco_romario.loja_ceda_artes.dtos.ClienteDTO;
import br.com.fco_romario.loja_ceda_artes.services.ClienteService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping(value = "api/v1/clientes")
@Tag(name = "Clientes", description = "Endpoint para gerenciar Clientes")
public class ClienteController implements ClienteControllerDoc {

    @Autowired
    private ClienteService clienteService;

    @GetMapping(
        produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_YAML_VALUE})
    @Override
    public ResponseEntity<List<ClienteDTO>> buscarTodos() {
        List<ClienteDTO> list = clienteService.buscarTodos();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}",
        produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_YAML_VALUE})
    @Override
    public ResponseEntity<ClienteDTO> buscarPorId(@PathVariable Integer id) {
        ClienteDTO obj = clienteService.buscarPorId(id);
        return ResponseEntity.ok().body(obj);
    }

    @PostMapping(
        consumes = {
                MediaType.APPLICATION_JSON_VALUE,
                MediaType.APPLICATION_XML_VALUE,
                MediaType.APPLICATION_YAML_VALUE},
        produces = {
                MediaType.APPLICATION_JSON_VALUE,
                MediaType.APPLICATION_XML_VALUE,
                MediaType.APPLICATION_YAML_VALUE})
    @Override
    public ResponseEntity<ClienteDTO> criar(@RequestBody ClienteDTO clienteDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteService.criar(clienteDTO));
    }

    @PutMapping(
        consumes = {
                MediaType.APPLICATION_JSON_VALUE,
                MediaType.APPLICATION_XML_VALUE,
                MediaType.APPLICATION_YAML_VALUE},
        produces = {
                MediaType.APPLICATION_JSON_VALUE,
                MediaType.APPLICATION_XML_VALUE,
                MediaType.APPLICATION_YAML_VALUE})
    @Override
    public ResponseEntity<ClienteDTO> atualizar(@RequestBody ClienteDTO clienteDTO) {
        return ResponseEntity.ok().body(clienteService.atualizar(clienteDTO));
    }

    @GetMapping("/enderecos/{id}")
    @Override
    public ResponseEntity<ClienteDTO> buscarClientePorEnderecoId(@PathVariable("id") Integer enderecoId) {
        ClienteDTO obj = clienteService.buscarClientePorEnderecoId(enderecoId);
        return ResponseEntity.ok().body(obj);
    }

    @DeleteMapping("/{id}")
    @Override
    public ResponseEntity<Void> deletar(@PathVariable("id") Integer id) {
        clienteService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
