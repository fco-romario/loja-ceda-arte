package br.com.fco_romario.loja_ceda_artes.file.exporter.contract;

import br.com.fco_romario.loja_ceda_artes.dtos.ClienteDTO;
import org.springframework.core.io.Resource;

import java.util.List;

public interface FileExporter {

    Resource exportFile(List<ClienteDTO> clientes) throws Exception;
}
