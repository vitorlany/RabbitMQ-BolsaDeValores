package com.project.bolsadevalores.app.service;

import com.project.bolsadevalores.app.domain.Ordem;

import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

public class LivroDeOfertas {
    private static HashMap<String, LivroDeOfertas> LIVROS_DE_OFERTAS = new HashMap<>();
    private Transacao transacao;
    private String codigoDeAcao;
    private PriorityQueue<Ordem> ordensDeCompra;
    private PriorityQueue<Ordem> ordensDeVenda;

    private LivroDeOfertas(String codigoDeAcao) {
        this.codigoDeAcao = codigoDeAcao;
        this.transacao = new Transacao();
        this.ordensDeCompra = new PriorityQueue<>(Comparator.comparing(Ordem::getValor).reversed().thenComparing(Ordem::getId));
        this.ordensDeVenda = new PriorityQueue<>(Comparator.comparing(Ordem::getValor).thenComparing(Ordem::getId));
    }

    public void registrarOrdemDeCompra(Ordem ordem) {
        ordensDeCompra.add(ordem);
        transacao.efetuarCompra(ordem, ordensDeCompra, ordensDeVenda);
    }

    public void registrarOrdemDeVenda(Ordem ordem) {
        ordensDeVenda.add(ordem);
        transacao.efetuarVenda(ordem, ordensDeVenda, ordensDeCompra);
    }

    public static LivroDeOfertas getInstance(String codigoDeAcao) {
        if (LIVROS_DE_OFERTAS.containsKey(codigoDeAcao)) {
            return LIVROS_DE_OFERTAS.get(codigoDeAcao);
        } else {
            LivroDeOfertas novoLivro = new LivroDeOfertas(codigoDeAcao);
            LIVROS_DE_OFERTAS.put(codigoDeAcao, novoLivro);
            return novoLivro;
        }
    }
}
