package com.project.bolsadevalores.app.controller;

public record AcaoRequest(String codigoAcao,
                          int quantidade,
                          double valor,
                          String corretora) {
}
