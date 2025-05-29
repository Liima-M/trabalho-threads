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
        estradasMap.put(Direcoes.CRUZAMENTO_CIMA, "CRUZAMENTOCIMA");
        estradasMap.put(Direcoes.CRUZAMENTO_DIREITA, "CRUZAMENTODIREITA");
        estradasMap.put(Direcoes.CRUZAMENTO_BAIXO, "CRUZAMENTOBAIXO");
        estradasMap.put(Direcoes.CRUZAMENTO_ESQUERDA, "CRUZAMENTOESQUERDA");
        estradasMap.put(Direcoes.CRUZAMENTO_CIMA_DIREITA, "CRUZAMENTODIREITACIMA");
        estradasMap.put(Direcoes.CRUZAMENTO_CIMA_ESQUERDA, "CRUZAMENTOCIMAESQUERDA");
        estradasMap.put(Direcoes.CRUZAMENTO_BAIXO_DIREITA, "CRUZAMENTODIREITABAIXO");
        estradasMap.put(Direcoes.CRUZAMENTO_BAIXO_ESQUERDA, "CRUZAMENTOBAIXOESQUERDA");
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
