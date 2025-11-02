package br.com.fco_romario.loja_ceda_artes.controllers;

import br.com.fco_romario.loja_ceda_artes.controllers.docs.ClienteControllerDoc;
import br.com.fco_romario.loja_ceda_artes.dtos.ClienteDTO;
import br.com.fco_romario.loja_ceda_artes.file.exporter.MediaTypeFiles;
import br.com.fco_romario.loja_ceda_artes.services.ClienteService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
    public ResponseEntity<PagedModel<EntityModel<ClienteDTO>>> buscarTodos(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "12") Integer size,
            @RequestParam(value = "direction", defaultValue = "asc") String direction
    ) {
        var sortDirection = "desc".equalsIgnoreCase(direction)
                ? Sort.Direction.DESC
                : Sort.Direction.ASC;

        Pageable paginado = PageRequest.of(page, size, Sort.by(sortDirection, "nome"));
        return ResponseEntity.ok(clienteService.buscarTodos(paginado));
    }

    @GetMapping(value = "exportar-paginas",
            produces = {
                    MediaTypeFiles.APPLICATION_XLSX_VALUE,
                    MediaTypeFiles.APPLICATION_CSV_VALUE,
                    MediaTypeFiles.APPLICATION_PDF_VALUE
    })
    public ResponseEntity<Resource> exportarPagina(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "12") Integer size,
            @RequestParam(value = "direction", defaultValue = "asc") String direction,
            HttpServletRequest request
    ) {
        var sortDirection = "desc".equalsIgnoreCase(direction)
                ? Sort.Direction.DESC
                : Sort.Direction.ASC;

        Pageable paginado = PageRequest.of(page, size, Sort.by(sortDirection, "nome"));

        String acceptHeader = request.getHeader(HttpHeaders.ACCEPT);

        Resource file = clienteService.exportarPagina(paginado, acceptHeader);

        Map<String, String> extensionMap = Map.of(
                MediaTypeFiles.APPLICATION_XLSX_VALUE, ".xlsx",
                MediaTypeFiles.APPLICATION_CSV_VALUE, ".csv",
                MediaTypeFiles.APPLICATION_PDF_VALUE, ".pdf"
        );

        String fileExtension = extensionMap.getOrDefault(acceptHeader, "");
        String contentType = acceptHeader != null ? acceptHeader : "application/octet-stream";
        var filename = "clientes_exportado" + fileExtension;

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + filename + "\"")
                .body(file);
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

    @PatchMapping("/{id}")
    @Override
    public ResponseEntity<ClienteDTO> inativarCliente(@PathVariable("id")  Integer id) {
        ClienteDTO obj = clienteService.inativarCliente(id);
        return ResponseEntity.ok().body(obj);
    }
}
