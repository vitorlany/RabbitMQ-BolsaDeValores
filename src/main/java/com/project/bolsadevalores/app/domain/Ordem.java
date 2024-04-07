package com.project.bolsadevalores.app.domain;

import lombok.Data;

import java.util.UUID;

@Data
public class Ordem {
    private UUID id;
    private int quantidade;
    private double valor;
    private String corretora;

    public Ordem(int quantidade, double valor, String corretora) {
        this.id = UUID.randomUUID();
        this.quantidade = quantidade;
        this.valor = valor;
        this.corretora = corretora;
    }
}