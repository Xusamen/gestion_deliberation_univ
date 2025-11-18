package gestion_deliberation_univ.Controleurs;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Function;

import gestion_deliberation_univ.Modeles.Fonctions;
import gestion_deliberation_univ.Modeles.StructuresDonnees.structure_classe;
import gestion_deliberation_univ.Modeles.StructuresDonnees.structure_filiere;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.util.Callback;

public class ControleursFenetreClasse implements Initializable {
    @FXML
    private TextField champ_matricule_etudiant;

    @FXML
    private TextField champ_nom_etudiant;

    @FXML
    private TextField champ_prenom_etudiant;

    @FXML
    private ChoiceBox<String> choicebox_annee_univ;

    @FXML
    private ChoiceBox<String> choicebox_classe;

    @FXML
    private TableColumn<structure_classe, Void> colonne_action;

    @FXML
    private TableColumn<structure_classe, String> colonne_annee_univ;

    @FXML
    private TableColumn<structure_classe, String> colonne_niveau_classe;

    @FXML
    private TableColumn<structure_classe, String> colonne_effectif_classe;

    @FXML
    private TableView<structure_classe> tableau_classe;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        colonne_annee_univ.setCellValueFactory(new PropertyValueFactory<>("Annee_univ"));
        colonne_niveau_classe.setCellValueFactory(new PropertyValueFactory<>("Niveau_classe"));
        colonne_effectif_classe.setCellValueFactory(new PropertyValueFactory<>("Effectif"));
        bouton_action_classe();
        tableau_classe.setItems(Fonctions.lister_classe());

        // initialisation des choicebox
        choicebox_annee_univ.getItems().addAll(Fonctions.suggestion_annee_universitaire());
        choicebox_classe.getItems().addAll("Licence_1", "Licence_2", "Licence_3", "Master_1", "Master_2");
    }

    public void reload() {
        tableau_classe.getItems().clear();
        tableau_classe.setItems(Fonctions.lister_classe());
    }

    @FXML
    void ajouter_etudiant(ActionEvent event) {
        String nom_etudiant = champ_nom_etudiant.getText();
        String prenom_etudiant = champ_prenom_etudiant.getText();
        String matricule_etudiant = champ_matricule_etudiant.getText();
        String annee_univ = choicebox_annee_univ.getValue();
        String niveau_classe = choicebox_classe.getValue();
        if (nom_etudiant.isEmpty() || prenom_etudiant.isEmpty() || matricule_etudiant.isEmpty()
                || annee_univ == null || niveau_classe == null) {
            Fonctions.Alerte_erreur("Tous les champs doivent etre remplis.");
        } else {
            if (Fonctions.ajouter_etudiant(nom_etudiant, prenom_etudiant, matricule_etudiant, niveau_classe,
                    annee_univ)) {
                Fonctions.Alerte_succes("Etudiant ajoute avec succes.");
                reload();
                champ_nom_etudiant.clear();
                champ_prenom_etudiant.clear();
                champ_matricule_etudiant.clear();
                choicebox_annee_univ.setValue(null);
                choicebox_classe.setValue(null);
            } else {
                Fonctions.Alerte_erreur("Echec de l'ajout de l'etudiant. Verifiez que le matricule n'existe pas deja.");
                return;
            }
        }

    }

    @FXML
    void fermer_fenetre_classe(ActionEvent event) {
    }

    public static structure_classe data;

    private void bouton_action_classe() {
        Callback<TableColumn<structure_classe, Void>, TableCell<structure_classe, Void>> cellFactory = new Callback<TableColumn<structure_classe, Void>, TableCell<structure_classe, Void>>() {
            @Override
            public TableCell<structure_classe, Void> call(final TableColumn<structure_classe, Void> param) {
                final TableCell<structure_classe, Void> cell = new TableCell<structure_classe, Void>() {
                    private final Button boutonConsulterClasse = new Button("Consulter Classe");
                    private final Button boutonSupprimerClasse = new Button("Supprimer Classe");
                    private final Button boutonDefinirMatiere = new Button("Definir Matieres");

                    {
                        boutonConsulterClasse.setOnAction(event -> {
                            data = getTableView().getItems().get(getIndex());

                        });

                        boutonSupprimerClasse.setOnAction(event -> {
                            data = getTableView().getItems().get(getIndex());

                        });

                        boutonDefinirMatiere.setOnAction(event -> {
                            data = getTableView().getItems().get(getIndex());

                        });

                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            HBox box = new HBox(10, boutonConsulterClasse, boutonSupprimerClasse, boutonDefinirMatiere);
                            box.setAlignment(Pos.CENTER);
                            setGraphic(box);
                        }
                    }
                };
                return cell;
            }
        };
        colonne_action.setCellFactory(cellFactory);
    }
}
