package com.project.bolsadevalores.app.domain;

import lombok.Data;

import java.util.UUID;

@Data
public class Ordem {
    private UUID id;
    private int quantidade;
    private double valor;
    private String corretora;
    private String codigoDeAcao;

    public Ordem(int quantidade, double valor, String corretora, String codigoDeAcao) {
        this.id = UUID.randomUUID();
        this.quantidade = quantidade;
        this.valor = valor;
        this.corretora = corretora;
        this.codigoDeAcao = codigoDeAcao;
    }
}