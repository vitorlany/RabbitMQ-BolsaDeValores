package com.project.bolsadevalores.app.service;

import com.project.bolsadevalores.app.domain.Ordem;
import com.project.bolsadevalores.app.messaging.QueueMessage;
import com.project.bolsadevalores.app.messaging.dto.AtualizacaoMessage;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

@AllArgsConstructor
public class Transacao {

    QueueMessage queue;

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
        afetados.forEach(afetado -> {
            AtualizacaoMessage atualizacao = new AtualizacaoMessage("venda", afetado);
            // Corrigr para acao
            queue.enviarAtualizacaoDeStatus(atualizacao, afetado.getCorretora());
        });

        filaDeOutroTipoDeOrdem.removeAll(remover);
        if (quantidadeNecessaria == 0) {
            filaAtual.remove(ordem);
        } else {
            ordem.setQuantidade(quantidadeNecessaria);
        }

        // Chama mensageria com afetados
    }
}
