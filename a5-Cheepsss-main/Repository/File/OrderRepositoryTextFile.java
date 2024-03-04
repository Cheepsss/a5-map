package Repository.File;

import Domain.Cake;
import Domain.Order;
import Repository.Memory.OrderRepository;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.LocalDate;

public class OrderRepositoryTextFile extends FileRepository<Order> {
    public OrderRepositoryTextFile(String fileName) {
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
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(fileName));
            String line = null;
            while ((line = br.readLine()) != null) {
                String[] attributes = line.split(",");
                if (attributes.length < 7)
                    continue;

                //id, idc, name, ingredients, type, localDate, client
                int orderId = Integer.parseInt(attributes[0].strip());
                Cake cake = new Cake(Integer.parseInt(attributes[1].strip()), attributes[2].strip(), attributes[3].strip(), attributes[4].strip());
                LocalDate date = LocalDate.parse(attributes[5].strip());
                String client = attributes[6].strip();
                Order order = new Order(orderId, cake, date, client);
                newRepo.add(order.getId(), order);
            }
            this.elements = newRepo.getAllMap();
        } catch (Exception e) {
            System.out.print("Couldn't get orders from file: " + e.getMessage());
            System.out.println();
        } finally {
            if (br != null) try {
                br.close();
            } catch (Exception e) {
                System.out.println("Error while closing the orders file: " + e.getMessage());
                System.out.println();
            }
        }
    }

    @Override
    public void writeToFile() {
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(this.fileName));

            //id, idp, name, ingredients, type, localDate, client
            for (Order order : this.elements.values()) {
                bw.write(order.getId() + " , " + order.getCake().getId() + " , " + order.getCake().getName() + " , " +
                        order.getCake().getIngredients() + " , " + order.getCake().getType() + " , " + order.getDate() + " , " + order.getClient());
                bw.newLine();
            }
        } catch (Exception e) {
            System.out.print("Couldn't add order to file: " + e.getMessage());
            System.out.println();
        } finally {
            try {
                assert bw != null;
                bw.close();
            } catch (Exception e) {
                System.out.println("Error while closing the orders file: " + e.getMessage());
                System.out.println();
            }
        }
    }
}
