package br.com.fco_romario.loja_ceda_artes.controllers;

import br.com.fco_romario.loja_ceda_artes.controllers.docs.CategoriaControllerDoc;
import br.com.fco_romario.loja_ceda_artes.dtos.CategoriaDTO;
import br.com.fco_romario.loja_ceda_artes.dtos.ClienteDTO;
import br.com.fco_romario.loja_ceda_artes.services.CategoriaService;
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
@RequestMapping(value = "api/v1/categorias")
@Tag(name = "Categorias", description = "Endpoint para gerenciar Categorias")
public class CategoriaController implements CategoriaControllerDoc {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping(
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_YAML_VALUE})
    @Override
    public ResponseEntity<List<CategoriaDTO>> buscarTodos() {
        List<CategoriaDTO> list = categoriaService.buscarTodos();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}",
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_YAML_VALUE})
    @Override
    public ResponseEntity<CategoriaDTO> buscarPorId(@PathVariable Integer id) {
        CategoriaDTO obj = categoriaService.buscarPorId(id);
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
    public ResponseEntity<CategoriaDTO> criar(@RequestBody CategoriaDTO categoriaDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaService.criar(categoriaDTO));
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
    public ResponseEntity<CategoriaDTO> atualizar(@RequestBody CategoriaDTO categoriaDTO) {
        return ResponseEntity.ok().body(categoriaService.atualizar(categoriaDTO));
    }

    @DeleteMapping("/{id}")
    @Override
    public ResponseEntity<Void> deletar(@PathVariable("id") Integer id) {
        categoriaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
