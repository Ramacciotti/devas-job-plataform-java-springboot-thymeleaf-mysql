package com.ramacciotti.devas.enums;

public enum Level {
    internship("Estágio"),
    trainee("Trainee"),
    junior("Júnior"),
    pleno("Pleno"),
    senior("Sênior"),
    specialist("Especialista");

    private final String description;

    Level(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}
