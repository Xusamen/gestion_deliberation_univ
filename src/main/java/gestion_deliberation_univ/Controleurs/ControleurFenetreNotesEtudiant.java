package gestion_deliberation_univ.Controleurs;

import java.net.URL;
import java.util.ResourceBundle;

import gestion_deliberation_univ.Modeles.Fonctions;
import gestion_deliberation_univ.Modeles.StructuresDonnees.structure_report_note;
import gestion_deliberation_univ.Modeles.StructuresDonnees.suggestion_matiere;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class ControleurFenetreNotesEtudiant implements Initializable {

    @FXML
    private Text information_etudiant;

    @FXML
    private Text intituler_notes;

    @FXML
    private TextField note_obtenu;

    @FXML
    private ChoiceBox<String> numero_devoir;

    @FXML
    private ChoiceBox<suggestion_matiere> suggestion_matiere;

    @FXML
    private TableColumn<structure_report_note, String> colonne_nom_matiere;

    @FXML
    private TableColumn<structure_report_note, String> colonne_note1;

    @FXML
    private TableColumn<structure_report_note, String> colonne_note2;

    @FXML
    private TableColumn<structure_report_note, String> colonne_session;

    @FXML
    private TableView<structure_report_note> tableau_note;

    @FXML
    private CheckBox checbox_session_rattrapage;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

        new Thread(() -> {
            var notes = Fonctions.lister_notes_etudiant(ControleurFenetreListeEtudiant.data.getId_etudiant(),
                    ControleursFenetreClasse.data.getId_classe(), ControleurFenetreListeEtudiant.semestre);
            Platform.runLater(() -> {
                tableau_note.setItems(notes);
            });
        }).start();

        new Thread(() -> {
            String intitule = ControleursFenetreClasse.data.getNiveau_classe() + " | Année universitaire: " +
                    ControleursFenetreClasse.data.getAnnee_univ() + " | " + ControleurFenetreListeEtudiant.semestre;

            String infoEtudiant = "Étudiant: " + ControleurFenetreListeEtudiant.data.getNom_prenom() +
                    " | Matricule: " + ControleurFenetreListeEtudiant.data.getMatricule();

            var matieres = Fonctions.lister_matiere_classe(ControleursFenetreClasse.data.getId_classe(),
                    ControleurFenetreListeEtudiant.semestre);

            Platform.runLater(() -> {
                intituler_notes.setText(intitule);
                information_etudiant.setText(infoEtudiant);
                numero_devoir.getItems().addAll("Devoir 1", "Devoir 2");
                suggestion_matiere.setItems(matieres);

            });
        }).start();

        colonne_nom_matiere.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("nom_matiere"));
        colonne_note1.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("note1"));
        colonne_note2.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("note2"));
        colonne_session.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("session"));
    }

    @FXML
    void ajouter_note(ActionEvent event) {

        try {
            Double note = Double.valueOf(note_obtenu.getText());
            String numeroDevoir = numero_devoir.getValue();
            suggestion_matiere matiere = suggestion_matiere.getValue();
            if (note < 0 || note > 20)
                throw new NumberFormatException();

            if (numeroDevoir == null || matiere == null) {
                Fonctions.Alerte_erreur("Veuillez sélectionner le devoir et la matière.");
                return;
            }
            String session = checbox_session_rattrapage.isSelected() ? "RATTRAPAGE" : "NORMAL";

            if (Fonctions.ajouter_note(ControleurFenetreListeEtudiant.data.getId_etudiant(),
                    matiere.getId_matiere(), note, numeroDevoir, session)) {
                note_obtenu.clear();
                numero_devoir.setValue(null);
                suggestion_matiere.setValue(null);
                reload_tableau_note();

            } else {
                Fonctions.Alerte_erreur("Erreur d'ajout de la note.");
            }
        } catch (Exception e) {
            Fonctions.Alerte_erreur("La note doit être un nombre entre 0 et 20.");
        }

    }

    @FXML
    void fermer_fenetre(ActionEvent event) {
        ControleurFenetreListeEtudiant.FenetreNotesEtudiant.close();
    }

    private void reload_tableau_note() {
        new Thread(() -> {
            var notes = Fonctions.lister_notes_etudiant(ControleurFenetreListeEtudiant.data.getId_etudiant(),
                    ControleursFenetreClasse.data.getId_classe(), ControleurFenetreListeEtudiant.semestre);
            Platform.runLater(() -> {
                tableau_note.setItems(notes);
            });
        }).start();
    }

}
