package br.com.fco_romario.loja_ceda_artes.serializer;

import br.com.fco_romario.loja_ceda_artes.enums.TipoCliente;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class TipoClienteSerializer extends JsonSerializer<TipoCliente> {

    @Override
    public void serialize(TipoCliente tipo, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        String formatadoTipo = tipo.getCodigo() == 2  ? "2" : "1";

        jsonGenerator.writeString(formatadoTipo);
    }
}
