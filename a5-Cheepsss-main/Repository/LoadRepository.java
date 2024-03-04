package Repository;

import Domain.Cake;
import Domain.Order;
import Repository.DB.CakeRepositoryDB;
import Repository.DB.OrderRepositoryDB;
import Repository.File.CakeRepositoryBinaryFile;
import Repository.File.CakeRepositoryTextFile;
import Repository.File.OrderRepositoryBinaryFile;
import Repository.File.OrderRepositoryTextFile;
import Repository.Memory.CakeRepository;
import Repository.Memory.OrderRepository;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class LoadRepository {

    public static IRepository<Integer, Cake> createCakeRepository() {
        IRepository<Integer, Cake> repo = null;

        Properties properties = new Properties();
        try (InputStream is = new FileInputStream("settings.properties")) {
            properties.load(is);
            String repoType = properties.getProperty("repository");
            String fileName = properties.getProperty("cakes");
            switch (repoType) {
                case "text" -> repo = new CakeRepositoryTextFile(fileName);

                case "memory" -> repo = new CakeRepository();

                case "binary" -> repo = new CakeRepositoryBinaryFile(fileName);

                case "db" -> repo=new CakeRepositoryDB(fileName);

                default -> throw new Exception("Invalid repository type");
            }
        } catch (Exception e) {
            System.out.print("Something went wrong when loading the cakes repository! " + e.getMessage());
        }
        return repo;
    }

    public static IRepository<Integer, Order> createOrderRepository() {
        IRepository<Integer, Order> repo = null;

        Properties properties = new Properties();
        try (InputStream is = new FileInputStream("settings.properties")) {
            properties.load(is);
            String repoType = properties.getProperty("repository");
            String fileName = properties.getProperty("orders");
            switch (repoType) {
                case "text" -> repo = new OrderRepositoryTextFile(fileName);


                case "memory" -> repo = new OrderRepository();

                case "binary" -> repo = new OrderRepositoryBinaryFile(fileName);

                case "db" -> repo=new OrderRepositoryDB(fileName);

                default -> throw new Exception("Invalid repository type");
            }
        } catch (Exception e) {
            System.out.print("Something went wrong when loading the orders repository! " + e.getMessage());
        }
        return repo;
    }
}
