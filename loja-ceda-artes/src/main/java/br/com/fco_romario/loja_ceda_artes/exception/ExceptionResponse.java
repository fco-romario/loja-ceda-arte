package br.com.fco_romario.loja_ceda_artes.exception;

import java.util.Date;

public record ExceptionResponse(
        Date timeStamp,
        String message,
        String details
) {
}
