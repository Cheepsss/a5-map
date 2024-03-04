package Service.DB;

import Domain.Cake;
import Repository.DB.DatabaseRepository;

public class CakeServiceDB extends EntityServiceDB<Cake> {

    public CakeServiceDB(DatabaseRepository<Cake> cakeRepo) {
        super(cakeRepo);
    }
}