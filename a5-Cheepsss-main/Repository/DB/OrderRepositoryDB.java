package Repository.DB;

import Domain.Cake;
import Domain.Order;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

public class OrderRepositoryDB extends DatabaseRepository<Order> {

    public OrderRepositoryDB(String url) {
        super(url);
    }

    @Override
    protected void createTable() {
        try {
            openConnection();
            final Statement s = conn.createStatement();
            s.executeUpdate("CREATE TABLE IF NOT EXISTS Orders(Id INT PRIMARY  KEY, CakeId INT, Date VARCHAR(10), Client VARCHAR(200));");
            s.close();
        } catch (SQLException e) {
            System.err.println("Couldn't create orders table! " + e.getMessage());
        }
    }

    @Override
    public boolean add(Integer id, Order order) {
        openConnection();
        try {
            PreparedStatement statement = conn.prepareStatement("INSERT INTO Orders VALUES (?, ?, ?, ?);");
            statement.setInt(1, id);
            statement.setInt(2, order.getCake().getId());
            statement.setString(3, order.getDate().toString());
            statement.setString(4, order.getClient());
            statement.executeUpdate();
            statement.close();
            return true;
        } catch (SQLException e) {
            System.out.println("Couldn't add orders to database! " + e.getMessage());
            return false;
        } finally {
            //closeConnection();
        }
    }
    

    @Override
    public boolean remove(Integer id) {
        openConnection();
        try {
            // return false if the order was not found
            if (!isThere(id)) {
                //closeConnection();
                return false;
            }
            PreparedStatement statement = conn.prepareStatement("DELETE FROM Orders WHERE Id = ?;");
            statement.setInt(1, id);
            statement.executeUpdate();
            statement.close();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        } finally {
            //closeConnection();
        }
    }

    @Override
    public boolean update(Integer id, Order order) {
        openConnection();
        try {
            // return false if the order was not found
            if (!isThere(id)) {
                //closeConnection();
                return false;
            }
            PreparedStatement statement = conn.prepareStatement("UPDATE Orders SET CakeId = ?, Date = ?, Client = ? WHERE Id = ?;");
            statement.setInt(1, order.getCake().getId());
            statement.setString(2, order.getDate().toString());
            statement.setString(3, order.getClient());
            statement.setInt(4, id);
            statement.executeUpdate();
            statement.close();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        } finally {
            //closeConnection();
        }
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public ArrayList<Order> getAll() {
        openConnection();
        ArrayList<Order> orders = new ArrayList<>();
        try {
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM Orders");
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {

                int orderId = rs.getInt("Id");
                int cakeId = rs.getInt("CakeId");
                LocalDate date = LocalDate.parse(rs.getString("Date"));
                String client = rs.getString("Client");

                PreparedStatement statementCake = conn.prepareStatement("SELECT * FROM Cakes WHERE Id = ?");
                statementCake.setInt(1, cakeId);
                ResultSet rsCake = statementCake.executeQuery();

                Cake cake = null;
                while (rsCake.next()) {
                    cake = new Cake(
                            rsCake.getInt("Id"),
                            rsCake.getString("CakeName"),
                            rsCake.getString("Ingredients"),
                            rsCake.getString("Type"));
                }

                Order order = new Order(orderId, cake, date, client);
                orders.add(order);
                rsCake.close();
                statementCake.close();
            }
            rs.close();
            statement.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            //closeConnection();
        }
        return orders;
    }

    @Override
    public Order getElem(Integer id) {
        openConnection();
        Order order = null;
        try {
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM Orders WHERE Id = ?");
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            int cakeId = rs.getInt("CakeId");
            String date = rs.getString("Date");
            String client = rs.getString("Client");

            if (date != null && client != null) {

                PreparedStatement statementCake = conn.prepareStatement("SELECT * FROM Cakes WHERE Id = ?");
                statementCake.setInt(1, cakeId);
                ResultSet rsCake = statementCake.executeQuery();

                Cake cake = null;
                while (rsCake.next()) {
                    cake = new Cake(
                            rsCake.getInt("Id"),
                            rsCake.getString("CakeName"),
                            rsCake.getString("Ingredients"),
                            rsCake.getString("Type"));
                }
                statementCake.close();
                rsCake.close();

                if (cake != null)
                    order = new Order(id, cake, LocalDate.parse(date), client);
            }
            statement.close();
            rs.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            //closeConnection();
        }
        // returns null if the order was not found
        return order;
    }

    @Override
    public boolean isThere(Integer id) {
        openConnection();
        int ct = 0;
        try {
            PreparedStatement statement = conn.prepareStatement("SELECT COUNT(1) FROM Orders WHERE Id = ?");
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            ct = rs.getInt(1);
            statement.close();
            rs.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            //closeConnection();
        }
        return ct == 1;
    }
}
