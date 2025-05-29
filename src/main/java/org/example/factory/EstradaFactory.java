package org.example.factory;

import org.example.enums.Direcoes;

import java.util.HashMap;
import java.util.Map;

public class EstradaFactory {
    private Map<Direcoes, String> estradasMap = new HashMap<>();

    public EstradaFactory() {
        estradasMap.put(Direcoes.CIMA, "CIMA");
        estradasMap.put(Direcoes.DIREITA, "DIREITA");
        estradasMap.put(Direcoes.BAIXO, "BAIXO");
        estradasMap.put(Direcoes.ESQUERDA, "ESQUERDA");
        estradasMap.put(Direcoes.CRUZAMENTO_CIMA, "");
        estradasMap.put(Direcoes.CRUZAMENTO_DIREITA, "");
        estradasMap.put(Direcoes.CRUZAMENTO_BAIXO, "");
        estradasMap.put(Direcoes.CRUZAMENTO_ESQUERDA, "");
        estradasMap.put(Direcoes.CRUZAMENTO_CIMA_DIREITA, "");
        estradasMap.put(Direcoes.CRUZAMENTO_CIMA_ESQUERDA, "");
        estradasMap.put(Direcoes.CRUZAMENTO_BAIXO_DIREITA, "");
        estradasMap.put(Direcoes.CRUZAMENTO_BAIXO_ESQUERDA, "");
    }

    public String getImagePath(int tipo) {
        try {
            Direcoes direcao = Direcoes.fromValor(tipo);
            return estradasMap.get(direcao);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
