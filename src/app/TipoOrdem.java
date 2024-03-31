package app;

public enum TipoOrdem {
    venda,
    compra;

    public static TipoOrdem getEnum(String value) {
        for(TipoOrdem tipoOrdem : TipoOrdem.values()) {
            if(tipoOrdem.name().equals(value)) {
                return tipoOrdem;
            }
        }
        throw new IllegalArgumentException("Tipo de ordem inválida");
    }
}
