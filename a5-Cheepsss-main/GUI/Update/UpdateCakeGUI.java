package GUI.Update;

import Domain.Cake;
import Service.DB.CakeServiceDB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.Optional;

public class UpdateCakeGUI {
    private CakeServiceDB service;

    public UpdateCakeGUI(CakeServiceDB service) {
        this.service = service;
        TextInputDialog dialog = new TextInputDialog();
        dialog.setHeaderText(null);
        dialog.setGraphic(null);
        dialog.setTitle("Update cake");
        dialog.setContentText("Enter cake id:");
        Sid = dialog.showAndWait();

        update = false;
        try {
            int id = Integer.parseInt(Sid.get());
            if (!service.isThere(id)) {
                Alert cakeError = new Alert(Alert.AlertType.NONE, "This id doesn't exit in the repository", ButtonType.OK);
                cakeError.showAndWait();
            } else update = true;
        } catch (Exception e) {
            Alert cakeError = new Alert(Alert.AlertType.NONE, "Invalid id!", ButtonType.OK);
            cakeError.showAndWait();
        }
    }

    private Optional<String> Sid;

    private boolean update;

    @FXML
    private Button updateCakeButton;

    @FXML
    private Button cancelCakeButton;

    @FXML
    private TextField cakeNameField;

    @FXML
    private TextField cakeIngredientsField;

    @FXML
    private TextField cakeTypeField;

    public boolean canUpdate() {
        return update;
    }

    public void cancelCake(ActionEvent actionEvent) throws Exception {
        Stage stage = (Stage) cancelCakeButton.getScene().getWindow();
        stage.close();
    }

    public int readCake() throws Exception {

        int id = Integer.parseInt(Sid.get());
        String name = cakeNameField.getText();
        String ingredients = cakeIngredientsField.getText();
        String type = cakeTypeField.getText();

        Cake cake = null;
        if (!name.isEmpty()) {
            cake = new Cake(id, name, ingredients, type);
            service.update(cake);
        } else
            return 1;
        return 0;
    }

    public void updateCake(ActionEvent mouseEvent) throws Exception {

        int updated = readCake();
        if (updated == 1) {
            Alert cakeError = new Alert(Alert.AlertType.NONE, "Please insert all the required information!", ButtonType.OK);
            cakeError.showAndWait();
        }
        if (updated == 0) {
            cakeNameField.clear();
            cakeIngredientsField.clear();
            cakeTypeField.clear();

            Stage stage = (Stage) updateCakeButton.getScene().getWindow();
            stage.close();

        }
    }
}
