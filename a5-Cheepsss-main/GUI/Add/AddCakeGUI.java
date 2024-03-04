package GUI.Add;

import Domain.Cake;
import Service.DB.CakeServiceDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddCakeGUI {
    private CakeServiceDB service;

    public AddCakeGUI(CakeServiceDB service) {
        this.service = service;
    }

    @FXML
    private Button addCakeButton;

    @FXML
    private Button cancelCakeButton;


    @FXML
    private TextField cakeIdField;

    @FXML
    private TextField cakeNameField;

    @FXML
    private TextField cakeIngredientsField;

    @FXML
    private TextField cakeTypeField;

    private ObservableList<Cake> cakes = FXCollections.observableArrayList();

    public void cancelCake(ActionEvent actionEvent) throws Exception {
        Stage stage = (Stage) cancelCakeButton.getScene().getWindow();
        stage.close();
    }

    public int readCake() throws Exception {
        String cakeId = cakeIdField.getText();
        String name = cakeNameField.getText();
        String ingredients = cakeIngredientsField.getText();
        String type = cakeTypeField.getText();

        Cake cake = null;
        if (!cakeId.isEmpty() && !name.isEmpty()) {
            if (service.isThere(Integer.parseInt(cakeId)))
                return 1;
            else {
                cake = new Cake(Integer.parseInt(cakeId), name, ingredients, type);
                service.add(cake);
            }
        } else
            return 2;
        return 0;
    }

    public void addCake(ActionEvent mouseEvent) throws Exception {

        int added = readCake();
        if (added == 2) {
            Alert cakeError = new Alert(Alert.AlertType.NONE, "Please insert all the required information!", ButtonType.OK);
            cakeError.showAndWait();
        }
        if (added == 1) {
            Alert cakeError = new Alert(Alert.AlertType.NONE, "This id already exists in the repository!", ButtonType.OK);
            cakeError.showAndWait();
        }
        if (added == 0) {
            cakeIdField.clear();
            cakeNameField.clear();
            cakeIngredientsField.clear();
            cakeTypeField.clear();

            Stage stage = (Stage) addCakeButton.getScene().getWindow();
            stage.close();
        }
    }
}
