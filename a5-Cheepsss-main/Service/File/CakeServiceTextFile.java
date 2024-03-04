package Service.File;

import Domain.Cake;
import Repository.File.FileRepository;

public class CakeServiceTextFile extends EntityServiceFile<Cake> {

    public CakeServiceTextFile(FileRepository<Cake> cakeRepo) {
        super(cakeRepo);
    }
}

