import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class Ordem {
    private UUID id;
    private TipoOrdem tipo;
    private String acao;
    private int quantidade;
    private double valor;
    private String corretora;
    private LocalDateTime dataHoraVenda;

    public Ordem(TipoOrdem tipo, String acao, int quantidade, double valor, String corretora) {
        this.id = UUID.randomUUID();
        this.tipo = tipo;
        this.acao = acao;
        this.quantidade = quantidade;
        this.valor = valor;
        this.corretora = corretora;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public TipoOrdem getTipo() {
        return tipo;
    }

    public void setTipo(TipoOrdem tipo) {
        this.tipo = tipo;
    }

    public String getAcao() {
        return acao;
    }

    public void setAcao(String acao) {
        this.acao = acao;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getCorretora() {
        return corretora;
    }

    public void setCorretora(String corretora) {
        this.corretora = corretora;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ordem ordem = (Ordem) o;
        return quantidade == ordem.quantidade && Double.compare(ordem.valor, valor) == 0 && Objects.equals(id, ordem.id) && tipo == ordem.tipo && Objects.equals(acao, ordem.acao) && Objects.equals(corretora, ordem.corretora);
    }

    public LocalDateTime getDataHoraVenda() {
        return dataHoraVenda;
    }

    public void setDataHoraVenda(LocalDateTime dataHoraVenda) {
        this.dataHoraVenda = dataHoraVenda;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, tipo, acao, quantidade, valor, corretora);
    }

    @Override
    public String toString() {
        return "Ordem{" +
                "id=" + id +
                ", tipo=" + tipo +
                ", acao='" + acao + '\'' +
                ", quantidade=" + quantidade +
                ", valor=" + valor +
                ", corretora='" + corretora + '\'' +
                '}';
    }
}