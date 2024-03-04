package Repository;

import java.util.ArrayList;

public interface IRepository<ID, TElem> {

    public abstract boolean add(ID id, TElem obj);

    public abstract boolean update(ID id, TElem obj);

    public abstract boolean remove(ID id);

    public abstract int size();

    public abstract ArrayList<TElem> getAll();

    public abstract TElem getElem(ID id);
}
