package gestion_deliberation_univ.Controleurs;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import gestion_deliberation_univ.Modeles.Fonctions;

import gestion_deliberation_univ.Modeles.StructuresDonnees.structure_etudiant;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
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
        tableau_liste_etudiant.setItems(Fonctions.lister_etudiant(ControleursFenetreClasse.data.getId_classe()));

        // Afficher les informations de la classe
        annee_universitaire.setText("Année Universitaire: " + ControleursFenetreClasse.data.getAnnee_univ());
        niveau_classe.setText("Niveau: " + ControleursFenetreClasse.data.getNiveau_classe());

    }

    @FXML
    void fermer_fenetreListeEtudiant(ActionEvent event) {
        ControleursFenetreClasse.FermerFenetreListeEtudiant();
    }

    public static structure_etudiant data;
    String libelle_bouton_1;
    String libelle_bouton_2;

    public static Stage FenetreNotesEtudiant;

    public static void FenetreNotesEtudiant() {
        FenetreNotesEtudiant.close();
    }

    public static String semestre;

    private void bouton_action_liste_etudiant() {
        switch (ControleursFenetreClasse.data.getNiveau_classe()) {
            case "Licence_1":
                libelle_bouton_1 = "Notes S1";
                libelle_bouton_2 = "Notes S2";
                break;
            case "Licence_2":
                libelle_bouton_1 = "Notes S3";
                libelle_bouton_2 = "Notes S4";
                break;
            case "Licence_3":
                libelle_bouton_1 = "Notes S5";
                libelle_bouton_2 = "Notes S6";
                break;
            case "Master_1":
                libelle_bouton_1 = "Notes S1";
                libelle_bouton_2 = "Notes S2";
                break;
            case "Master_2":
                libelle_bouton_1 = "Notes S3";
                libelle_bouton_2 = "Notes S4";
                break;

            default:
                break;
        }
        Callback<TableColumn<structure_etudiant, Void>, TableCell<structure_etudiant, Void>> cellFactory = new Callback<TableColumn<structure_etudiant, Void>, TableCell<structure_etudiant, Void>>() {
            @Override
            public TableCell<structure_etudiant, Void> call(final TableColumn<structure_etudiant, Void> param) {
                final TableCell<structure_etudiant, Void> cell = new TableCell<structure_etudiant, Void>() {

                    private final Button boutonReportNoteSemestre_1 = new Button(libelle_bouton_1);
                    private final Button boutonReportNoteSemestre_2 = new Button(libelle_bouton_2);
                    private final Button boutonRecapNote = new Button("Relevés de notes");
                    private final Button boutonSupprimerEtudiant = new Button("Supprimer");

                    {
                        boutonReportNoteSemestre_1.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white;");
                        boutonReportNoteSemestre_2.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white;");
                        boutonRecapNote.setStyle("-fx-background-color: #FF9800; -fx-text-fill: white;");
                        boutonSupprimerEtudiant.setStyle("-fx-background-color: #f44336; -fx-text-fill: white;");
                    }

                    {
                        boutonReportNoteSemestre_1.setOnAction(event -> {
                            data = getTableView().getItems().get(getIndex());
                            semestre = libelle_bouton_1;
                            try {
                                Image icone = new Image(
                                        getClass()
                                                .getResourceAsStream("/gestion_deliberation_univ/icones/devoirs.png"));
                                FXMLLoader loader = new FXMLLoader(
                                        getClass().getResource(
                                                "/gestion_deliberation_univ/views/FenetreNotesEtudiant.fxml"));
                                Parent root = loader.load();
                                @SuppressWarnings("unused")
                                ControleurFenetreNotesEtudiant controleur = loader.getController();
                                FenetreNotesEtudiant = new Stage();
                                FenetreNotesEtudiant.getIcons().add(icone);
                                FenetreNotesEtudiant.setResizable(false);
                                FenetreNotesEtudiant.initModality(Modality.APPLICATION_MODAL); // Modality.WINDOW_MODAL
                                FenetreNotesEtudiant
                                        .initOwner(((javafx.scene.Node) event.getSource()).getScene().getWindow());
                                Scene scene = new Scene(root);
                                scene.setFill(Color.TRANSPARENT);
                                FenetreNotesEtudiant.setScene(scene);
                                FenetreNotesEtudiant.showAndWait();

                            } catch (IOException erreur) {
                                System.err.println(erreur.getMessage());
                            }

                        });

                        boutonReportNoteSemestre_2.setOnAction(event -> {
                            data = getTableView().getItems().get(getIndex());
                            semestre = libelle_bouton_2;
                            try {
                                Image icone = new Image(
                                        getClass()
                                                .getResourceAsStream("/gestion_deliberation_univ/icones/devoirs.png"));
                                FXMLLoader loader = new FXMLLoader(
                                        getClass().getResource(
                                                "/gestion_deliberation_univ/views/FenetreNotesEtudiant.fxml"));
                                Parent root = loader.load();
                                @SuppressWarnings("unused")
                                ControleurFenetreNotesEtudiant controleur = loader.getController();
                                FenetreNotesEtudiant = new Stage();
                                FenetreNotesEtudiant.getIcons().add(icone);
                                FenetreNotesEtudiant.setResizable(false);
                                FenetreNotesEtudiant.initModality(Modality.APPLICATION_MODAL); // Modality.WINDOW_MODAL
                                FenetreNotesEtudiant
                                        .initOwner(((javafx.scene.Node) event.getSource()).getScene().getWindow());
                                Scene scene = new Scene(root);
                                scene.setFill(Color.TRANSPARENT);
                                FenetreNotesEtudiant.setScene(scene);
                                FenetreNotesEtudiant.showAndWait();

                            } catch (IOException erreur) {
                                System.err.println(erreur.getMessage());
                            }

                        });

                        boutonRecapNote.setOnAction(event -> {
                            data = getTableView().getItems().get(getIndex());

                        });

                        boutonSupprimerEtudiant.setOnAction(event -> {
                            data = getTableView().getItems().get(getIndex());
                            if (Fonctions.supprimer_etudiant(data.getId_etudiant())) {
                                Fonctions.Alerte_succes("Étudiant supprimé avec succès.");
                                reload();
                            } else {
                                Fonctions
                                        .Alerte_erreur("Une erreur est survenue lors de la suppression de l'étudiant.");
                            }

                        });

                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            HBox box = new HBox(10, boutonReportNoteSemestre_1, boutonReportNoteSemestre_2,
                                    boutonRecapNote, boutonSupprimerEtudiant);
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

    private void reload() {
        tableau_liste_etudiant.setItems(Fonctions.lister_etudiant(ControleursFenetreClasse.data.getId_classe()));
    }

}
