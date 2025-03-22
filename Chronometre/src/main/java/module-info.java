module org.project.chronometre {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.project.chronometre to javafx.fxml;
    exports org.project.chronometre;
}