import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class LivroDeOfertas {

    private PriorityQueue<Ordem> vendas;

    private PriorityQueue<Ordem> compras;

    public LivroDeOfertas() {
        this.vendas = new PriorityQueue<>(Comparator.comparing(Ordem::getValor).thenComparing(Ordem::getId));
        this.compras = new PriorityQueue<>(Comparator.comparing(Ordem::getValor).reversed().thenComparing(Ordem::getId));
    }

    public void armazenar(String tipo, String acao, int quantidade, double valor, String corretora) {
        Ordem ordem = registrarOferta(tipo, acao, quantidade, valor, corretora);

        if (ordem.getTipo() == TipoOrdem.compra) {
            List<Ordem> remover = new ArrayList<>();
            int quantidadeNecessaria = ordem.getQuantidade();
            for (Ordem ordemIteracao : vendas) {
                if (ordemIteracao.getValor() > ordem.getValor()) break;
                if (quantidadeNecessaria < ordemIteracao.getQuantidade()) {
                    ordemIteracao.setQuantidade(ordemIteracao.getQuantidade() - quantidadeNecessaria);
                    quantidadeNecessaria = 0;
                    break;
                } else {
                    quantidadeNecessaria -= ordemIteracao.getQuantidade();
                    remover.add(ordemIteracao);
                }
            }
            vendas.removeAll(remover);
            if (quantidadeNecessaria == 0) {
                compras.remove(ordem);
            } else {
                ordem.setQuantidade(quantidadeNecessaria);
            }
        }
    }

    private Ordem registrarOferta(String tipo, String acao, int quantidade, double valor, String corretora) {
        TipoOrdem tipoOrdem = TipoOrdem.getEnum(tipo);
        Ordem ordem = new Ordem(tipoOrdem, acao, quantidade, valor, corretora);
        if (tipoOrdem == TipoOrdem.venda) {
            vendas.add(ordem);
        }
        if (tipoOrdem == TipoOrdem.compra) {
            compras.add(ordem);
        }
        return ordem;
    }

    public static void main(String[] args) {
        LivroDeOfertas livroDeOfertas = new LivroDeOfertas();

        livroDeOfertas.armazenar("venda", "XPTO", 15, 150, "ABCD");
        livroDeOfertas.armazenar("venda", "XPTO", 15, 150, "ABCD");
        livroDeOfertas.armazenar("compra", "XPTO", 35, 150, "ABCD");
        livroDeOfertas.armazenar("venda", "XPTO", 150, 1500, "ABCD");
        livroDeOfertas.armazenar("compra", "XPTO", 15, 1500, "ABCD");

        System.out.println(livroDeOfertas.compras);
        System.out.println(livroDeOfertas.vendas);
    }
}
