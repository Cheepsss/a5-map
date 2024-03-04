package Repository.Memory;

import Domain.Entity;
import Repository.IRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class EntityRepository<TElem extends Entity> implements IRepository<Integer, TElem> {
    protected Map<Integer, TElem> elements;

    public EntityRepository() {
        elements = new HashMap<Integer, TElem>();
    }

    @Override
    public boolean add(Integer id, TElem obj) {
        return elements.put(id, obj) == null;
    }

    @Override
    public boolean remove(Integer id) {
        return elements.remove(id) != null;
    }

    @Override
    public boolean update(Integer id, TElem obj) {
        return elements.replace(id, obj) != null;
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

    public void setAllMap(Map<Integer, TElem> m) {
        this.elements = m;
    }
}
