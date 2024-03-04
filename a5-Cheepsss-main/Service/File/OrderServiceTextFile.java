package Service.File;

import Domain.Order;
import Repository.File.FileRepository;

public class OrderServiceTextFile extends EntityServiceFile<Order> {

    public OrderServiceTextFile(FileRepository<Order> orderRepo) {
        super(orderRepo);
    }
}
