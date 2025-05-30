package com.ramacciotti.devas.enums;

public enum Preference {

    company("Presencial"),
    online("Remoto"),
    hybrid("Híbrido");

    private final String description;

    Preference(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
