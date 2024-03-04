package Service.File;

import Domain.Order;
import Repository.File.FileRepository;

public class OrderServiceBinaryFile extends EntityServiceFile<Order> {

    public OrderServiceBinaryFile(FileRepository<Order> orderRepo) {
        super(orderRepo);
    }
}
