package utils;

import java.io.IOException;

public class Cls {
    public static void clear() throws IOException {
        try {
            new ProcessBuilder("cmd","/c","cls").inheritIO().start().waitFor();
        } catch(Exception e) {
            System.out.println(e);
        }
    }
}
