import game.Game;
import menu.item.SimpleItemMenu;
import menu.Menu;

import utils.MenuConstValue;
import utils.Cls;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import java.io.*;
import java.util.InputMismatchException;
import java.util.Scanner;

import static utils.Cls.*;

public class Main {
    public static void main(String[] args) throws InterruptedException, IOException {
        SimpleItemMenu newGame = new SimpleItemMenu("New Game", MenuConstValue.newGame);
        SimpleItemMenu credits = new SimpleItemMenu("Credits", MenuConstValue.credits);
        SimpleItemMenu quit = new SimpleItemMenu("Quit", MenuConstValue.quit);


        ArrayList<SimpleItemMenu> itemList = new ArrayList<>();
        itemList.add(newGame);
        itemList.add(credits);
        itemList.add(quit);

        Menu mainMenu = new Menu(itemList);
        Game game = new Game();

        MenuConstValue menuOptions = MenuConstValue.defaultValue;
        boolean quitCondition = false;
        boolean quitGameCondition = false;
        int options = 0;

        while(!quitCondition) {

            options = mainMenu.RunMenu();

            if (options == MenuConstValue.newGame.getValue()) {

                while(!quitGameCondition) {
                    game.drawChessBoard();
                    game.runGame();
                }
            }
        }

    }
}


