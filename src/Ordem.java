import java.util.UUID;

public record Ordem(
        UUID id,
        TipoOrdem tipo,
        String acao,
        int quantidade,
        double valor,
        String corretora
) {
    public Ordem(
            TipoOrdem tipo,
            String acao,
            int quantidade,
            double valor,
            String corretora
    ) {
        this(UUID.randomUUID(), tipo, acao, quantidade, valor, corretora);
    }
}
