package br.com.fco_romario.loja_ceda_artes.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {

    public BadRequestException() {
        super("Extensão do arquivo não suportada");
    }

    public BadRequestException(String message) {
        super(message);
    }
}
