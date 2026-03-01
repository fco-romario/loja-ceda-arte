package br.com.fco_romario.loja_ceda_artes.file.exporter.factory;

import br.com.fco_romario.loja_ceda_artes.exception.BadRequestException;
import br.com.fco_romario.loja_ceda_artes.file.exporter.MediaTypeFiles;
import br.com.fco_romario.loja_ceda_artes.file.exporter.contract.FileExporter;
import br.com.fco_romario.loja_ceda_artes.file.exporter.impl.CsvExporter;
import br.com.fco_romario.loja_ceda_artes.file.exporter.impl.PdfExporter;
import br.com.fco_romario.loja_ceda_artes.file.exporter.impl.XlsxExporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class FileExporterFactory {

    Logger logger = LoggerFactory.getLogger(FileExporterFactory.class);

    @Autowired
    private ApplicationContext context;

    public FileExporter getExporter(String acceptHeader) {
        if(acceptHeader.equalsIgnoreCase(MediaTypeFiles.APPLICATION_CSV_VALUE)) {
            return context.getBean(CsvExporter.class);
        } else if (acceptHeader.equalsIgnoreCase(MediaTypeFiles.APPLICATION_PDF_VALUE)) {
            return context.getBean(PdfExporter.class);
        } else if (acceptHeader.equalsIgnoreCase(MediaTypeFiles.APPLICATION_XLSX_VALUE)) {
            return context.getBean(XlsxExporter.class);
        } else {
            throw new BadRequestException("Formato do arquivo inválido");
        }
    }
}
