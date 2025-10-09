package br.com.fco_romario.loja_ceda_artes.controllers;

import br.com.fco_romario.loja_ceda_artes.controllers.docs.PedidoControllerDoc;
import br.com.fco_romario.loja_ceda_artes.dtos.ClienteDTO;
import br.com.fco_romario.loja_ceda_artes.dtos.PedidoDTO;
import br.com.fco_romario.loja_ceda_artes.services.PedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/v1/pedidos")
@Tag(name = "Pedidos", description = "Endpoint para gerenciar Pedidos")
public class PedidoController implements PedidoControllerDoc {

    @Autowired
    private PedidoService pedidoService;

    @GetMapping(
        produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_YAML_VALUE})
    @Override
    public ResponseEntity<List<PedidoDTO>> buscarTodos() {
        List<PedidoDTO> list = pedidoService.buscarTodos();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}",
        produces = {
                MediaType.APPLICATION_JSON_VALUE,
                MediaType.APPLICATION_XML_VALUE,
                MediaType.APPLICATION_YAML_VALUE})
    @Override
    public ResponseEntity<PedidoDTO> buscarPorId(@PathVariable Integer id) {
        PedidoDTO obj = pedidoService.buscarPorId(id);
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
    public ResponseEntity<PedidoDTO> criar(@RequestBody PedidoDTO pedidoDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(pedidoService.criar(pedidoDTO));
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
    public ResponseEntity<PedidoDTO> atualizar(@RequestBody PedidoDTO pedidoDTO) {
        return ResponseEntity.ok().body(pedidoService.atualizar(pedidoDTO));
    }

    @DeleteMapping("/{id}")
    @Override
    public ResponseEntity<Void> deletar(@PathVariable("id") Integer id) {
        pedidoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}

