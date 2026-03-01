package br.com.fco_romario.loja_ceda_artes.config;

public interface TestConfigs {
    int SERVER_PORT = 8888;

    String HEADER_PARAM_AUTHORIZATION = "Authorization";
    String HEADER_PARAM_ORIGIN = "Origin";

    String ORIGIN_EXEMPLO = "http://localhost:8081";
    String ORIGIN_EXEMPLO_NAO_AUTORIZADA = "http://origem-exemplo-nao-autorizada.com";
    String ORIGIN_LOCALHOST = "http://localhost:8080";
}
