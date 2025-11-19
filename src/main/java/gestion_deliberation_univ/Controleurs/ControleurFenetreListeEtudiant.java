package gestion_deliberation_univ.Controleurs;

import java.net.URL;
import java.util.ResourceBundle;

import gestion_deliberation_univ.Modeles.Fonctions;

import gestion_deliberation_univ.Modeles.StructuresDonnees.structure_etudiant;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.util.Callback;

public class ControleurFenetreListeEtudiant implements Initializable {
    @FXML
    private TableColumn<structure_etudiant, Void> colonne_action;

    @FXML
    private TableColumn<structure_etudiant, String> colonne_matricule;

    @FXML
    private TableColumn<structure_etudiant, String> colonne_nom_prenoms;

    @FXML
    private Text niveau_classe;

    @FXML
    private Text annee_universitaire;

    @FXML
    private TableView<structure_etudiant> tableau_liste_etudiant;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        colonne_nom_prenoms.setCellValueFactory(new PropertyValueFactory<>("Nom_prenom"));
        colonne_matricule.setCellValueFactory(new PropertyValueFactory<>("Matricule"));
        bouton_action_liste_etudiant();
        tableau_liste_etudiant.setItems(Fonctions.lister_etudiant(ControleursFenetreClasse.data.getId()));

        // Afficher les informations de la classe
        annee_universitaire.setText("Année Universitaire: " + ControleursFenetreClasse.data.getAnnee_univ());
        niveau_classe.setText("Niveau: " + ControleursFenetreClasse.data.getNiveau_classe());

    }

    @FXML
    void fermer_fenetreListeEtudiant(ActionEvent event) {
        ControleursFenetreClasse.FermerFenetreListeEtudiant();
    }

    public static structure_etudiant data;

    private void bouton_action_liste_etudiant() {
        Callback<TableColumn<structure_etudiant, Void>, TableCell<structure_etudiant, Void>> cellFactory = new Callback<TableColumn<structure_etudiant, Void>, TableCell<structure_etudiant, Void>>() {
            @Override
            public TableCell<structure_etudiant, Void> call(final TableColumn<structure_etudiant, Void> param) {
                final TableCell<structure_etudiant, Void> cell = new TableCell<structure_etudiant, Void>() {
                    private final Button boutonReportNoteSemestre_1 = new Button("Notes S1");
                    private final Button boutonReportNoteSemestre_2 = new Button("Notes S2");
                    private final Button boutonRecapNote = new Button("Récapitulatif");
                    private final Button boutonSupprimerEtudiant = new Button("Supprimer");



                    {
                        boutonReportNoteSemestre_1.setOnAction(event -> {
                            data = getTableView().getItems().get(getIndex());

                        });

                        boutonReportNoteSemestre_2.setOnAction(event -> {
                            data = getTableView().getItems().get(getIndex());

                        });

                        boutonRecapNote.setOnAction(event -> {
                            data = getTableView().getItems().get(getIndex());

                        });

                        boutonSupprimerEtudiant.setOnAction(event -> {
                            data = getTableView().getItems().get(getIndex());

                        });

                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            HBox box = new HBox(10, boutonReportNoteSemestre_1, boutonReportNoteSemestre_2, boutonRecapNote, boutonSupprimerEtudiant);
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
