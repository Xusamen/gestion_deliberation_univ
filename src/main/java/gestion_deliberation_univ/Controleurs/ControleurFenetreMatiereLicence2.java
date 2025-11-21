package gestion_deliberation_univ.Controleurs;

import java.net.URL;
import java.util.ResourceBundle;

import gestion_deliberation_univ.Modeles.Fonctions;
import gestion_deliberation_univ.Modeles.StructuresDonnees.structure_matiere;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

public class ControleurFenetreMatiereLicence2 implements Initializable {
    @FXML
    private TextField coef_matiere_s3;

    @FXML
    private TextField coef_matiere_s4;

    @FXML
    private TableColumn<structure_matiere, Void> colonne_actions_s3;

    @FXML
    private TableColumn<structure_matiere, Void> colonne_actions_s4;

    @FXML
    private TableColumn<structure_matiere, Double> colonne_coef_s3;

    @FXML
    private TableColumn<structure_matiere, Double> colonne_coef_s4;

    @FXML
    private TableColumn<structure_matiere, String> colonne_matiere_s3;

    @FXML
    private TableColumn<structure_matiere, String> colonne_matiere_s4;

    @FXML
    private TableColumn<structure_matiere, String> colonne_ue_s3;

    @FXML
    private TableColumn<structure_matiere, String> colonne_ue_s4;

    @FXML
    private TextField nom_matiere_s3;

    @FXML
    private TextField nom_matiere_s4;

    @FXML
    private TableView<structure_matiere> tableau_matiere_s3;

    @FXML
    private TableView<structure_matiere> tableau_matiere_s4;

    @FXML
    private TextField ue_matiere_s3;

    @FXML
    private TextField ue_matiere_s4;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        colonne_matiere_s3.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("nom_matiere"));
        colonne_ue_s3.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("unite_enseignement"));
        colonne_coef_s3.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("coefficient"));
        bouton_action_matiere_licence2_s3();
        
        colonne_matiere_s4.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("nom_matiere"));
        colonne_ue_s4.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("unite_enseignement"));
        colonne_coef_s4.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("coefficient"));
        bouton_action_matiere_licence2_s4();


        int id = ControleursFenetreClasse.data.getId_classe();

        new Thread(() -> {
            var liste1 = Fonctions.lister_matieres(id, "S3");
            Platform.runLater(() -> tableau_matiere_s3.setItems(liste1));
        }).start();

        new Thread(() -> {
            var liste2 = Fonctions.lister_matieres(id, "S4");
            Platform.runLater(() -> tableau_matiere_s4.setItems(liste2));
        }).start();
    }

    @FXML
    void ajouter_matiere_s3(ActionEvent event) {
        String ue = ue_matiere_s3.getText();
        String nom = nom_matiere_s3.getText();
        String coef = coef_matiere_s3.getText();
        int id_classe = ControleursFenetreClasse.data.getId_classe();
        if (Fonctions.ajouter_matiere(ue, nom, coef, id_classe, "S3")) {
            Fonctions.Alerte_succes("Matiere ajoutee avec succes.");
            ue_matiere_s3.clear();
            nom_matiere_s3.clear();
            coef_matiere_s3.clear();
            actualisationTableView(tableau_matiere_s3, id_classe, "S3");
        } else {
            Fonctions.Alerte_erreur("Echec de l'ajout de la matiere.");
        }
    }

    @FXML
    void ajouter_matiere_s4(ActionEvent event) {
        String ue = ue_matiere_s4.getText();
        String nom = nom_matiere_s4.getText();
        String coef = coef_matiere_s4.getText();
        int id_classe = ControleursFenetreClasse.data.getId_classe();
        if (Fonctions.ajouter_matiere(ue, nom, coef, id_classe, "S4")) {
            Fonctions.Alerte_succes("Matiere ajoutee avec succes.");
            ue_matiere_s4.clear();
            nom_matiere_s4.clear();
            coef_matiere_s4.clear();
            actualisationTableView(tableau_matiere_s4, id_classe, "S4");
        } else {
            Fonctions.Alerte_erreur("Echec de l'ajout de la matiere.");
        }
    }

    @FXML
    void fermer_fenetre_matiere_l2(ActionEvent event) {
        ControleursFenetreClasse.FermerFenetreMatiereLicence2();
    }

    public static structure_matiere data;

    private void bouton_action_matiere_licence2_s3() {
        Callback<TableColumn<structure_matiere, Void>, TableCell<structure_matiere, Void>> cellFactory = new Callback<TableColumn<structure_matiere, Void>, TableCell<structure_matiere, Void>>() {
            @Override
            public TableCell<structure_matiere, Void> call(final TableColumn<structure_matiere, Void> param) {
                final TableCell<structure_matiere, Void> cell = new TableCell<structure_matiere, Void>() {
                    private final Button boutonModifier = new Button("Modifier");
                    private final Button boutonSupprimer = new Button("Supprimer");

                    {
                        boutonModifier.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white;");
                        boutonSupprimer.setStyle("-fx-background-color: #f44336; -fx-text-fill: white;");
                    }

                    {
                        boutonModifier.setOnAction(event -> {
                            data = getTableView().getItems().get(getIndex());

                        });

                        boutonModifier.setOnAction(event -> {
                            data = getTableView().getItems().get(getIndex());

                        });

                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            HBox box = new HBox(5, boutonModifier, boutonSupprimer);
                            box.setAlignment(Pos.CENTER);
                            setGraphic(box);
                        }
                    }
                };
                return cell;
            }
        };
        colonne_actions_s3.setCellFactory(cellFactory);

    }

    private void bouton_action_matiere_licence2_s4() {
        Callback<TableColumn<structure_matiere, Void>, TableCell<structure_matiere, Void>> cellFactory = new Callback<TableColumn<structure_matiere, Void>, TableCell<structure_matiere, Void>>() {
            @Override
            public TableCell<structure_matiere, Void> call(final TableColumn<structure_matiere, Void> param) {
                final TableCell<structure_matiere, Void> cell = new TableCell<structure_matiere, Void>() {
                    private final Button boutonModifier = new Button("Modifier");
                    private final Button boutonSupprimer = new Button("Supprimer");

                    {
                        boutonModifier.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white;");
                        boutonSupprimer.setStyle("-fx-background-color: #f44336; -fx-text-fill: white;");
                    }
                    {
                        boutonModifier.setOnAction(event -> {
                            data = getTableView().getItems().get(getIndex());

                        });

                        boutonModifier.setOnAction(event -> {
                            data = getTableView().getItems().get(getIndex());

                        });

                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            HBox box = new HBox(5, boutonModifier, boutonSupprimer);
                            box.setAlignment(Pos.CENTER);
                            setGraphic(box);
                        }
                    }
                };
                return cell;
            }
        };
        colonne_actions_s4.setCellFactory(cellFactory);
    }

    private void actualisationTableView(TableView<structure_matiere> table, int idClasse, String semestre) {
        new Thread(() -> {
            var liste = Fonctions.lister_matieres(idClasse, semestre);
            Platform.runLater(() -> table.setItems(liste));
        }).start();
    }

}
