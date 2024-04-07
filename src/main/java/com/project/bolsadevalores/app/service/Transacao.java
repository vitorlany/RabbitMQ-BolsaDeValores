package com.project.bolsadevalores.app.service;

import com.project.bolsadevalores.app.domain.Ordem;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class Transacao {

    public void efetuarVenda(Ordem ordem, Queue<Ordem> filaAtual, Queue<Ordem> filaDeOutroTipoDeOrdem) {
        this.efetuarTransacao(ordem, filaAtual, filaDeOutroTipoDeOrdem, true);
    }

    public void efetuarCompra(Ordem ordem, Queue<Ordem> filaAtual, Queue<Ordem> filaDeOutroTipoDeOrdem) {
        this.efetuarTransacao(ordem, filaAtual, filaDeOutroTipoDeOrdem, false);
    }

    private void efetuarTransacao(Ordem ordem, Queue<Ordem> filaAtual, Queue<Ordem> filaDeOutroTipoDeOrdem, boolean isCompra) {
        Queue<Ordem> fila = filaDeOutroTipoDeOrdem;
        List<Ordem> remover = new ArrayList<>();
        int quantidadeNecessaria = ordem.getQuantidade();

        for (Ordem ordemIteracao : fila) {
            if ((quantidadeNecessaria == 0) || (isCompra ? (ordemIteracao.getValor() > ordem.getValor()) :
                                                           (ordemIteracao.getValor() < ordem.getValor()))) break;
            if (quantidadeNecessaria < ordemIteracao.getQuantidade()) {
                ordemIteracao.setQuantidade(ordemIteracao.getQuantidade() - quantidadeNecessaria);
                quantidadeNecessaria = 0;
            } else {
                quantidadeNecessaria -= ordemIteracao.getQuantidade();
                remover.add(ordemIteracao);
            }
        }

        List<Ordem> afetados = new ArrayList<>(remover);
        afetados.add(ordem);

        filaDeOutroTipoDeOrdem.removeAll(remover);
        if (quantidadeNecessaria == 0) {
            filaAtual.remove(ordem);
        } else {
            ordem.setQuantidade(quantidadeNecessaria);
        }

        // Chama mensageria com afetados
    }
}
