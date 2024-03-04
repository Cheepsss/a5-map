package GUI.TableView;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class OrderTableView {

    private SimpleIntegerProperty orderId;
    private SimpleIntegerProperty cakeId;
    private SimpleStringProperty date;
    private SimpleStringProperty client;


    public OrderTableView(Integer orderId, Integer cakeId, String date, String client) {
        this.orderId = new SimpleIntegerProperty(orderId);
        this.cakeId = new SimpleIntegerProperty(cakeId);
        this.date = new SimpleStringProperty(date);
        this.client = new SimpleStringProperty(client);
    }


    public Integer getOrderId() {
        return orderId.get();
    }

    public Integer getCakeId() {
        return cakeId.get();
    }

    public String getDate() {
        return date.get();
    }

    public String getClient() {
        return client.get();
    }

    @Override
    public String toString() {
        return "OrderTableView{" +
                "orderId=" + orderId +
                ", cakeId=" + cakeId +
                ", date=" + date +
                ", client=" + client +
                '}';
    }
}
