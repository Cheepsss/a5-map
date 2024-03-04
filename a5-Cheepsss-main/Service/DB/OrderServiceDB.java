package Service.DB;

import Domain.Order;
import Repository.DB.DatabaseRepository;

public class OrderServiceDB extends EntityServiceDB<Order> {

    public OrderServiceDB(DatabaseRepository<Order> databaseRepository) {
        super(databaseRepository);
    }
}