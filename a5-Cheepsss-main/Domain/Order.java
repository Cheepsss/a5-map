package Domain;

import java.io.Serializable;
import java.time.LocalDate;

public class Order extends Entity implements Serializable {

    public static final long serialVersionUID = 123L;
    private Cake cake;
    private LocalDate date;
    private String client;


    public Order(int id, Cake cake, LocalDate date, String client) {
        super(id);
        this.cake = cake;
        this.date = date;
        this.client = client;
    }

    public Cake getCake() {
        return cake;
    }

    public void setCake(Cake cake) {
        this.cake = cake;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    @Override
    public String toString() {
        return "Order{" + '\n' +
                "id = " + Integer.toString(getId()) + '\n' + cake.toString() +
                "date = " + date.toString() + '\n' +
                "client = " + client + '\n' +
                '}' + '\n';
    }
}