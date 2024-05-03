package com.project.bolsadevalores.app.service;

import com.project.bolsadevalores.BolsadevaloresApplication;
import com.project.bolsadevalores.app.domain.Ordem;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

@Component
public class LivroDeOfertas {
    private static HashMap<String, LivroDeOfertas> LIVROS_DE_OFERTAS = new HashMap<>();
    private final Transacao transacao;
    private PriorityQueue<Ordem> ordensDeCompra;
    private PriorityQueue<Ordem> ordensDeVenda;

    LivroDeOfertas(Transacao transacao) {
        this.transacao = transacao;
        this.ordensDeCompra = new PriorityQueue<>(Comparator.comparing(Ordem::getValor).reversed().thenComparing(Ordem::getId));
        this.ordensDeVenda = new PriorityQueue<>(Comparator.comparing(Ordem::getValor).thenComparing(Ordem::getId));
    }

    public void registrarOrdemDeCompra(Ordem ordem) throws IOException {
        ordensDeCompra.add(ordem);
        transacao.efetuarCompra(ordem, ordensDeCompra, ordensDeVenda);
    }

    public void registrarOrdemDeVenda(Ordem ordem) throws IOException {
        ordensDeVenda.add(ordem);
        transacao.efetuarVenda(ordem, ordensDeVenda, ordensDeCompra);
    }

    public static LivroDeOfertas getInstance(String codigoDeAcao) {
        if (LIVROS_DE_OFERTAS.containsKey(codigoDeAcao)) {
            return LIVROS_DE_OFERTAS.get(codigoDeAcao);
        } else {
            LivroDeOfertas novoLivro = BolsadevaloresApplication.applicationContext.getBean(LivroDeOfertas.class);
            LIVROS_DE_OFERTAS.put(codigoDeAcao, novoLivro);
            return novoLivro;
        }
    }
}
