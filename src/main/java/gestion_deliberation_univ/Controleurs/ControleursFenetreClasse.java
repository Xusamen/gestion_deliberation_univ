package gestion_deliberation_univ.Controleurs;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import gestion_deliberation_univ.App;
import gestion_deliberation_univ.Modeles.Fonctions;
import gestion_deliberation_univ.Modeles.StructuresDonnees.structure_classe;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
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

    @FXML
    private Text nom_filiere;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        colonne_annee_univ.setCellValueFactory(new PropertyValueFactory<>("Annee_univ"));
        colonne_niveau_classe.setCellValueFactory(new PropertyValueFactory<>("Niveau_classe"));
        colonne_effectif_classe.setCellValueFactory(new PropertyValueFactory<>("Effectif"));
        bouton_action_classe();
        tableau_classe.setItems(Fonctions.lister_classe(ControleursFenetreAcceuil.data.getId_filiere()));

        // initialisation des choicebox & autres composants
        nom_filiere.setText(ControleursFenetreAcceuil.data.getNom_filiere());
        choicebox_annee_univ.getItems().addAll(Fonctions.suggestion_annee_universitaire());
        choicebox_classe.getItems().addAll("Licence_1", "Licence_2", "Licence_3", "Master_1", "Master_2");
    }

    public void reload() {
        tableau_classe.getItems().clear();
        tableau_classe.setItems(Fonctions.lister_classe(ControleursFenetreAcceuil.data.getId_filiere()));
    }

    @FXML
    void ajouter_etudiant(ActionEvent event) {
        String nom_etudiant = champ_nom_etudiant.getText();
        String prenom_etudiant = champ_prenom_etudiant.getText();
        String matricule_etudiant = champ_matricule_etudiant.getText();
        String annee_univ = choicebox_annee_univ.getValue();
        String niveau_classe = choicebox_classe.getValue();
        int idFiliere = ControleursFenetreAcceuil.data.getId_filiere();
        if (nom_etudiant.isEmpty() || prenom_etudiant.isEmpty() || matricule_etudiant.isEmpty()
                || annee_univ == null || niveau_classe == null) {
            Fonctions.Alerte_erreur("Tous les champs doivent etre remplis.");
        } else {
            if (Fonctions.ajouter_etudiant(nom_etudiant, prenom_etudiant, matricule_etudiant, niveau_classe,
                    annee_univ, idFiliere)) {
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
        try {
            App.setRoot("/gestion_deliberation_univ/views/FenetreAcceuil");
        } catch (IOException erreur) {
            System.err.println(erreur.getMessage());
        }
    }

    private static Stage FenetreListeEtudiant;

    public static void FermerFenetreListeEtudiant() {
        FenetreListeEtudiant.close();
    }

    private static Stage FenetreMatiereLicence1;

    public static void FermerFenetreMatiereLicence1() {
        FenetreMatiereLicence1.close();
    }

    private static Stage FenetreMatiereLicence2;

    public static void FermerFenetreMatiereLicence2() {
        FenetreMatiereLicence2.close();
    }

    private static Stage FenetreMatiereLicence3;

    public static void FermerFenetreMatiereLicence3() {
        FenetreMatiereLicence3.close();
    }

    private static Stage FenetreMatiereMaster1;

    public static void FermerFenetreMatiereMaster1() {
        FenetreMatiereMaster1.close();
    }

    private static Stage FenetreMatiereMaster2;

    public static void FermerFenetreMatiereMaster2() {
        FenetreMatiereMaster2.close();
    }

    public static structure_classe data;

    private void bouton_action_classe() {
        Callback<TableColumn<structure_classe, Void>, TableCell<structure_classe, Void>> cellFactory = new Callback<TableColumn<structure_classe, Void>, TableCell<structure_classe, Void>>() {
            @Override
            public TableCell<structure_classe, Void> call(final TableColumn<structure_classe, Void> param) {
                final TableCell<structure_classe, Void> cell = new TableCell<structure_classe, Void>() {
                    private final Button boutonConsulterClasse = new Button("Liste Etudiants");
                    private final Button boutonSupprimerClasse = new Button("Supprimer Classe");
                    private final Button boutonDefinirMatiere = new Button("Matieres");

                    {
                        boutonConsulterClasse.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white;");
                        boutonDefinirMatiere.setStyle("-fx-background-color: #FF9800; -fx-text-fill: white;");
                        boutonSupprimerClasse.setStyle("-fx-background-color: #f44336; -fx-text-fill: white;");
                    }
                    {
                        boutonConsulterClasse.setOnAction(event -> {
                            data = getTableView().getItems().get(getIndex());
                            try {
                                Image icone = new Image(
                                        getClass()
                                                .getResourceAsStream("/gestion_deliberation_univ/icones/etudiant.png"));
                                FXMLLoader loader = new FXMLLoader(
                                        getClass().getResource(
                                                "/gestion_deliberation_univ/views/FenetreListeEtudiant.fxml"));
                                Parent root = loader.load();
                                @SuppressWarnings("unused")
                                ControleurFenetreListeEtudiant controleur = loader.getController();
                                FenetreListeEtudiant = new Stage();
                                FenetreListeEtudiant.getIcons().add(icone);
                                FenetreListeEtudiant.setResizable(false);
                                FenetreListeEtudiant.initModality(Modality.APPLICATION_MODAL); // Modality.WINDOW_MODAL
                                FenetreListeEtudiant
                                        .initOwner(((javafx.scene.Node) event.getSource()).getScene().getWindow());
                                Scene scene = new Scene(root);
                                ;
                                scene.setFill(Color.TRANSPARENT);
                                FenetreListeEtudiant.setScene(scene);
                                FenetreListeEtudiant.showAndWait();

                            } catch (IOException erreur) {
                                System.err.println(erreur.getMessage());
                            }

                        });

                        boutonSupprimerClasse.setOnAction(event -> {
                            data = getTableView().getItems().get(getIndex());
                            if (Fonctions.supprimer_classe(ControleursFenetreClasse.data.getId_classe())) {
                                Fonctions.Alerte_succes("Classe supprimee avec succes.");
                                reload();
                            } else {
                                Fonctions.Alerte_erreur("Echec de la suppression de la classe.");
                            }
                        });

                        boutonDefinirMatiere.setOnAction(event -> {
                            data = getTableView().getItems().get(getIndex());
                            switch (data.getNiveau_classe()) {
                                case "Licence_1":
                                    try {
                                        Image icone = new Image(
                                                getClass().getResourceAsStream(
                                                        "/gestion_deliberation_univ/icones/livre.png"));
                                        FXMLLoader loader = new FXMLLoader(
                                                getClass().getResource(
                                                        "/gestion_deliberation_univ/views/FenetreMatiereLicence1.fxml"));
                                        Parent root = loader.load();
                                        @SuppressWarnings("unused")
                                        ControleurFenetreMatiereLicence1 controleur = loader.getController();
                                        FenetreMatiereLicence1 = new Stage();
                                        FenetreMatiereLicence1.getIcons().add(icone);
                                        FenetreMatiereLicence1.setResizable(false);
                                        FenetreMatiereLicence1.initModality(Modality.APPLICATION_MODAL); // Modality.WINDOW_MODAL
                                        FenetreMatiereLicence1
                                                .initOwner(
                                                        ((javafx.scene.Node) event.getSource()).getScene().getWindow());
                                        Scene scene = new Scene(root);
                                        scene.setFill(Color.TRANSPARENT);
                                        FenetreMatiereLicence1.setScene(scene);
                                        FenetreMatiereLicence1.showAndWait();

                                    } catch (IOException erreur) {
                                        System.err.println(erreur.getMessage());
                                    }

                                    break;
                                case "Licence_2":
                                    try {
                                        Image icone = new Image(
                                                getClass().getResourceAsStream(
                                                        "/gestion_deliberation_univ/icones/livre.png"));
                                        FXMLLoader loader = new FXMLLoader(
                                                getClass().getResource(
                                                        "/gestion_deliberation_univ/views/FenetreMatiereLicence2.fxml"));
                                        Parent root = loader.load();
                                        @SuppressWarnings("unused")
                                        ControleurFenetreMatiereLicence2 controleur = loader.getController();
                                        FenetreMatiereLicence2 = new Stage();
                                        FenetreMatiereLicence2.getIcons().add(icone);
                                        FenetreMatiereLicence2.setResizable(false);
                                        FenetreMatiereLicence2.initModality(Modality.APPLICATION_MODAL); // Modality.WINDOW_MODAL
                                        FenetreMatiereLicence2
                                                .initOwner(
                                                        ((javafx.scene.Node) event.getSource()).getScene().getWindow());
                                        Scene scene = new Scene(root);
                                        scene.setFill(Color.TRANSPARENT);
                                        FenetreMatiereLicence2.setScene(scene);
                                        FenetreMatiereLicence2.showAndWait();

                                    } catch (IOException erreur) {
                                        System.err.println(erreur.getMessage());
                                    }
                                    break;
                                case "Licence_3":
                                    try {
                                        Image icone = new Image(
                                                getClass().getResourceAsStream(
                                                        "/gestion_deliberation_univ/icones/livre.png"));
                                        FXMLLoader loader = new FXMLLoader(
                                                getClass().getResource(
                                                        "/gestion_deliberation_univ/views/FenetreMatiereLicence3.fxml"));
                                        Parent root = loader.load();
                                        @SuppressWarnings("unused")
                                        ControleurFenetreMatiereLicence3 controleur = loader.getController();
                                        FenetreMatiereLicence3 = new Stage();
                                        FenetreMatiereLicence3.getIcons().add(icone);
                                        FenetreMatiereLicence3.setResizable(false);
                                        FenetreMatiereLicence3.initModality(Modality.APPLICATION_MODAL); // Modality.WINDOW_MODAL
                                        FenetreMatiereLicence3
                                                .initOwner(
                                                        ((javafx.scene.Node) event.getSource()).getScene().getWindow());
                                        Scene scene = new Scene(root);
                                        scene.setFill(Color.TRANSPARENT);
                                        FenetreMatiereLicence3.setScene(scene);
                                        FenetreMatiereLicence3.showAndWait();

                                    } catch (IOException erreur) {
                                        System.err.println(erreur.getMessage());
                                    }
                                    break;
                                case "Master_1":
                                    try {
                                        Image icone = new Image(
                                                getClass().getResourceAsStream(
                                                        "/gestion_deliberation_univ/icones/livre.png"));
                                        FXMLLoader loader = new FXMLLoader(
                                                getClass().getResource(
                                                        "/gestion_deliberation_univ/views/FenetreMatiereMaster1.fxml"));
                                        Parent root = loader.load();
                                        @SuppressWarnings("unused")
                                        ControleurFenetreMatiereMaster1 controleur = loader.getController();
                                        FenetreMatiereMaster1 = new Stage();
                                        FenetreMatiereMaster1.getIcons().add(icone);
                                        FenetreMatiereMaster1.setResizable(false);
                                        FenetreMatiereMaster1.initModality(Modality.APPLICATION_MODAL); // Modality.WINDOW_MODAL
                                        FenetreMatiereMaster1
                                                .initOwner(
                                                        ((javafx.scene.Node) event.getSource()).getScene().getWindow());
                                        Scene scene = new Scene(root);
                                        scene.setFill(Color.TRANSPARENT);
                                        FenetreMatiereMaster1.setScene(scene);
                                        FenetreMatiereMaster1.showAndWait();

                                    } catch (IOException erreur) {
                                        System.err.println(erreur.getMessage());
                                    }
                                    break;
                                case "Master_2":
                                    try {
                                        Image icone = new Image(
                                                getClass().getResourceAsStream(
                                                        "/gestion_deliberation_univ/icones/livre.png"));
                                        FXMLLoader loader = new FXMLLoader(
                                                getClass().getResource(
                                                        "/gestion_deliberation_univ/views/FenetreMatiereMaster2.fxml"));
                                        Parent root = loader.load();
                                        @SuppressWarnings("unused")
                                        ControleurFenetreMatiereMaster2 controleur = loader.getController();
                                        FenetreMatiereMaster2 = new Stage();
                                        FenetreMatiereMaster2.getIcons().add(icone);
                                        FenetreMatiereMaster2.setResizable(false);
                                        FenetreMatiereMaster2.initModality(Modality.APPLICATION_MODAL); // Modality.WINDOW_MODAL
                                        FenetreMatiereMaster2
                                                .initOwner(
                                                        ((javafx.scene.Node) event.getSource()).getScene().getWindow());
                                        Scene scene = new Scene(root);
                                        scene.setFill(Color.TRANSPARENT);
                                        FenetreMatiereMaster2.setScene(scene);
                                        FenetreMatiereMaster2.showAndWait();

                                    } catch (IOException erreur) {
                                        System.err.println(erreur.getMessage());
                                    }
                                    break;

                                default:
                                    break;
                            }

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
