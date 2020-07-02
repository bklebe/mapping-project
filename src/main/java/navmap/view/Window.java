package navmap.view;

import java.util.List;
import javax.swing.*;
import navmap.model.Graph;
import navmap.model.Vertex;

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
