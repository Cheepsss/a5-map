package GUI.Update;

import Domain.Order;
import Domain.Cake;
import Service.DB.OrderServiceDB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.Optional;

public class UpdateOrderGUI {

    private OrderServiceDB appServ;

    public UpdateOrderGUI(OrderServiceDB appServ) {
        this.appServ = appServ;

        TextInputDialog dialog = new TextInputDialog();
        dialog.setHeaderText(null);
        dialog.setGraphic(null);
        dialog.setTitle("Update order");
        dialog.setContentText("Enter order id:");
        Sid = dialog.showAndWait();

        update = false;
        try {
            int id = Integer.parseInt(Sid.get());
            if (!appServ.isThere(id)) {
                Alert orderError = new Alert(Alert.AlertType.NONE, "This id doesn't exit in the repository", ButtonType.OK);
                orderError.showAndWait();
            } else update = true;
        } catch (Exception e) {
            Alert cakeError = new Alert(Alert.AlertType.NONE, "Invalid id!", ButtonType.OK);
            cakeError.showAndWait();
        }
    }

    private Optional<String> Sid;

    private boolean update;

    @FXML
    private Button addOrderButton;

    @FXML
    Button cancelOrderButton;

    @FXML
    private TextField orderDateField;

    @FXML
    private TextArea orderClientField;

    public boolean canUpdate() {
        return update;
    }

    public void cancelOrder(ActionEvent actionEvent) throws Exception {
        Stage stage = (Stage) cancelOrderButton.getScene().getWindow();
        stage.close();
    }

    public int readOrder() throws Exception {

        int id = Integer.parseInt(Sid.get());
        String date = orderDateField.getText();
        String client = orderClientField.getText();

        Cake cake = null;
        Order order = null;
        if (!date.isEmpty()) {
            cake = appServ.getById(id).getCake();
            order = new Order(id, cake, LocalDate.parse(date), client);
            appServ.update(order);
        } else
            return 1;
        return 0;
    }

    public void updateOrder(ActionEvent actionEvent) throws Exception {

        int updated = readOrder();
        if (updated == 1) {
            Alert orderError = new Alert(Alert.AlertType.NONE, "Please insert all the required information!", ButtonType.OK);
            orderError.showAndWait();
        }
        if (updated == 0) {
            orderDateField.clear();
            orderClientField.clear();

            Stage stage = (Stage) addOrderButton.getScene().getWindow();
            stage.close();
        }
    }
}
