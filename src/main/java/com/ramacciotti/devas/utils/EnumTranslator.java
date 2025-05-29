package com.ramacciotti.devas.utils;

import com.ramacciotti.devas.dto.UserDTO;
import com.ramacciotti.devas.enums.Level;
import com.ramacciotti.devas.enums.Objective;
import com.ramacciotti.devas.enums.Preference;
import com.ramacciotti.devas.model.User;

public class EnumTranslator {

    public static String translateLevel(Level level) {
        if (level == null) return "";
        return switch (level) {
            case internship -> "Estágio";
            case trainee -> "Trainee";
            case junior -> "Júnior";
            case pleno -> "Pleno";
            case senior -> "Sênior";
            case specialist -> "Especialista";
        };
    }

    public static String translatePreference(Preference preference) {
        if (preference == null) return "";
        return switch (preference) {
            case company -> "Presencial";
            case online -> "Remoto";
            case hybrid -> "Híbrido";
        };
    }

    public static String translateObjective(Objective objective) {
        if (objective == null) return "";
        return switch (objective) {
            case first_oportunity -> "Primeira Oportunidade";
            case looking_oportunity -> "Buscando Oportunidade";
            case career_transition -> "Transição de Carreira";
            case return_market -> "Retorno ao Mercado";
        };
    }

    public static void translateEnums(User user, UserDTO userDTO) {
        if (user.getJob() != null) {
            userDTO.getJob().setLevel(EnumTranslator.translateLevel(user.getJob().getLevel()));
            userDTO.getJob().setPreference(EnumTranslator.translatePreference(user.getJob().getPreference()));
            userDTO.getJob().setObjective(EnumTranslator.translateObjective(user.getJob().getObjective()));
        }
    }

}
