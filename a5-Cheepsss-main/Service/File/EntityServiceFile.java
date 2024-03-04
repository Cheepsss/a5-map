package Service.File;

import Domain.Entity;
import Repository.File.FileRepository;

import java.util.ArrayList;

public abstract class EntityServiceFile<TElem extends Entity> {

    private final FileRepository<TElem> repo;

    public EntityServiceFile(FileRepository<TElem> repo) {
        this.repo = repo;
    }

    public void add(TElem obj) throws Exception {
        if (!repo.add(obj.getId(), obj))
            throw new Exception("The id already exists!");
    }

    public void remove(Integer id) throws Exception {
        if (!repo.remove(id))
            throw new Exception("This id doesn't exist!");
    }

    public void update(TElem obj) throws Exception {
        if (!repo.update(obj.getId(), obj))
            throw new Exception("The id already exists!");
    }

    public TElem getById(Integer id) throws Exception {
        var piu = repo.getElem(id);
        if (piu == null)
            throw new Exception("This id doesn't exist!");
        else
            return piu;
    }

    public boolean checkExistence(Integer id) throws Exception {
        var piu = repo.getElem(id);
        return piu != null;
    }

    public int size() {
        return repo.size();
    }

    public ArrayList<TElem> getAll() {
        return repo.getAll();
    }
}
