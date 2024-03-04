package GUI.Add;

import Domain.Order;
import Domain.Cake;
import Service.DB.OrderServiceDB;
import Service.DB.CakeServiceDB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDate;

public class AddOrderGUI {

    private OrderServiceDB appServ;
    private CakeServiceDB patServ;

    public AddOrderGUI(CakeServiceDB patServ, OrderServiceDB appServ) {
        this.patServ = patServ;
        this.appServ = appServ;
    }

    @FXML
    private Button addOrderButton;

    @FXML
    Button cancelOrderButton;

    @FXML
    private TextField orderIdField;

    @FXML
    private TextField orderDateField;

    @FXML
    private TextArea orderClientField;

    @FXML
    private TextField cakeIdField;

    @FXML
    private TextField cakeNameField;

    @FXML
    private TextField cakeIngredientsField;

    @FXML
    private TextField cakeTypeField;

    public void cancelOrder(ActionEvent actionEvent) throws Exception {
        Stage stage = (Stage) cancelOrderButton.getScene().getWindow();
        stage.close();
    }

    public int readOrder() throws Exception {
        String orderId = orderIdField.getText();
        String date = orderDateField.getText();
        String client = orderClientField.getText();

        String cakeId = cakeIdField.getText();
        String name = cakeNameField.getText();
        String ingredients = cakeIngredientsField.getText();
        String type = cakeTypeField.getText();

        Cake cake = null;
        if (!orderId.isEmpty() && !date.isEmpty() && !cakeId.isEmpty() && !name.isEmpty()) {
            cake = new Cake(Integer.parseInt(cakeId), name, ingredients, type);
            Order order = null;
            if (appServ.isThere(Integer.parseInt(orderId)))
                return 2;

            order = new Order(Integer.parseInt(orderId), cake, LocalDate.parse(date), client);
            appServ.add(order);
            if (!patServ.isThere(Integer.parseInt(cakeId)))
                patServ.add(cake);
        } else return 1;
        return 0;
    }

    public void addOrder(ActionEvent actionEvent) throws Exception {

        int added = readOrder();
        if (added == 1) {
            Alert orderError = new Alert(Alert.AlertType.NONE, "Please insert all the required information!", ButtonType.OK);
            orderError.showAndWait();
        }
        if (added == 2) {
            Alert orderError = new Alert(Alert.AlertType.NONE, "Order id already exists in the database!", ButtonType.OK);
            orderError.showAndWait();
        }
        if (added == 0) {
            orderIdField.clear();
            orderDateField.clear();
            orderClientField.clear();
            cakeIdField.clear();
            cakeNameField.clear();
            cakeIngredientsField.clear();
            cakeTypeField.clear();

            Stage stage = (Stage) addOrderButton.getScene().getWindow();
            stage.close();
        }
    }
}
