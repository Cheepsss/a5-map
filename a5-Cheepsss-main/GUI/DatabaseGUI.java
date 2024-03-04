package GUI;

import Domain.Order;
import Domain.Cake;
import GUI.Add.AddOrderGUI;
import GUI.Add.AddCakeGUI;
import GUI.TableView.OrderTableView;
import GUI.TableView.CakeTableView;
import GUI.Update.UpdateOrderGUI;
import GUI.Update.UpdateCakeGUI;
import Service.DB.OrderServiceDB;
import Service.DB.CakeServiceDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.Optional;

public class DatabaseGUI {

    private CakeServiceDB patServ;
    private OrderServiceDB appServ;

    public DatabaseGUI(CakeServiceDB patServ, OrderServiceDB appServ) throws Exception {
        this.patServ = patServ;
        this.appServ = appServ;
    }

    @FXML
    public TableView databaseTable = new TableView();

    @FXML
    public Button executeQueryButton;

    @FXML
    public Button showCakesButton;

    @FXML
    public Button showOrdersButton;

    @FXML
    private final ObservableList<OrderTableView> orders = FXCollections.observableArrayList();

    @FXML
    private final ObservableList<CakeTableView> cakes = FXCollections.observableArrayList();

    @FXML
    public ComboBox<String> selectQueryComboBox = new ComboBox<>();

    public void displayCakes() throws Exception {

        databaseTable.getItems().clear();
        databaseTable.getColumns().clear();

        for (Cake p : patServ.getAll()) {
            CakeTableView cakeTableView = new CakeTableView(p.getId(), p.getName(),
                    p.getIngredients(), p.getType());
            cakes.add(cakeTableView);
        }

        databaseTable.setEditable(true);
        TableColumn<CakeTableView, Integer> cakeIdColumn = new TableColumn<CakeTableView, Integer>("Cake id");
        TableColumn<CakeTableView, String> nameColumn = new TableColumn<CakeTableView, String>("Name");
        TableColumn<CakeTableView, String> ingredientsColumn = new TableColumn<CakeTableView, String>("Ingredients");
        TableColumn<CakeTableView, String> typeColumn = new TableColumn<CakeTableView, String>("Type");

        cakeIdColumn.setMinWidth(70);
        nameColumn.setMinWidth(150);
        ingredientsColumn.setMinWidth(90);
        typeColumn.setMinWidth(290);

        cakeIdColumn.setStyle("-fx-alignment: CENTER;");
        nameColumn.setStyle("-fx-alignment: CENTER-LEFT;");
        ingredientsColumn.setStyle("-fx-alignment: CENTER-LEFT;");
        typeColumn.setStyle("-fx-alignment: CENTER-LEFT;");

        cakeIdColumn.setCellValueFactory(
                new PropertyValueFactory<CakeTableView, Integer>("cakeId")
        );
        nameColumn.setCellValueFactory(
                new PropertyValueFactory<CakeTableView, String>("name")
        );
        ingredientsColumn.setCellValueFactory(
                new PropertyValueFactory<CakeTableView, String>("ingredients")
        );

        typeColumn.setCellValueFactory(
                new PropertyValueFactory<CakeTableView, String>("type")
        );

        databaseTable.setItems(cakes);
        databaseTable.getColumns().addAll(cakeIdColumn, nameColumn, ingredientsColumn, typeColumn);
    }

