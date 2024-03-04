package Repository.DB;

import Domain.Cake;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CakeRepositoryDB extends DatabaseRepository<Cake> {

    public CakeRepositoryDB(String url) {
        super(url);
    }

    @Override
    protected void createTable() {
        openConnection();
        try {
            final Statement s = conn.createStatement();
            s.executeUpdate("CREATE TABLE IF NOT EXISTS Cakes(Id INT PRIMARY  KEY, CakeName VARCHAR(50), Ingredients VARCHAR(15), `Type` VARCHAR(50));");
            s.close();
        } catch (SQLException e) {
            System.err.println("Couldn't create cakes table! " + e.getMessage());
        }
    }

    @Override
    public boolean add(Integer id, Cake cake) {
        openConnection();
        try {
            PreparedStatement statement = conn.prepareStatement("INSERT INTO Cakes VALUES (?, ?, ?, ?);");
            statement.setInt(1, id);
            statement.setString(2, cake.getName());
            statement.setString(3, cake.getIngredients());
            statement.setString(4, cake.getType());
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
    public boolean remove(Integer id) {
        openConnection();
        try {
            // return false if the cake was not found
            if (!isThere(id)) {
                return false;
            }
            PreparedStatement statement = conn.prepareStatement("DELETE FROM Cakes WHERE Id = ?;");
            statement.setInt(1, id);
            statement.executeUpdate();

            statement = conn.prepareStatement("DELETE FROM Orders WHERE CakeId = ?;");
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
    public boolean update(Integer id, Cake cake) {
        openConnection();
        try {
            // return false if the cake was not found
            if (!isThere(id)) {
                return false;
            }
            PreparedStatement statement = conn.prepareStatement("UPDATE Cakes SET CakeName = ?, Ingredients = ?, `Type` = ? WHERE Id = ?;");
            statement.setString(1, cake.getName());
            statement.setString(2, cake.getIngredients());
            statement.setString(3, cake.getType());
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
    public ArrayList<Cake> getAll() {
        openConnection();
        ArrayList<Cake> cakes = new ArrayList<>();
        try {
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM Cakes");
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Cake cake = new Cake(
                        rs.getInt("Id"),
                        rs.getString("CakeName"),
                        rs.getString("Ingredients"),
                        rs.getString("Type"));
                cakes.add(cake);
            }
            rs.close();
            statement.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            //closeConnection();
        }
        return cakes;
    }

    @Override
    public Cake getElem(Integer id) {
        openConnection();
        Cake cake = null;
        try {
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM Cakes WHERE Id = ?");
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            String name = rs.getString("CakeName");
            String ingredients = rs.getString("Ingredients");
            String type = rs.getString("Type");
            if (name != null && ingredients != null && type != null)
                cake = new Cake(id, name, ingredients, type);
            statement.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            //closeConnection();
        }
        // returns null if the cake was not found
        return cake;
    }

    @Override
    public boolean isThere(Integer id) {
        openConnection();
        int ct = 0;
        try {
            PreparedStatement statement = conn.prepareStatement("SELECT COUNT(1) FROM Cakes WHERE Id = ?");
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
