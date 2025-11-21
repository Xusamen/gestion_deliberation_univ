package gestion_deliberation_univ.Outils;

import java.util.regex.*;

public class Outils {

    // Méthode pour extraire le semestre d'une chaîne donnée ex: "Notes S1" -> "S1"
    public static String extraireSemestre(String input) {
        Pattern pattern = Pattern.compile("S\\d+");
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            return matcher.group();
        }
        return "";
    }

}
