package menu.item;
import utils.MenuConstValue;

public class SimpleItemMenu {
    private String name;
    private MenuConstValue value;

    public SimpleItemMenu() {
        name = null;
        value = MenuConstValue.defaultValue;
    }
    public SimpleItemMenu(String name, MenuConstValue value) {
        this.name = name;
        this.value = value;
    }

    public int getValue() {
        return this.value.getValue();
    }
    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return "Menu Item: name= " + name + ", value= " + value + ";";
    }
}
