package navmap.model;

/*
 * Author: Beatrice Klebe
 * NetID: bklebe
 */

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public final class Vertex {
  private final String id;
  private final double latitude;
  private final double longitude;
  private final List<Edge> neighbors;

  public Vertex(final String line) {
    final List<String> splat = Arrays.asList(line.split("\\t"));
    id = splat.get(1);
    latitude = Double.parseDouble(splat.get(2));
    longitude = Double.parseDouble(splat.get(3));
    neighbors = new LinkedList<>();
  }

  private Vertex(double latitude, double longitude) {
    id = null;
    neighbors = null;
    this.latitude = latitude;
    this.longitude = longitude;
  }

  List<Vertex> getAdjacent() {
    return neighbors.stream()
        .map(
            n -> {
              if (n.getStart().equals(this)) {
                return n.getEnd();
              }
              return n.getStart();
            })
        .collect(Collectors.toList());
  }

  boolean equals(Vertex v) {
    return id.equals(v.getId());
  }

  double distanceTo(Vertex v) {
    List<Double> distances =
        neighbors.stream()
            .filter(e -> e.getEnd().equals(v) || e.getStart().equals(v))
            .map(Edge::getWeight)
            .collect(Collectors.toList());

    return distances.get(0) == null ? Double.MAX_VALUE : distances.get(0);
  }

  public Vertex scale(double scale, double latMin, double longMin) {
    double scaledLat = (this.latitude - latMin) * scale;
    double scaledLong = (this.longitude - longMin) * scale;
    return new Vertex(scaledLat, scaledLong);
  }

  @Override
  public String toString() {
    return id;
  }

  void addEdge(Edge e) {
    neighbors.add(e);
  }

  public String getId() {
    return id;
  }

  public double getLat() {
    return latitude;
  }

  public double getLong() {
    return longitude;
  }
}
