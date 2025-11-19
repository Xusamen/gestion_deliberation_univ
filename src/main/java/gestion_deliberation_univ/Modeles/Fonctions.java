package gestion_deliberation_univ.Modeles;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import gestion_deliberation_univ.App;
import gestion_deliberation_univ.Modeles.StructuresDonnees.structure_classe;
import gestion_deliberation_univ.Modeles.StructuresDonnees.structure_etudiant;
import gestion_deliberation_univ.Modeles.StructuresDonnees.structure_filiere;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Fonctions {

    // notification en cas de reussite
    public static void Alerte_erreur(String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // notification en cas de reussite
    public static void Alerte_succes(String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // GESTION DES
    // FILIERES//////////////////////////////////////////////////////////////////////
    // creer une filiere
    public static ObservableList<structure_filiere> createFiliere(String nomFiliere) {
        String requete = "INSERT INTO filiere (nom_filiere) VALUES (?)";
        ObservableList<structure_filiere> liste_filiere = FXCollections.observableArrayList();
        try (Connection connexion = DriverManager.getConnection(App.url, App.utilisateur, App.motDePasse);
                PreparedStatement statement = connexion.prepareStatement(requete)) {

            statement.setString(1, nomFiliere);
            statement.executeUpdate();

        } catch (SQLException erreur) {
            System.err.println(erreur.getMessage());
        }
        return liste_filiere;
    }

    // lister les filieres presentes dans la base de donnees
    public static ObservableList<structure_filiere> lister_filiere() {
        String requete = "SELECT idFiliere, nom_filiere FROM filiere";
        ObservableList<structure_filiere> liste_filiere = FXCollections.observableArrayList();

        try (Connection connexion = DriverManager.getConnection(App.url, App.utilisateur, App.motDePasse);
                PreparedStatement statement = connexion.prepareStatement(requete)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("idFiliere");
                String nom = resultSet.getString("nom_filiere");
                liste_filiere.add(new structure_filiere(id, nom));
            }
        } catch (SQLException erreur) {
            System.err.println(erreur.getMessage());
        }
        return liste_filiere;
    }

    // rechercher une filiere par son nom
    public static ObservableList<structure_filiere> rechercher_filiere(String nomFiliere) {
        String requete = "SELECT idFiliere, nom_filiere FROM filiere WHERE nom_filiere LIKE ?";
        ObservableList<structure_filiere> liste_filiere = FXCollections.observableArrayList();

        try (Connection connexion = DriverManager.getConnection(App.url, App.utilisateur, App.motDePasse);
                PreparedStatement statement = connexion.prepareStatement(requete)) {

            statement.setString(1, "%" + nomFiliere + "%");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("idFiliere");
                String nom = resultSet.getString("nom_filiere");
                liste_filiere.add(new structure_filiere(id, nom));
            }
        } catch (SQLException erreur) {
            System.err.println(erreur.getMessage());
        }
        return liste_filiere;
    }

    // supprimer une filiere
    public static void supprimer_filiere(int id) {
        String requete = "DELETE FROM filiere WHERE idFiliere = ?";

        try (Connection connexion = DriverManager.getConnection(App.url, App.utilisateur, App.motDePasse);
                PreparedStatement statement = connexion.prepareStatement(requete)) {

            statement.setInt(1, id);
            statement.executeUpdate();

        } catch (SQLException erreur) {
            System.err.println(erreur.getMessage());
        }
    }

    // GESTION DES
    // CLASSES//////////////////////////////////////////////////////////////////////
    // lister les classes d'une filiere
    public static ObservableList<structure_classe> lister_classe(int idFilere) {
        String requete = "SELECT idClasse, niveau, idFiliere, annee_universitaire, effectif FROM classe WHERE idFiliere = ?";
        ObservableList<structure_classe> liste_classe = FXCollections.observableArrayList();

        try (Connection connexion = DriverManager.getConnection(App.url, App.utilisateur, App.motDePasse);
                PreparedStatement statement = connexion.prepareStatement(requete)) {
            statement.setInt(1, idFilere);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int idClasse = resultSet.getInt("idClasse");
                String niveau_classe = resultSet.getString("niveau");
                int idFiliere = resultSet.getInt("idFiliere");
                String annee_univ = resultSet.getString("annee_universitaire");
                int effectif = resultSet.getInt("effectif");
                liste_classe.add(new structure_classe(idClasse, niveau_classe, idFiliere, annee_univ, effectif));
            }
        } catch (SQLException erreur) {
            System.err.println(erreur.getMessage());
        }
        return liste_classe;
    }

    // ajouter une classe a une filiere
    public static boolean ajouter_classe(String niveau_classe, String annee_univ, int id) {
        String requete = "INSERT INTO classe (niveau, annee_universitaire, idFiliere) VALUES (?, ?, ?)";

        try (Connection connexion = DriverManager.getConnection(App.url, App.utilisateur, App.motDePasse);
                PreparedStatement statement = connexion.prepareStatement(requete)) {

            statement.setString(1, niveau_classe);
            statement.setString(2, annee_univ);
            statement.setInt(3, id);
            statement.executeUpdate();
            return true;

        } catch (SQLException erreur) {
            System.err.println(erreur.getMessage());
            return false;
        }
    }

    // supprimer une classe
    public static boolean supprimer_classe(int id) {
        String requete = "DELETE FROM classe WHERE idClasse = ?";

        try (Connection connexion = DriverManager.getConnection(App.url, App.utilisateur, App.motDePasse);
                PreparedStatement statement = connexion.prepareStatement(requete)) {

            statement.setInt(1, id);
            statement.executeUpdate();
            return true;

        } catch (SQLException erreur) {
            System.err.println(erreur.getMessage());
            return false;
        }
    }

    // recuperer l'id d'une classe a partir de son niveau et annee universitaire
    public static int idClasse(String niveau_classe, String annee_univ) {
        String requete = "SELECT idClasse FROM classe WHERE niveau = ? AND annee_universitaire = ?";
        int idClasse = -1;

        try (Connection connexion = DriverManager.getConnection(App.url, App.utilisateur, App.motDePasse);
                PreparedStatement statement = connexion.prepareStatement(requete)) {

            statement.setString(1, niveau_classe);
            statement.setString(2, annee_univ);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                idClasse = resultSet.getInt("idClasse");
            }

        } catch (SQLException erreur) {
            System.err.println(erreur.getMessage());
        }
        return idClasse;
    }

    // suggestions des annees universitaires existantes dans la base de donnees
    public static ObservableList<String> suggestion_annee_universitaire() {
        String requete = "SELECT DISTINCT annee_universitaire FROM classe";
        ObservableList<String> liste_annee_univ = FXCollections.observableArrayList();

        try (Connection connexion = DriverManager.getConnection(App.url, App.utilisateur, App.motDePasse);
                PreparedStatement statement = connexion.prepareStatement(requete)) {

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String annee_univ = resultSet.getString("annee_universitaire");
                liste_annee_univ.add(annee_univ);
            }

        } catch (SQLException erreur) {
            System.err.println("Erreur SQL: " + erreur.getMessage());
        }

        // Retourne sous forme de tableau
        return liste_annee_univ;
    }


    // GESTION DES ETUDIANTS//////////////////////////////////////////////////////////////////////
    // ajouter un etudiant a une classe
    public static boolean ajouter_etudiant(String nom_etudiant, String prenom_etudiant, String matricule_etudiant,
            String niveau_classe, String annee_univ) {

        String requete = "INSERT INTO etudiant (nom, prenoms, matricule, idClasse) VALUES (?, ?, ?, ?)";

        try (Connection connexion = DriverManager.getConnection(App.url, App.utilisateur, App.motDePasse);
                PreparedStatement statement = connexion.prepareStatement(requete)) {

            int idClasse = idClasse(niveau_classe, annee_univ);

            statement.setString(1, nom_etudiant);
            statement.setString(2, prenom_etudiant);
            statement.setString(3, matricule_etudiant);
            statement.setInt(4, idClasse);
            statement.executeUpdate();
            mettreAJourEffectif(idClasse);

            return true;

        } catch (SQLException erreur) {
            System.err.println(erreur.getMessage());
            return false;
        }
    }

    // mettre a jour l'effectif d'une classe
    public static void mettreAJourEffectif(int idClasse) {
        String requete = "UPDATE classe SET effectif = (SELECT COUNT(*) FROM etudiant WHERE idClasse = ?) "
                + "WHERE idClasse = ?";

        try (Connection connexion = DriverManager.getConnection(App.url, App.utilisateur, App.motDePasse);
                PreparedStatement statement = connexion.prepareStatement(requete)) {

            statement.setInt(1, idClasse);
            statement.setInt(2, idClasse);
            statement.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Erreur mise à jour effectif : " + e.getMessage());
        }
    }

    public static ObservableList<structure_etudiant> lister_etudiant(int id) {
        String requete = "SELECT idEtudiant, nom, prenoms, matricule FROM etudiant WHERE idClasse = ?";
        ObservableList<structure_etudiant> liste_etudiant = FXCollections.observableArrayList();

        try (Connection connexion = DriverManager.getConnection(App.url, App.utilisateur, App.motDePasse);
                PreparedStatement statement = connexion.prepareStatement(requete)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int idEtudiant = resultSet.getInt("idEtudiant");
                String nom_etudiant = resultSet.getString("nom");
                String prenom_etudiant = resultSet.getString("prenoms");
                String matricule = resultSet.getString("matricule");
                liste_etudiant.add(new structure_etudiant(idEtudiant, nom_etudiant, prenom_etudiant, matricule));
            }
        } catch (SQLException erreur) {
            System.err.println(erreur.getMessage());
        }
        return liste_etudiant;
    }

}
