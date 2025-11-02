package br.com.fco_romario.loja_ceda_artes.file.exporter.impl;

import br.com.fco_romario.loja_ceda_artes.dtos.ClienteDTO;
import br.com.fco_romario.loja_ceda_artes.exception.FileNotFoundException;
import br.com.fco_romario.loja_ceda_artes.file.exporter.contract.FileExporter;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Component
public class PdfExporter implements FileExporter {

    @Override
    public Resource exportFile(List<ClienteDTO> clientes) throws Exception {
        InputStream inputStream = getClass().getResourceAsStream("/templates/cliente.jrxml");
        String logoPath = Objects.requireNonNull(getClass().getResource("/templates/logo_ceda-artes.PNG")).getPath();

        if(inputStream == null)
            throw new FileNotFoundException("Template não encontrado: /templates/cliente.jrxml");

        if(logoPath == null)
            throw new FileNotFoundException("Imagem da logo não encontrada: /logo_ceda-artes.PNG");

        JasperReport JasperReport = JasperCompileManager.compileReport(inputStream);

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(clientes);

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("P_LOGO", logoPath);

        JasperPrint jasperPrint = JasperFillManager.fillReport(JasperReport, parameters, dataSource);

        try(ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
            return new ByteArrayResource(outputStream.toByteArray());
        }
    }
}
