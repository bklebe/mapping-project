package map.view;

/*
 * Author: Beatrice Klebe
 * NetID: bklebe
 */

import map.model.Graph;
import map.model.Vertex;

import javax.swing.*;
import java.util.List;

public class Window extends JFrame {
    public Window(final Graph model, final List<Vertex> path) {
        Canvas canvas = new Canvas(model, path);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(800, 500);
        setTitle("Map");
        add(canvas);
        setVisible(true);
    }
}
