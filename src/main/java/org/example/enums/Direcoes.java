package org.example.enums;

public enum Direcoes {

    CIMA(1),
    DIREITA(2),
    BAIXO(3),
    ESQUERDA(4),
    CRUZAMENTO_CIMA(5),
    CRUZAMENTO_DIREITA(6),
    CRUZAMENTO_BAIXO(7),
    CRUZAMENTO_ESQUERDA(8),
    CRUZAMENTO_CIMA_DIREITA(9),
    CRUZAMENTO_CIMA_ESQUERDA(10),
    CRUZAMENTO_BAIXO_DIREITA(11),
    CRUZAMENTO_BAIXO_ESQUERDA(12);


    private final int valor;

    public static Direcoes fromValor(int valor) {
        for (Direcoes tipo : values()) {
            if (tipo.getValor() == valor) {
                return tipo;
            }
        }
        throw new IllegalArgumentException();
    }


    Direcoes(int valor) {
        this.valor = valor;
    }

    public int getValor() {
        return valor;
    }
}
