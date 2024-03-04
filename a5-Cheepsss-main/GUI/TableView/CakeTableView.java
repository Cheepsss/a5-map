package GUI.TableView;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class CakeTableView {

    private SimpleIntegerProperty cakeId;
    private SimpleStringProperty name;
    private SimpleStringProperty ingredients;
    private SimpleStringProperty type;

    public CakeTableView(Integer cakeId, String name, String ingredients, String type) {
        this.cakeId = new SimpleIntegerProperty(cakeId);
        this.name = new SimpleStringProperty(name);
        this.ingredients = new SimpleStringProperty(ingredients);
        this.type = new SimpleStringProperty(type);
    }

    public int getCakeId() {
        return cakeId.get();
    }

    public String getName() {
        return name.get();
    }

    public String getIngredients() {
        return ingredients.get();
    }

    public String getType() {
        return type.get();
    }

    @Override
    public String toString() {
        return "CakeTableView{" +
                "cakeId=" + cakeId +
                ", name=" + name +
                ", ingredients=" + ingredients +
                ", type=" + type +
                '}';
    }
}
