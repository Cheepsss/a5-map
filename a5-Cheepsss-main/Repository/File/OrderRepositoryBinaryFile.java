package Repository.File;

import Domain.Cake;
import Domain.Order;
import Repository.Memory.OrderRepository;

import java.io.*;
import java.time.LocalDate;
import java.util.Map;

public class OrderRepositoryBinaryFile extends FileRepository<Order> {
    public OrderRepositoryBinaryFile(String fileName) {
        super(fileName);
    }

    @Override
    public boolean add(Integer id, Order order) {
        boolean ok = super.add(id, order);
        writeToFile();
        return ok;
    }

    @Override
    public void readFromFile() {
        OrderRepository newRepo = new OrderRepository();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            Map<Integer, Order> orderMap = (Map<Integer, Order>) ois.readObject();
            newRepo.setAllMap(orderMap);
            this.elements = newRepo.getAllMap();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Couldn't get orders from file: " + e.getMessage());
        }
    }

    @Override
    public void writeToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            Map<Integer, Order> orderMap = this.elements;
            oos.writeObject(orderMap);
        } catch (IOException e) {
            System.out.println("Couldn't add order to file: " + e.getMessage());
        }
    }
}
