package br.com.fco_romario.loja_ceda_artes.file.importer.impl;

import br.com.fco_romario.loja_ceda_artes.dtos.ProdutoDTO;
import br.com.fco_romario.loja_ceda_artes.file.importer.contract.FileImporter;

import java.io.InputStream;
import java.util.List;

public class XlsxImporter implements FileImporter {

    @Override
    public List<ProdutoDTO> importFile(InputStream inputStream) throws Exception {
        return List.of();
    }
}
