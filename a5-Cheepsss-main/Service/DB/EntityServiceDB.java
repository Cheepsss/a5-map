package Service.DB;

import Domain.Entity;
import Repository.DB.DatabaseRepository;

import java.util.ArrayList;

public abstract class EntityServiceDB<TElem extends Entity> {

    private final DatabaseRepository<TElem> repo;

    public EntityServiceDB(DatabaseRepository<TElem> repo) {
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
            throw new Exception("The id doesn't exists!");
    }

    public TElem getById(Integer id) throws Exception {
        var piu = repo.getElem(id);
        if (piu == null)
            throw new Exception("This id doesn't exist!");
        else
            return piu;
    }

    public int size() {
        return repo.size();
    }

    public ArrayList<TElem> getAll() {
        return repo.getAll();
    }

    public TElem getElem(Integer id){
        return repo.getElem(id);
    }

    public boolean isThere(Integer id) {
        return repo.isThere(id);
    }
}
