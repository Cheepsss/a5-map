package Repository.File;

import Domain.Cake;
import Repository.Memory.CakeRepository;

import java.io.*;
import java.util.Map;

public class CakeRepositoryBinaryFile extends FileRepository<Cake> {
    public CakeRepositoryBinaryFile(String fileName) {
        super(fileName);
    }

    @Override
    public void readFromFile() {
        CakeRepository newRepo = new CakeRepository();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            Map<Integer, Cake> cakeMap = (Map<Integer, Cake>) ois.readObject();
            newRepo.setAllMap(cakeMap);
            this.elements = newRepo.getAllMap();
        } catch (Exception e) {
            System.out.println("Couldn't get cakes from file: " + e.getMessage());
        }
    }

    @Override
    public void writeToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(this.elements);
        } catch (Exception e) {
            System.out.println("Couldn't add cake to file: " + e.getMessage());
        }
    }
}