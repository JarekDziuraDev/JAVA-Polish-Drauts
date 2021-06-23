package menu;
import menu.item.SimpleItemMenu;
import utils.MenuConstValue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class Menu<T extends SimpleItemMenu> {
    private ArrayList<T> itemMenuList;
    private String input;
    private Scanner scan;
    private int options;


    public Menu(ArrayList<T> itemMenuList) {

        this.itemMenuList = new ArrayList<>();
        scan = new Scanner(System.in);
        this.options = 0;

        this.itemMenuList = itemMenuList;
    }
    public void printItemMenuList() {
        for (SimpleItemMenu sIM: itemMenuList) {
            System.out.println(sIM.getValue() + " - "+ sIM.getName());
        }
        System.out.print("Enter your choice: ");
    }
    private String inputMenuOption() {

        String tmpInput = scan.nextLine();
        return tmpInput;
    }

    private boolean validateTypeOptions() {
        try {
            this.options = Integer.parseInt(this.input);
            return true;
        }
        catch (NumberFormatException e) {
            System.out.println("Error - enter an integer value.");
        }
        return false;
    }

    private boolean validateValueOptions() {
        if (this.options >= 1 && this.options < itemMenuList.size()) {
            return true;
        }
        System.out.println("Invalid value of options menu!");
        return false;
    }

    public int RunMenu() {
        this.printItemMenuList();

        input = inputMenuOption();

        if (! validateTypeOptions()) {
            return MenuConstValue.error.getValue();
        }

        if (! validateValueOptions()) {
            return MenuConstValue.error.getValue();
        }
        return this.options;
    }
}
