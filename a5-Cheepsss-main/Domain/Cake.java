package Domain;

import java.io.Serializable;

public class Cake extends Entity implements Serializable {

    public static final long serialVersionUID = 123L;
    private String name;
    private String ingredients;
    private String type;

    public Cake(int id, String name, String ingredients, String type) {
        super(id);
        this.name = name;
        this.ingredients = ingredients;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    @Override
    public String toString() {
        return "Cake{" + '\n' +
                "id = " + getId() + '\n' +
                "name = " + name + '\n' +
                "type = " + type + '\n' +
                "ingredients = " + ingredients + '\n' +
                '}' + '\n';
    }
}
