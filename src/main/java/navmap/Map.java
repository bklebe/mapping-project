package navmap;

/*
 * Author: Beatrice Klebe
 * NetID: bklebe
 */

import navmap.controller.Launcher;

import java.io.IOException;
import java.util.Arrays;

final class Map {
    public static void main(final String[] args) {
        try {
            new Launcher(Arrays.asList(args));
        } catch (IOException e) {
            System.err.println("Could not load file.");
            e.printStackTrace();
        }
    }
}
