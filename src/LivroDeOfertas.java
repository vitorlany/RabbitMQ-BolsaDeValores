import java.util.Comparator;
import java.util.PriorityQueue;

public class LivroDeOfertas {

    private PriorityQueue<Ordem> vendas;

    private PriorityQueue<Ordem> compras;

    public LivroDeOfertas() {
        this.vendas = new PriorityQueue<>(Comparator.comparing(Ordem::valor).thenComparing(Ordem::id));
        this.compras = new PriorityQueue<>(Comparator.comparing(Ordem::valor).reversed().thenComparing(Ordem::id));
    }

    public void armazenar(String tipo, String acao, int quantidade, double valor, String corretora) {
        Ordem ordem = registrarOferta(tipo, acao, quantidade, valor, corretora);
        // verifica se é possível realizar e cria transação
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
        livroDeOfertas.armazenar("compra", "XPTO", 15, 150, "ABCD");
        livroDeOfertas.armazenar("venda", "XPTO", 150, 1500, "ABCD");
        livroDeOfertas.armazenar("compra", "XPTO", 15, 1500, "ABCD");

        System.out.println(livroDeOfertas.compras);
        System.out.println(livroDeOfertas.vendas);
    }
}