    public void displayOrders() throws Exception {

        databaseTable.getItems().clear();
        databaseTable.getColumns().clear();

        for (Order a : appServ.getAll()) {
            OrderTableView orderTableView = new OrderTableView(a.getId(), a.getCake().getId(),
                    a.getDate().toString(), a.getClient());
            orders.add(orderTableView);
        }

        databaseTable.setEditable(true);
        TableColumn<OrderTableView, Integer> orderIdColumn = new TableColumn<OrderTableView, Integer>("Order id");
        TableColumn<OrderTableView, Integer> cakeIdColumn = new TableColumn<OrderTableView, Integer>("Cake id");
        TableColumn<OrderTableView, String> dateColumn = new TableColumn<OrderTableView, String>("Date");
        TableColumn<OrderTableView, String> clientColumn = new TableColumn<OrderTableView, String>("Client description");

        orderIdColumn.setMinWidth(120);
        cakeIdColumn.setMinWidth(75);
        dateColumn.setMinWidth(120);
        clientColumn.setMinWidth(290);

        orderIdColumn.setStyle("-fx-alignment: CENTER;");
        cakeIdColumn.setStyle("-fx-alignment: CENTER;");
        dateColumn.setStyle("-fx-alignment: CENTER;");
        clientColumn.setStyle("-fx-alignment: CENTER-LEFT;");

        orderIdColumn.setCellValueFactory(
                new PropertyValueFactory<OrderTableView, Integer>("orderId")
        );
        cakeIdColumn.setCellValueFactory(
                new PropertyValueFactory<OrderTableView, Integer>("cakeId")
        );
        dateColumn.setCellValueFactory(
                new PropertyValueFactory<OrderTableView, String>("date")
        );

        clientColumn.setCellValueFactory(
                new PropertyValueFactory<OrderTableView, String>("client")
        );

        databaseTable.setItems(orders);
        databaseTable.getColumns().addAll(orderIdColumn, cakeIdColumn, dateColumn, clientColumn);
    }

    public void addCake() throws Exception {
        displayCakes();
        AddCakeGUI cakesGUI = new AddCakeGUI(patServ);
        FXMLLoader cakeLoader = new FXMLLoader(getClass().getResource("/GUI/Add/AddCakeGUI.fxml"));
        cakeLoader.setController(cakesGUI);

        Parent root = (Parent) cakeLoader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Add cake");
        stage.show();

        displayCakes();
    }

    public void addOrder() throws Exception {
        displayOrders();
        AddOrderGUI ordersGUI = new AddOrderGUI(patServ, appServ);
        FXMLLoader cakeLoader = new FXMLLoader(getClass().getResource("/GUI/Add/AddOrderGUI.fxml"));
        cakeLoader.setController(ordersGUI);

        Parent root = (Parent) cakeLoader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Add order");
        stage.show();
        displayOrders();
    }

    public void showCakes(ActionEvent actionEvent) throws Exception {
        displayCakes();
    }

    public void showOrders(ActionEvent actionEvent) throws Exception {
        displayOrders();
    }

    public void removeCake() throws Exception {
        displayCakes();
        TextInputDialog dialog = new TextInputDialog();
        dialog.setHeaderText(null);
        dialog.setGraphic(null);
        dialog.setTitle("Remove cake");
        dialog.setContentText("Enter cake id:");
        Optional<String> id = dialog.showAndWait();

        try {
            if (id.isPresent())
                patServ.remove(Integer.parseInt(id.get()));
        } catch (Exception e) {
            Alert cakeError = new Alert(Alert.AlertType.NONE, "Invalid id!", ButtonType.OK);
            cakeError.showAndWait();
        }
        displayCakes();
    }

    public void removeOrder() throws Exception {
        displayOrders();
        TextInputDialog dialog = new TextInputDialog();
        dialog.setHeaderText(null);
        dialog.setGraphic(null);
        dialog.setTitle("Remove order");
        dialog.setContentText("Enter order id:");
        Optional<String> id = dialog.showAndWait();

        try {
            if (id.isPresent())
                appServ.remove(Integer.parseInt(id.get()));
        } catch (Exception e) {
            Alert orderError = new Alert(Alert.AlertType.NONE, "This id doesn't exist in the database!", ButtonType.OK);
            orderError.showAndWait();
        }
        displayOrders();
    }

    public void updateCake() throws Exception {
        displayCakes();
        UpdateCakeGUI cakeGUI = new UpdateCakeGUI(patServ);
        if (cakeGUI.canUpdate()) {
            FXMLLoader cakeLoader = new FXMLLoader(getClass().getResource("/GUI/Update/UpdateCakeGUI.fxml"));
            cakeLoader.setController(cakeGUI);

            Parent root = (Parent) cakeLoader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Update cake");
            stage.show();
        }
        displayCakes();
    }


    public void updateOrder() throws Exception {
        displayOrders();
        UpdateOrderGUI orderGUI = new UpdateOrderGUI(appServ);
        if (orderGUI.canUpdate()) {
            FXMLLoader orderLoader = new FXMLLoader(getClass().getResource("/GUI/Update/UpdateOrderGUI.fxml"));
            orderLoader.setController(orderGUI);

            Parent root = (Parent) orderLoader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Update order");
            stage.show();
        }
        displayOrders();
    }

