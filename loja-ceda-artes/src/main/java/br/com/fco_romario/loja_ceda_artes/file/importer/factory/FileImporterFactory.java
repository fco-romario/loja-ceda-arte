package br.com.fco_romario.loja_ceda_artes.file.importer.factory;

import br.com.fco_romario.loja_ceda_artes.exception.BadRequestException;
import br.com.fco_romario.loja_ceda_artes.file.importer.contract.FileImporter;
import br.com.fco_romario.loja_ceda_artes.file.importer.impl.CsvImporter;
import br.com.fco_romario.loja_ceda_artes.file.importer.impl.XlsxImporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class FileImporterFactory {

    private Logger logger = LoggerFactory.getLogger(FileImporterFactory.class);

    @Autowired
    private ApplicationContext context;

    public FileImporter getImporter(String fileName) {
        if(fileName.equals(".xlsx")) {
            return context.getBean(XlsxImporter.class);
        } else if(fileName.equals(".csv")) {
            return context.getBean(CsvImporter.class);
        } else {
            throw new BadRequestException("Invalid file format!");
        }
    }
}
