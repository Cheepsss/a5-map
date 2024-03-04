package main;

import Domain.Order;
import Domain.Cake;
import GUI.Add.AddOrderGUI;
import GUI.DatabaseGUI;
import Repository.DB.DatabaseRepository;
import Repository.LoadRepository;
import Service.DB.OrderServiceDB;
import Service.DB.CakeServiceDB;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        try {
            FXMLLoader orderLoader = new FXMLLoader(getClass().getResource("/GUI/DatabaseGUI.fxml"));

            var cakesRepository = LoadRepository.createCakeRepository();
            CakeServiceDB cakesService = new CakeServiceDB((DatabaseRepository<Cake>) cakesRepository);

            var ordersRepository = LoadRepository.createOrderRepository();
            OrderServiceDB ordersService = new OrderServiceDB((DatabaseRepository<Order>) ordersRepository);

            AddOrderGUI orderGUI = new AddOrderGUI(cakesService, ordersService);
            DatabaseGUI databaseGUI = new DatabaseGUI(cakesService, ordersService);

            orderLoader.setController(databaseGUI);

            Parent root = (Parent) orderLoader.load();

            Scene scene = new Scene(root);
            stage.setTitle("Orders database");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}