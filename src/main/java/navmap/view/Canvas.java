package navmap.view;

import java.awt.*;
import java.awt.geom.Line2D;
import java.util.List;
import javax.swing.*;
import navmap.model.Graph;
import navmap.model.Vertex;

class Canvas extends JComponent {
  private final transient Graph model;
  private final transient List<Vertex> shortestPath;

  Canvas(final Graph model, final List<Vertex> shortestPath) {
    super();
    this.model = model;
    this.shortestPath = shortestPath;
  }

  @Override
  public void paintComponent(Graphics g) {
    Graphics2D g2d = (Graphics2D) g;
    g2d.scale(1.0, -1.0);
    g2d.translate(0, -getHeight());
    double scale = getWidth() / (model.getLongRange());
    g2d.setColor(Color.WHITE);

    g2d.fillRect(0, 0, getWidth(), getHeight());
    g2d.setColor(Color.BLACK);
    model
        .getEdges()
        .forEach(
            e -> {
              Vertex scaledStart = e.getStart().scale(scale, model.getLatMin(), model.getLongMin());
              Vertex scaledEnd = e.getEnd().scale(scale, model.getLatMin(), model.getLongMin());
              g2d.draw(
                  new Line2D.Double(
                      scaledStart.getLong(),
                      scaledStart.getLat(),
                      scaledEnd.getLong(),
                      scaledEnd.getLat()));
            });

    g2d.setColor(Color.RED);
    g2d.setStroke(new BasicStroke(2));
    shortestPath.stream()
        .skip(1)
        .forEach(
            v -> {
              Vertex scaledStart =
                  shortestPath
                      .get(shortestPath.indexOf(v) - 1)
                      .scale(scale, model.getLatMin(), model.getLongMin());
              Vertex scaledEnd = v.scale(scale, model.getLatMin(), model.getLongMin());
              g2d.draw(
                  new Line2D.Double(
                      scaledStart.getLong(),
                      scaledStart.getLat(),
                      scaledEnd.getLong(),
                      scaledEnd.getLat()));
            });
  }
}
