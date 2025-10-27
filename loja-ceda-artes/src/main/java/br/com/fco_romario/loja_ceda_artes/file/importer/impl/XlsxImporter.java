package br.com.fco_romario.loja_ceda_artes.file.importer.impl;

import br.com.fco_romario.loja_ceda_artes.dtos.ProdutoDTO;
import br.com.fco_romario.loja_ceda_artes.file.importer.contract.FileImporter;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class XlsxImporter implements FileImporter {

    @Override
    public List<ProdutoDTO> importFile(InputStream inputStream) throws Exception {
        try(XSSFWorkbook workbook = new XSSFWorkbook(inputStream)) {
            XSSFSheet sheet = workbook.getSheetAt(0);

            Iterator<Row> rowIterator = sheet.iterator();

            if(rowIterator.hasNext()) rowIterator.next();

            return parseRowsToProdutoDTOList(rowIterator);
        }
    }

    private List<ProdutoDTO> parseRowsToProdutoDTOList(Iterator<Row> rowIterator) {
        List<ProdutoDTO> produtos = new ArrayList<>();

        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();

            if(isRowValid(row)) {
                produtos.add(parseRowToProdutoDTO(row));
            }
        }
    }

    private ProdutoDTO parseRowToProdutoDTO(Row row) {
        ProdutoDTO produto = new ProdutoDTO();
        produto.setNome(row.getCell(0).getStringCellValue());
        produto.setPreco(Double.parseDouble(row.getCell(1).getStringCellValue()));

        return produto;
    }

    private static boolean isRowValid(Row row) {
        return row.getCell(0) != null && row.getCell(0).getCellType() != CellType.BLANK;
    }


}
