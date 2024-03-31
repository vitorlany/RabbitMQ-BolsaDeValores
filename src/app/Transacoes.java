package app;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class Transacoes {

    public static void armazena(Ordem ordem, LivroDeOfertas livroDeOfertas) {
        Queue<Ordem> vendas = livroDeOfertas.getVendas();

        if (ordem.getTipo() == TipoOrdem.compra) {
            List<Ordem> remover = new ArrayList<>();
            int quantidadeNecessaria = ordem.getQuantidade();

            for (Ordem ordemIteracao : vendas) {
                if ((quantidadeNecessaria == 0) || (ordemIteracao.getValor() > ordem.getValor())) break;
                if (quantidadeNecessaria < ordemIteracao.getQuantidade()) {
                    ordemIteracao.setQuantidade(ordemIteracao.getQuantidade() - quantidadeNecessaria);
                    quantidadeNecessaria = 0;
                } else {
                    quantidadeNecessaria -= ordemIteracao.getQuantidade();
                    ordemIteracao.setDataHoraVenda(LocalDateTime.now());
                    remover.add(ordemIteracao);
                }
            }

            List<Ordem> afetados = new ArrayList<>(vendas);
            afetados.add(ordem);

            livroDeOfertas.atualizaCompra(ordem, quantidadeNecessaria, remover);

            // envia mensagem para afetados
        }
    }
}
