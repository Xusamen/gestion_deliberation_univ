package gestion_deliberation_univ.Controleurs;

import java.net.URL;
import java.util.ResourceBundle;

import gestion_deliberation_univ.Modeles.Fonctions;
import gestion_deliberation_univ.Modeles.StructuresDonnees.structure_matiere;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

public class ControleurFenetreMatiereMaster1 implements javafx.fxml.Initializable {
    @FXML
    private TextField coef_matiere_s1;

    @FXML
    private TextField coef_matiere_s2;

    @FXML
    private TableColumn<structure_matiere, Void> colonne_actions_s1;

    @FXML
    private TableColumn<structure_matiere, Void> colonne_actions_s2;

    @FXML
    private TableColumn<structure_matiere, Double> colonne_coef_s1;

    @FXML
    private TableColumn<structure_matiere, Double> colonne_coef_s2;

    @FXML
    private TableColumn<structure_matiere, String> colonne_matiere_s1;

    @FXML
    private TableColumn<structure_matiere, String> colonne_matiere_s2;

    @FXML
    private TableColumn<structure_matiere, String> colonne_ue_s1;

    @FXML
    private TableColumn<structure_matiere, String> colonne_ue_s2;

    @FXML
    private TextField nom_matiere_s1;

    @FXML
    private TextField nom_matiere_s2;

    @FXML
    private TableView<structure_matiere> tableau_matiere_s1;

    @FXML
    private TableView<structure_matiere> tableau_matiere_s2;

    @FXML
    private TextField ue_matiere_s1;

    @FXML
    private TextField ue_matiere_s2;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        colonne_ue_s1.setCellValueFactory(new PropertyValueFactory<>("unite_enseignement"));
        colonne_matiere_s1.setCellValueFactory(new PropertyValueFactory<>("nom_matiere"));
        colonne_coef_s1.setCellValueFactory(new PropertyValueFactory<>("coefficient"));
        bouton_action_matiere_master1_s1();

        colonne_ue_s2.setCellValueFactory(new PropertyValueFactory<>("unite_enseignement"));
        colonne_matiere_s2.setCellValueFactory(new PropertyValueFactory<>("nom_matiere"));
        colonne_coef_s2.setCellValueFactory(new PropertyValueFactory<>("coefficient"));
        bouton_action_matiere_master1_s2();

        int id = ControleursFenetreClasse.data.getId_classe();

        new Thread(() -> {
            var liste1 = Fonctions.lister_matieres(id, "S1");
            Platform.runLater(() -> tableau_matiere_s1.setItems(liste1));
        }).start();

        new Thread(() -> {
            var liste2 = Fonctions.lister_matieres(id, "S2");
            Platform.runLater(() -> tableau_matiere_s2.setItems(liste2));
        }).start();
    }

    @FXML
    void ajouter_matiere_s1(ActionEvent event) {
        String ue = ue_matiere_s1.getText();
        String nom = nom_matiere_s1.getText();
        String coef = coef_matiere_s1.getText();
        int id_classe = ControleursFenetreClasse.data.getId_classe();
        if (Fonctions.ajouter_matiere(ue, nom, coef, id_classe, "S1")) {
            Fonctions.Alerte_succes("Matiere ajoutee avec succes.");
            ue_matiere_s1.clear();
            nom_matiere_s1.clear();
            coef_matiere_s1.clear();
            actualisationTableView(tableau_matiere_s1, id_classe, "S1");
        } else {
            Fonctions.Alerte_erreur("Echec de l'ajout de la matiere.");
        }

    }

    @FXML
    void ajouter_matiere_s2(ActionEvent event) {
        String ue = ue_matiere_s2.getText();
        String nom = nom_matiere_s2.getText();
        String coef = coef_matiere_s2.getText();
        int id_classe = ControleursFenetreClasse.data.getId_classe();
        if (Fonctions.ajouter_matiere(ue, nom, coef, id_classe, "S2")) {
            Fonctions.Alerte_succes("Matiere ajoutee avec succes.");
            ue_matiere_s2.clear();
            nom_matiere_s2.clear();
            coef_matiere_s2.clear();
            actualisationTableView(tableau_matiere_s2, id_classe, "S2");
        } else {
            Fonctions.Alerte_erreur("Echec de l'ajout de la matiere.");
        }

    }

    @FXML
    void fermer_fenetre_matiere_m1(ActionEvent event) {
        ControleursFenetreClasse.FermerFenetreMatiereMaster1();
    }

    public static structure_matiere data;

    private void bouton_action_matiere_master1_s2() {
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
        colonne_actions_s2.setCellFactory(cellFactory);

    }

    private void bouton_action_matiere_master1_s1() {
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
        colonne_actions_s1.setCellFactory(cellFactory);

    }

    private void actualisationTableView(TableView<structure_matiere> table, int idClasse, String semestre) {
        new Thread(() -> {
            var liste = Fonctions.lister_matieres(idClasse, semestre);
            Platform.runLater(() -> table.setItems(liste));
        }).start();
    }

}
