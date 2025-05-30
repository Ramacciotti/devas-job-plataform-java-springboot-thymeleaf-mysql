package com.ramacciotti.devas.enums;

public enum Objective {

    first_oportunity("Primeira Oportunidade"),
    seeking_oportunity("Buscando Oportunidade"),
    career_transition("Transição de Carreira"),
    return_market("Retorno ao Mercado");

    private final String description;

    Objective(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}