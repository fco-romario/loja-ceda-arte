package br.com.fco_romario.loja_ceda_artes.controllers.docs;

import br.com.fco_romario.loja_ceda_artes.dtos.ClienteDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface ClienteControllerDoc {

    @Operation(summary = "Buscar todos os Clientes paginado.",
            description = "Paginação para trazer Clientes por paginação - JSON, XML ou YAML.",
            tags = {"Clientes"},
            responses = {
                    @ApiResponse(
                            description = "Succes",
                            responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            array = @ArraySchema(schema = @Schema(implementation = ClienteDTO.class))),
                                    @Content(
                                            mediaType = MediaType.APPLICATION_XML_VALUE,
                                            array = @ArraySchema(schema = @Schema(implementation = ClienteDTO.class))),
                                    @Content(
                                            mediaType = MediaType.APPLICATION_YAML_VALUE,
                                            array = @ArraySchema(schema = @Schema(implementation = ClienteDTO.class))),
                            }),
                    @ApiResponse(description = "No Cotent", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
            }
    )
    public ResponseEntity<Page<ClienteDTO>> buscarTodos(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "12") Integer size,
            @RequestParam(value = "direction", defaultValue = "asc") String direction
    );

    @Operation(summary = "Busca um Cliente.",
            description = "Busca um Cliente por seu ID - JSON, XML ou YAML.",
            tags = {"Clientes"},
            responses = {
                    @ApiResponse(
                            description = "Succes",
                            responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = ClienteDTO.class)),
                                    @Content(
                                            mediaType = MediaType.APPLICATION_XML_VALUE,
                                            schema = @Schema(implementation = ClienteDTO.class)),
                                    @Content(
                                            mediaType = MediaType.APPLICATION_YAML_VALUE,
                                            schema = @Schema(implementation = ClienteDTO.class)),
                            }),
                    @ApiResponse(description = "No Cotent", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
            }
    )
    public ResponseEntity<ClienteDTO> buscarPorId(@PathVariable Integer id);

    @Operation(summary = "Cria um Cliente.",
            description = "Cria um Cliente - JSON, XML ou YAML.",
            tags = {"Clientes"},
            responses = {
                    @ApiResponse(
                            description = "Succes",
                            responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = ClienteDTO.class)),
                                    @Content(
                                            mediaType = MediaType.APPLICATION_XML_VALUE,
                                            schema = @Schema(implementation = ClienteDTO.class)),
                                    @Content(
                                            mediaType = MediaType.APPLICATION_YAML_VALUE,
                                            schema = @Schema(implementation = ClienteDTO.class)),
                            }
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
            }
    )
    public ResponseEntity<ClienteDTO> criar(@RequestBody ClienteDTO clienteDTO);

    @Operation(summary = "Atualiza um Cliente",
            description = "Atualiza um Cliente por seu ID - JSON, XML ou YAML.",
            tags = {"Clientes"},
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = ClienteDTO.class)),
                                    @Content(
                                            mediaType = MediaType.APPLICATION_XML_VALUE,
                                            schema = @Schema(implementation = ClienteDTO.class)),
                                    @Content(
                                            mediaType = MediaType.APPLICATION_YAML_VALUE,
                                            schema = @Schema(implementation = ClienteDTO.class)),
                            }
                    ),
                    @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
            }
    )
    public ResponseEntity<ClienteDTO> atualizar(@RequestBody ClienteDTO clienteDTO);

    @Operation(summary = "Busca um Cliente.",
            description = "Busca um Cliente por ID do seu Endereço - JSON, XML ou YAML.",
            tags = {"Clientes"},
            responses = {
                    @ApiResponse(
                            description = "Succes",
                            responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = ClienteDTO.class)),
                                    @Content(
                                            mediaType = MediaType.APPLICATION_XML_VALUE,
                                            schema = @Schema(implementation = ClienteDTO.class)),
                                    @Content(
                                            mediaType = MediaType.APPLICATION_YAML_VALUE,
                                            schema = @Schema(implementation = ClienteDTO.class)),
                            }),
                    @ApiResponse(description = "No Cotent", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
            }
    )
    public ResponseEntity<ClienteDTO> buscarClientePorEnderecoId(@PathVariable("id") Integer enderecoId);

    @Operation(summary = "Deleta um Cliente",
            description = "Deleta um Cliente por seu ID - JSON, XML ou YAML.",
            tags = {"Clientes"},
            responses = {
                    @ApiResponse(
                            description = "No Content",
                            responseCode = "204", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
            }
    )
    public ResponseEntity<Void> deletar(@PathVariable("id") Integer id);

    @Operation(summary = "Inativa um Cliente.",
            description = "Inativa um Cliente por seu ID - JSON, XML ou YAML.",
            tags = {"Clientes"},
            responses = {
                    @ApiResponse(
                            description = "Succes",
                            responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = ClienteDTO.class)),
                                    @Content(
                                            mediaType = MediaType.APPLICATION_XML_VALUE,
                                            schema = @Schema(implementation = ClienteDTO.class)),
                                    @Content(
                                            mediaType = MediaType.APPLICATION_YAML_VALUE,
                                            schema = @Schema(implementation = ClienteDTO.class)),
                            }),
                    @ApiResponse(description = "No Cotent", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
            }
    )
    public ResponseEntity<ClienteDTO> inativarCliente(@PathVariable("id")  Integer id);
}
