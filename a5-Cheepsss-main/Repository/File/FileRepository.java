package Repository.File;

import Domain.Entity;
import Repository.IRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class FileRepository<TElem extends Entity> implements IRepository<Integer, TElem> {
    protected String fileName;
    protected Map<Integer, TElem> elements;

    public FileRepository(String fileName) {
        this.fileName = fileName;
        elements = new HashMap<Integer, TElem>();
        readFromFile();
    }

    abstract public void readFromFile();

    abstract public void writeToFile();

    @Override
    public boolean add(Integer id, TElem obj) {
        boolean ok = elements.put(id, obj) == null;
        writeToFile();
        return ok;
    }

    @Override
    public boolean remove(Integer id) {
        boolean ok = elements.remove(id) != null;
        writeToFile();
        return ok;
    }

    @Override
    public boolean update(Integer id, TElem obj) {
        boolean ok = elements.replace(id, obj) != null;
        writeToFile();
        return ok;
    }

    @Override
    public int size() {
        return elements.size();
    }

    @Override
    public ArrayList<TElem> getAll() {
        return new ArrayList<TElem>(elements.values());
    }

    @Override
    public TElem getElem(Integer id) {
        return elements.get(id);
    }

    public Map<Integer, TElem> getAllMap() {
        return elements;
    }

}
