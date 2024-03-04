package Repository.DB;

import Domain.Entity;
import Repository.IRepository;
import org.sqlite.SQLiteDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class DatabaseRepository<TElem extends Entity> implements IRepository<Integer, TElem> {

    protected String url;
    protected Connection conn = null;

    public DatabaseRepository(String url) {
        this.url = "jdbc:sqlite:" + url;
        createTable();
    }

    protected void openConnection() {
        try {
            SQLiteDataSource ds = new SQLiteDataSource();
            ds.setUrl(url);
            if (conn == null || conn.isClosed()) {
                conn = ds.getConnection();
                System.out.println("Successfully connected!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void closeConnection() {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    abstract protected void createTable();

    @Override
    abstract public boolean add(Integer id, TElem obj);

    @Override
    abstract public boolean remove(Integer id);

    @Override
    public abstract boolean update(Integer id, TElem obj);

    @Override
    public abstract TElem getElem(Integer id);

    abstract public boolean isThere(Integer id);
}
