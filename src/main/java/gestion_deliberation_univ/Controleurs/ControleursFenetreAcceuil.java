package gestion_deliberation_univ.Controleurs;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import gestion_deliberation_univ.Modeles.Fonctions;
import gestion_deliberation_univ.Modeles.StructuresDonnees.structure_filiere;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;

public class ControleursFenetreAcceuil implements Initializable {

    @FXML
    private StackPane conteneur_de_fenetre;

    @FXML
    private TableColumn<structure_filiere, Void> cellule_action;

    @FXML
    private TableColumn<structure_filiere, String> cellule_nom_filiere;

    @FXML
    private TextField champ_nom_filiere;

    @FXML
    private TextField champ_rechercher_nom_filiere;

    @FXML
    private TableView<structure_filiere> tableau_filiere;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        cellule_nom_filiere.setCellValueFactory(new PropertyValueFactory<>("Nom_filiere"));
        bouton_action_filiere();
        tableau_filiere.setItems(Fonctions.lister_filiere());
    }

    @FXML
    void creer_filiere(ActionEvent event) {
        String nomFiliere = champ_nom_filiere.getText();
        if (!nomFiliere.isEmpty()) {
            gestion_deliberation_univ.Modeles.Fonctions.createFiliere(nomFiliere);
            champ_nom_filiere.clear();
            initialize(null, null);
        } else {
            Fonctions.Alerte_erreur("Le nom de la filiere ne peut pas etre vide.");
        }
    }

    @FXML
    void rechercher_filiere(ActionEvent event) {
        String nomFiliere = champ_rechercher_nom_filiere.getText();
        tableau_filiere.setItems(Fonctions.rechercher_filiere(nomFiliere));
        champ_rechercher_nom_filiere.clear();
    }

    private static Stage FenetreAjoutClasse;

    public static void FermerFenetreAjoutClasse() {
        FenetreAjoutClasse.close();
    }

    public static structure_filiere data;

    private void bouton_action_filiere() {
        Callback<TableColumn<structure_filiere, Void>, TableCell<structure_filiere, Void>> cellFactory = new Callback<TableColumn<structure_filiere, Void>, TableCell<structure_filiere, Void>>() {
            @Override
            public TableCell<structure_filiere, Void> call(final TableColumn<structure_filiere, Void> param) {
                final TableCell<structure_filiere, Void> cell = new TableCell<structure_filiere, Void>() {
                    private final Button boutonConsulterClasse = new Button("Les Classes");
                    private final Button boutonSupprimerFiliere = new Button("Supprimer Filiere");
                    private final Button boutonAjouterClasse = new Button("Ajouter une classe");

                    {
                        boutonConsulterClasse.setOnAction(event -> {
                            data = getTableView().getItems().get(getIndex());
                            Pane vueDashboard;
                            try {
                                vueDashboard = FXMLLoader.load(
                                        getClass().getResource("/gestion_deliberation_univ/views/fenetre_classe.fxml"));
                                ChangerContenu_StackPane(vueDashboard);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        });

                        boutonSupprimerFiliere.setOnAction(event -> {
                            data = getTableView().getItems().get(getIndex());
                            Fonctions.supprimer_filiere(data.getId());
                            Fonctions.Alerte_succes("Filiere supprimee avec succes.");
                            initialize(null, null);
                        });

                        boutonAjouterClasse.setOnAction(event -> {
                            data = getTableView().getItems().get(getIndex());
                            try {
                                Image icone = new Image(
                                        getClass().getResourceAsStream("/gestion_deliberation_univ/icones/ajout.png"));
                                FXMLLoader loader = new FXMLLoader(
                                        getClass().getResource("/gestion_deliberation_univ/views/FenetreAjoutClasse.fxml"));
                                Parent root = loader.load();
                                @SuppressWarnings("unused")
                                ControleurFenetreAjoutClasse controleur = loader.getController();
                                FenetreAjoutClasse = new Stage();
                                FenetreAjoutClasse.getIcons().add(icone);
                                FenetreAjoutClasse.setResizable(false);
                                FenetreAjoutClasse.initModality(Modality.APPLICATION_MODAL); // Modality.WINDOW_MODAL
                                FenetreAjoutClasse
                                        .initOwner(((javafx.scene.Node) event.getSource()).getScene().getWindow());
                                Scene scene = new Scene(root);
                                scene.setFill(Color.TRANSPARENT);
                                FenetreAjoutClasse.setScene(scene);
                                FenetreAjoutClasse.showAndWait();

                            } catch (IOException erreur) {
                                System.err.println(erreur.getMessage());
                            }
                        });

                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            HBox box = new HBox(10, boutonConsulterClasse, boutonSupprimerFiliere, boutonAjouterClasse);
                            box.setAlignment(Pos.CENTER);
                            setGraphic(box);
                        }
                    }
                };
                return cell;
            }
        };
        cellule_action.setCellFactory(cellFactory);
    }

    public void ChangerContenu_StackPane(Pane nouveauContenu) {

        if (!conteneur_de_fenetre.getChildren().isEmpty()) {
            Node ancien = conteneur_de_fenetre.getChildren().get(0);

            FadeTransition fadeOut = new FadeTransition(Duration.millis(200), ancien);
            fadeOut.setFromValue(1);
            fadeOut.setToValue(0);

            fadeOut.setOnFinished(event -> {
                conteneur_de_fenetre.getChildren().setAll(nouveauContenu);

                FadeTransition fadeIn = new FadeTransition(Duration.millis(200), nouveauContenu);
                fadeIn.setFromValue(0);
                fadeIn.setToValue(1);
                fadeIn.play();
            });

            fadeOut.play();
        } else {
            conteneur_de_fenetre.getChildren().add(nouveauContenu);
        }
    }

}
