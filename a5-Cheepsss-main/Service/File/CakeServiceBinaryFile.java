package Service.File;

import Domain.Order;
import Repository.File.FileRepository;

public class CakeServiceBinaryFile extends EntityServiceFile<Order> {

    public CakeServiceBinaryFile(FileRepository<Order> orderRepo) {
        super(orderRepo);
    }
}
