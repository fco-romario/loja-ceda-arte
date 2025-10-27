package br.com.fco_romario.loja_ceda_artes.file.importer.impl;

import br.com.fco_romario.loja_ceda_artes.dtos.ProdutoDTO;
import br.com.fco_romario.loja_ceda_artes.file.importer.contract.FileImporter;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Component
public class CsvImporter implements FileImporter {

    @Override
    public List<ProdutoDTO> importFile(InputStream inputStream) throws Exception {
        CSVFormat format = CSVFormat.Builder.create()
                .setHeader()
                .setSkipHeaderRecord(true)
                .setIgnoreEmptyLines(true)
                .setTrim(true)
                .build();

        Iterable<CSVRecord> records = format.parse(new InputStreamReader(inputStream));
        return parseRecordsToProdutoDTOs(records);
    }

    private List<ProdutoDTO> parseRecordsToProdutoDTOs(Iterable<CSVRecord> records) {
        List<ProdutoDTO> produtos = new ArrayList<>();

        for(CSVRecord record: records) {
            ProdutoDTO produto = new ProdutoDTO();

            produto.setNome(record.get("nome"));
            produto.setPreco(Double.parseDouble(record.get("preco")));

            produtos.add(produto);
        }

        return produtos;
    }
}