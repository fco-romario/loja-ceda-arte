package br.com.fco_romario.loja_ceda_artes.services;

import br.com.fco_romario.loja_ceda_artes.dtos.ProdutoDTO;
import br.com.fco_romario.loja_ceda_artes.domain.*;
import br.com.fco_romario.loja_ceda_artes.exception.BadRequestException;
import br.com.fco_romario.loja_ceda_artes.exception.FileStorageException;
import br.com.fco_romario.loja_ceda_artes.exception.ResourceNotFoundException;
import br.com.fco_romario.loja_ceda_artes.file.importer.contract.FileImporter;
import br.com.fco_romario.loja_ceda_artes.file.importer.factory.FileImporterFactory;
import br.com.fco_romario.loja_ceda_artes.mapper.ProdutoMapper;
import br.com.fco_romario.loja_ceda_artes.repositories.ProdutoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private FileImporterFactory importerFactory;

//    @Autowired
//    private ModelMapper modelMapper;

    public ProdutoDTO buscarPorId(Integer id) {
        Produto entity = produtoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Objecto não encontrado id: " + id + ", tipo: " + ProdutoDTO.class.getSimpleName()));

        return ProdutoMapper.toDTO(entity);
    }

    public List<ProdutoDTO> buscarTodos() {
        return produtoRepository.findAll()
                .stream()
                .map(ProdutoMapper::toDTO)
                .toList();
    }

    public ProdutoDTO criar(ProdutoDTO produtoDTO) {
        Produto entity = ProdutoMapper.toEntity(produtoDTO);
        return ProdutoMapper.toDTO(produtoRepository.save(entity));
    }

    public ProdutoDTO atualizar(ProdutoDTO produtoDTO) {
        Produto entity =  ProdutoMapper.toEntity(buscarPorId(produtoDTO.getId()));

        entity.setNome(produtoDTO.getNome());
        entity.setPreco(produtoDTO.getPreco());
//        entity.getCategorias().addAll(
//                Arrays.asList(modelMapper.map(produtoDTO.getCategorias(), Categoria.class))
//        );

        return ProdutoMapper.toDTO(produtoRepository.save(entity));
    }

    public void deletar(Integer id) {
        Produto entity =  ProdutoMapper.toEntity(buscarPorId(id));
        produtoRepository.delete(entity);
    }

    public List<ProdutoDTO> criacaoMassiva(MultipartFile file) throws Exception {
        if(file.isEmpty())
            throw new BadRequestException("Impossível salvar Arquivo vasio. Envie um arquivo válido!");

        try(InputStream inputStream = file.getInputStream()) {
            String fileName = Optional.ofNullable(file.getOriginalFilename())
                    .orElseThrow(() -> new BadRequestException("Nome do arquivo não pode ser nulo!"));

            FileImporter importer = this.importerFactory.getImporter(fileName);

            List<Produto> entities =  importer.importFile(inputStream)
                    .stream()
                    .map(dto -> produtoRepository.save(ProdutoMapper.toEntity(dto)))
                    .toList();

            return entities
                    .stream()
                    .map(entity -> {
                        var dto = ProdutoMapper.toDTO(entity);
                        //adicionaLinksHateoas(dto); todo: adicionar suport os Links HATEOAS
                        return dto;
                    }).toList();

        } catch (Exception e) {
            throw new FileStorageException("Erro ao processar o arquivo!");
        }
    }
}
