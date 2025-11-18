module gestion_deliberation_univ {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires javafx.base;
    requires javafx.graphics;

    opens gestion_deliberation_univ.Controleurs to javafx.fxml;
    opens gestion_deliberation_univ.Modeles to javafx.base;
    exports gestion_deliberation_univ;
}
