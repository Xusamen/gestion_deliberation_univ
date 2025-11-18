package gestion_deliberation_univ.Controleurs;

import java.net.URL;
import java.util.ResourceBundle;

import gestion_deliberation_univ.Modeles.Fonctions;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

public class ControleurFenetreAjoutClasse implements Initializable {

    @FXML
    private TextField champ_annee_universitaire;

    @FXML
    private ChoiceBox<String> choicebox_niveau_classe;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        choicebox_niveau_classe.getItems().addAll("Licence_1", "Licence_2", "Licence_3", "Master_1", "Master_2");
    }

    @FXML
    void ajouter_la_classe(ActionEvent event) {

        String annee_univ = champ_annee_universitaire.getText();
        String niveau_classe = choicebox_niveau_classe.getValue();

        // Vérifier que les champs sont remplis
        if (annee_univ.isEmpty() || niveau_classe == null) {
            Fonctions.Alerte_erreur("Veuillez remplir tous les champs.");
            return;
        }

        // Vérifier le format 2025/2026
        if (!annee_univ.matches("^[0-9]{4}/[0-9]{4}$")) {
            Fonctions.Alerte_erreur("Format invalide. Exemple attendu : 2025/2026");
            return;
        }

        // Vérifier la cohérence des années
        String[] parts = annee_univ.split("/");
        int debut = Integer.parseInt(parts[0]);
        int fin = Integer.parseInt(parts[1]);
        if (fin != debut + 1) {
            Fonctions.Alerte_erreur("L'année universitaire doit être continue. Exemple : 2025/2026");
            return;
        }


        if (Fonctions.ajouter_classe(niveau_classe, annee_univ, ControleursFenetreAcceuil.data.getId())) {
            champ_annee_universitaire.clear();
            choicebox_niveau_classe.setValue(null);
            Fonctions.Alerte_succes("Classe ajoutée avec succès.");
            ControleursFenetreAcceuil.FermerFenetreAjoutClasse();
        } else {
            Fonctions.Alerte_erreur("Échec de l'ajout de la classe.");
        }
    }

    @FXML
    void annuler_ajout_classe(ActionEvent event) {
        ControleursFenetreAcceuil.FermerFenetreAjoutClasse();
    }

}
