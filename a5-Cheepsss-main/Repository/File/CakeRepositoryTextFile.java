package Repository.File;

import Domain.Cake;
import Repository.Memory.CakeRepository;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

public class CakeRepositoryTextFile extends FileRepository<Cake> {
    public CakeRepositoryTextFile(String fileName) {
        super(fileName);
    }

    @Override
    public void readFromFile() {
        CakeRepository newRepo = new CakeRepository();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(fileName));
            String line = null;
            while ((line = br.readLine()) != null) {
                String[] attributes = line.split(" , ");
                if (attributes.length < 3)
                    continue;
                Cake cake = new Cake(Integer.parseInt(attributes[0].strip()), attributes[1].strip(), attributes[2].strip(), attributes[3].strip());
                newRepo.add(cake.getId(), cake);
            }
            this.elements = newRepo.getAllMap();
        } catch (Exception e) {
            System.out.print("Couldn't get cakes from file: " + e.getMessage());
            System.out.println();
        } finally {
            if (br != null)
                try {
                    br.close();
                } catch (Exception e) {
                    System.out.println("Error while closing the cakes file: " + e.getMessage());
                    System.out.println();
                }
        }
    }

    @Override
    public void writeToFile() {
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(this.fileName));
            for (Cake cake : this.elements.values()) {
                bw.write(cake.getId() + " , " + cake.getName() + " , " +
                        cake.getIngredients() + " , " + cake.getType());
                bw.newLine();
            }
        } catch (Exception e) {
            System.out.print("Couldn't add cake to file: " + e.getMessage());
        } finally {
            try {
                assert bw != null;
                bw.close();
            } catch (Exception e) {
                System.out.println("Error while closing the cakes file: " + e.getMessage());
            }
        }
    }
}