    public void displayIngredientsNumberOfCake() throws Exception {
        displayCakes();
        TextInputDialog dialog = new TextInputDialog();
        dialog.setHeaderText(null);
        dialog.setGraphic(null);
        dialog.setTitle("Display ingredients number");
        dialog.setContentText("Enter cake id:");
        Optional<String> Sid = dialog.showAndWait();
        try {
            int id = Integer.parseInt(Sid.get());
            String ingredients = patServ.getById(id).getIngredients();
            Alert cakeError = new Alert(Alert.AlertType.NONE, ingredients, ButtonType.OK);
            cakeError.showAndWait();
        } catch (Exception e) {
            Alert cakeError = new Alert(Alert.AlertType.NONE, "Invalid id!", ButtonType.OK);
            cakeError.showAndWait();
        }
        displayCakes();
    }

    public void displayTypeOfCake() throws Exception {
        displayCakes();
        TextInputDialog dialog = new TextInputDialog();
        dialog.setHeaderText(null);
        dialog.setGraphic(null);
        dialog.setTitle("Display type");
        dialog.setContentText("Enter cake id:");
        Optional<String> Sid = dialog.showAndWait();
        try {
            int id = Integer.parseInt(Sid.get());
            String client = null;
            for (Order a : appServ.getAll()) {
                if (a.getCake().getId() == id)
                    if (client == null)
                        client = a.getClient();
                    else
                        client = client + '\n' + a.getClient();
            }
            Alert cakeError = new Alert(Alert.AlertType.NONE, client, ButtonType.OK);
            cakeError.showAndWait();
            displayOrdersOfCake(id);
        } catch (Exception e) {
            Alert cakeError = new Alert(Alert.AlertType.NONE, "Invalid id!", ButtonType.OK);
            cakeError.showAndWait();
        }
    }

    public void displayOrdersOfCake(int givenId) {
        databaseTable.getItems().clear();
        databaseTable.getColumns().clear();
        try {
            int id;
            if (givenId == -1) {
                TextInputDialog dialog = new TextInputDialog();
                dialog.setHeaderText(null);
                dialog.setGraphic(null);
                dialog.setTitle("Display orders");
                dialog.setContentText("Enter cake id:");
                Optional<String> Sid = dialog.showAndWait();
                id = Integer.parseInt(Sid.get());
            } else id = givenId;

            for (Order a : appServ.getAll()) {
                if (a.getCake().getId() == id) {
                    OrderTableView orderTableView = new OrderTableView(a.getId(), a.getCake().getId(),
                            a.getDate().toString(), a.getClient());
                    orders.add(orderTableView);
                }
            }

            databaseTable.setEditable(true);
            TableColumn<OrderTableView, Integer> orderIdColumn = new TableColumn<OrderTableView, Integer>("Order id");
            TableColumn<OrderTableView, Integer> cakeIdColumn = new TableColumn<OrderTableView, Integer>("Cake id");
            TableColumn<OrderTableView, String> dateColumn = new TableColumn<OrderTableView, String>("Date");
            TableColumn<OrderTableView, String> clientColumn = new TableColumn<OrderTableView, String>("Client description");

            orderIdColumn.setMinWidth(120);
            cakeIdColumn.setMinWidth(75);
            dateColumn.setMinWidth(120);
            clientColumn.setMinWidth(290);

            orderIdColumn.setStyle("-fx-alignment: CENTER;");
            cakeIdColumn.setStyle("-fx-alignment: CENTER;");
            dateColumn.setStyle("-fx-alignment: CENTER;");
            clientColumn.setStyle("-fx-alignment: CENTER-LEFT;");

            orderIdColumn.setCellValueFactory(
                    new PropertyValueFactory<OrderTableView, Integer>("orderId")
            );
            cakeIdColumn.setCellValueFactory(
                    new PropertyValueFactory<OrderTableView, Integer>("cakeId")
            );
            dateColumn.setCellValueFactory(
                    new PropertyValueFactory<OrderTableView, String>("date")
            );

            clientColumn.setCellValueFactory(
                    new PropertyValueFactory<OrderTableView, String>("client")
            );

            databaseTable.setItems(orders);
            databaseTable.getColumns().addAll(orderIdColumn, cakeIdColumn, dateColumn, clientColumn);
        } catch (Exception e) {
            Alert cakeError = new Alert(Alert.AlertType.NONE, "Invalid id!", ButtonType.OK);
            cakeError.showAndWait();
        }
    }

