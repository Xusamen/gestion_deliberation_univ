package gestion_deliberation_univ;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    public static final String utilisateur = "root";
    public static final String motDePasse = "souris";
    public static final String url = "jdbc:mysql://localhost:3306/db_deliberation";
    private static Scene scene;

    @Override
    public void start(Stage PremiereFenetre) {
        try {
            scene = new Scene(loadFXML("/gestion_deliberation_univ/views/fenetre_acceuil"));
            Image icone = new Image(getClass().getResourceAsStream("/gestion_deliberation_univ/icones/icone_univ.png"));
            PremiereFenetre.getIcons().add(icone);
            PremiereFenetre.setResizable(false);
            PremiereFenetre.setTitle("Gestion de Délibération Universitaire");
            PremiereFenetre.setScene(scene);
            PremiereFenetre.show();
        } catch (IOException erreur) {
            System.err.println(erreur.getMessage());
        }
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}