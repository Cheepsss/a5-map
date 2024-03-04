module GUI {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.sql;
    requires org.xerial.sqlitejdbc;

    exports GUI;
    exports main;
    exports GUI.TableView;

    opens GUI to javafx.fxml;
    opens GUI.TableView to javafx.fxml;
    opens GUI.Add to javafx.fxml;
    opens GUI.Update to javafx.fxml;
}