package org.example.factory;

import org.example.enums.Direcoes;
import org.example.model.estrada.EstradaAbstrata;
import org.example.model.estrada.EstradaEsquerda;

import java.util.HashMap;
import java.util.Map;

public class EstradaFactory {
    private Map<Direcoes, EstradaAbstrata> estradasMap = new HashMap<>();

    public EstradaFactory() {
        estradasMap.put(Direcoes.ESQUERDA, new EstradaEsquerda());
    }

    public EstradaAbstrata getEstrada(int tipo) {
        try {
            return estradasMap.get(tipo);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