    public void displayOrdersByDate() throws Exception {
        databaseTable.getItems().clear();
        databaseTable.getColumns().clear();
        displayOrders();

        try {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setHeaderText(null);
            dialog.setGraphic(null);
            dialog.setTitle("Display orders");
            dialog.setContentText("Enter date (YYYY-MM-DD):");
            Optional<String> Sid = dialog.showAndWait();
            String date = Sid.get();

            databaseTable.getItems().clear();
            databaseTable.getColumns().clear();

            for (Order a : appServ.getAll()) {
                if (a.getDate().toString().equals(date)) {
                    OrderTableView orderTableView = new OrderTableView(a.getId(), a.getCake().getId(),
                            a.getDate().toString(), a.getClient());
                    orders.add(orderTableView);
                }
            }

            databaseTable.setEditable(true);
            TableColumn<OrderTableView, Integer> orderIdColumn = new TableColumn<OrderTableView, Integer>("Order id");
            TableColumn<OrderTableView, Integer> cakeIdColumn = new TableColumn<OrderTableView, Integer>("Cake id");
            TableColumn<OrderTableView, String> dateColumn = new TableColumn<OrderTableView, String>("Date");
            TableColumn<OrderTableView, String> clientColumn = new TableColumn<OrderTableView, String>("Client description");

            orderIdColumn.setMinWidth(120);
            cakeIdColumn.setMinWidth(75);
            dateColumn.setMinWidth(120);
            clientColumn.setMinWidth(290);

            orderIdColumn.setStyle("-fx-alignment: CENTER;");
            cakeIdColumn.setStyle("-fx-alignment: CENTER;");
            dateColumn.setStyle("-fx-alignment: CENTER;");
            clientColumn.setStyle("-fx-alignment: CENTER-LEFT;");

            orderIdColumn.setCellValueFactory(
                    new PropertyValueFactory<OrderTableView, Integer>("orderId")
            );
            cakeIdColumn.setCellValueFactory(
                    new PropertyValueFactory<OrderTableView, Integer>("cakeId")
            );
            dateColumn.setCellValueFactory(
                    new PropertyValueFactory<OrderTableView, String>("date")
            );

            clientColumn.setCellValueFactory(
                    new PropertyValueFactory<OrderTableView, String>("client")
            );

            databaseTable.setItems(orders);
            databaseTable.getColumns().addAll(orderIdColumn, cakeIdColumn, dateColumn, clientColumn);
        } catch (Exception e) {
            Alert cakeError = new Alert(Alert.AlertType.NONE, "Invalid date! (date format: YYYY-MM-DD)", ButtonType.OK);
            cakeError.showAndWait();
        }
    }

    public void executeQuery(ActionEvent actionEvent) throws Exception {
        String option = selectQueryComboBox.getValue();
        databaseTable.getItems().clear();
        switch (option) {
            case "Add a cake" -> addCake();
            case "Update a cake" -> updateCake();
            case "Remove a cake" -> removeCake();
            case "Add an order" -> addOrder();
            case "Update an order" -> updateOrder();
            case "Remove an order" -> removeOrder();
            case "Display ingredients number of a cake" -> displayIngredientsNumberOfCake();
            case "Display all type of a cake" -> displayTypeOfCake();
            case "Display all orders of a cake" -> displayOrdersOfCake(-1);
            case "Display all orders in a day" -> displayOrdersByDate();
        }
    }

    public void initialize() throws Exception {

        ObservableList<String> queries = FXCollections.observableArrayList();
        queries.add("Add a cake");
        queries.add("Update a cake");
        queries.add("Remove a cake");
        queries.add("Add an order");
        queries.add("Update an order");
        queries.add("Remove an order");
        queries.add("Display ingredients of a cake");
        queries.add("Display all type of a cake");
        queries.add("Display all orders of a cake");
        queries.add("Display all orders in a day");
        selectQueryComboBox.setValue("Select query");
        selectQueryComboBox.setItems(queries);
    }
}
