package com.project.bolsadevalores.app.service;

import java.io.IOException;

import com.project.bolsadevalores.app.domain.Ordem;

public class BolsaDeValores {
    public static void comprarAcao(Ordem ordem, String codigoDeAcao) throws IOException {
        LivroDeOfertas livroDeOfertas = LivroDeOfertas.getInstance(codigoDeAcao);
        livroDeOfertas.registrarOrdemDeCompra(ordem);
    }

    public static void venderAcao(Ordem ordem, String codigoDeAcao) throws IOException {
        LivroDeOfertas livroDeOfertas = LivroDeOfertas.getInstance(codigoDeAcao);
        livroDeOfertas.registrarOrdemDeVenda(ordem);
    }
}
