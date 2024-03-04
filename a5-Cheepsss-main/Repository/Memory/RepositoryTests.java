package Repository.Memory;/*package Repository.Memory;

import Domain.Cake;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RepositoryTest {

    private CakeRepository patRepo;

    @BeforeEach
    void setUp() {
        patRepo = new CakeRepository();
        patRepo.add(1, new Cake(1, "name1", "ingredients1", "a1"));
        patRepo.add(2, new Cake(2, "name2", "ingredients2", "a2"));
        patRepo.add(3, new Cake(3, "name3", "ingredients3", "a3"));
    }

    @Test
    void addCake() {
        Cake p4 = new Cake(4, "name4", "ingredients4", "a4");
        patRepo.add(p4.getId(), p4);
        assert patRepo.getElem(4) == p4;
        assert patRepo.size() == 4;
    }

    @Test
    void removeCake() {
        patRepo.remove(3);
        assert patRepo.size() == 2;
    }

    @Test
    void sizeCake() {
        Cake p4 = new Cake(4, "name4", "ingredients4", "a4");
        assert patRepo.size() == 4;
    }
}*/