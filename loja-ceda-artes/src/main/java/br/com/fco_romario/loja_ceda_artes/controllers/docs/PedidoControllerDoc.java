package br.com.fco_romario.loja_ceda_artes.controllers.docs;

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

public interface PedidoControllerDoc {

    @Operation(summary = "Buscar todos os Pedidos paginado.",
            description = "Paginação para trazer Pedidos por paginação - JSON, XML ou YAML.",
            tags = {"Pedidos"},
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
    public ResponseEntity<List<PedidoDTO>> buscarTodos();

    @Operation(summary = "Busca um Pedido.",
            description = "Busca um Pedido por seu ID - JSON, XML ou YAML.",
            tags = {"Pedidos"},
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
    public ResponseEntity<PedidoDTO> buscarPorId(@PathVariable Integer id);

    @Operation(summary = "Cria um Pedido.",
            description = "Cria um Pedido - JSON, XML ou YAML.",
            tags = {"Pedidos"},
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
    public ResponseEntity<PedidoDTO> criar(@RequestBody PedidoDTO pedidoDTO);

    @Operation(summary = "Atualiza um Pedido",
            description = "Atualiza um Pedido por seu ID - JSON, XML ou YAML.",
            tags = {"Pedidos"},
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
    public ResponseEntity<PedidoDTO> atualizar(@RequestBody PedidoDTO pedidoDTO);

    @Operation(summary = "Deleta um Pedido",
            description = "Deleta um Pedido por seu ID - JSON, XML ou YAML.",
            tags = {"Pedidos"},
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
}

