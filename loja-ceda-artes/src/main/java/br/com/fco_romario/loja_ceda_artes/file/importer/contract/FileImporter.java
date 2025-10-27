package br.com.fco_romario.loja_ceda_artes.file.importer.contract;

import br.com.fco_romario.loja_ceda_artes.dtos.ProdutoDTO;

import java.io.InputStream;
import java.util.List;

public interface FileImporter {
    List<ProdutoDTO> importFile(InputStream inputStream) throws Exception;
}
