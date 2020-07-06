package navmap.controller;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import navmap.model.Edge;
import navmap.model.Graph;
import navmap.model.Vertex;
import navmap.view.Window;

public final class Launcher {
  private final PrintStream output;
  private List<Vertex> shortestPath;
  private boolean show;
  private boolean directions;
  private final boolean empty;
  private String origin;
  private String destination;
  private Path mapFile;
  private Stream<String> lines;

  public Launcher(final String[] args, PrintStream output) {
    List<String> arguments = Arrays.asList(args);
    this.empty = arguments.isEmpty();
    this.show = arguments.contains("-show");
    this.directions = arguments.contains("-directions");
    this.output = output;
    if (!this.empty) {
      this.origin = arguments.get(3);
      this.destination = arguments.get(4);
      this.mapFile = Paths.get(arguments.get(0));
      try {
        this.lines = Files.lines(mapFile);
      } catch (IOException e) {
        this.lines = null;
      }
    }
  }

  public void launch() {
    if (empty) {
      this.output.println(
          "Usage: Graph data.txt [-show] [-directions startIntersection endIntersection] [-meridianmap]");
    } else {

      final List<Vertex> vertices = getVertices();

      final Map<String, Vertex> vertexMap =
          vertices.stream().collect(Collectors.toMap(Vertex::getId, v -> v));

      final List<Edge> edges = getEdges(vertexMap);

      Graph model = new Graph(vertices, edges, vertexMap);

      shortestPath = new ArrayList<>();

      if (directions) {
        // args 3 and 4
        shortestPath = model.shortestPath(this.origin, this.destination);
      }

      if (show) {
        new Window(model, shortestPath);
      }
    }
  }

  private List<Edge> getEdges(Map<String, Vertex> vertexMap) {
    var r = Pattern.compile("^r");
    return this.lines
        .filter(r.asPredicate())
        .map(
            s -> {
              final List<String> splat = Arrays.asList(s.split("\\t"));
              return new Edge(
                  splat.get(1), vertexMap.get(splat.get(2)), vertexMap.get(splat.get(3)));
            })
        .collect(Collectors.toList());
  }

  private List<Vertex> getVertices() {
    var i = Pattern.compile("^i");
    return this.lines.filter(i.asPredicate()).map(Vertex::new).collect(Collectors.toList());
  }
}
