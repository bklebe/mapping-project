package map.controller;

/*
 * Author: Beatrice Klebe
 * NetID: bklebe
 */

import map.model.Edge;
import map.model.Graph;
import map.model.Vertex;
import map.view.Window;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public final class Launcher {
    private List<Vertex> shortestPath;

    public Launcher(final List<String> args) throws IOException {
        final boolean show = args.contains("-show");
        final boolean directions = args.contains("-directions");
//        final boolean meridianMap = args.contains("-meridianmap");
        if (args.isEmpty()) {
            System.out.println("Usage: Graph data.txt [-show] [-directions startIntersection endIntersection] [-meridianmap]");
        } else {
            String fileName = args.get(0);
            Path mapFile = Paths.get(fileName);
//            List<String> mapData = null;
            final Pattern i = Pattern.compile("^i");
            final Pattern r = Pattern.compile("^r");

            final List<Vertex> vertices = Files.lines(mapFile)
                                               .filter(i.asPredicate())
                                               .map(Vertex::new)
                                               .collect(Collectors.toList());

            final Map<String, Vertex> vertexMap = vertices.stream()
                                                          .collect(Collectors.toMap(Vertex::getId, v -> v));

            final List<Edge> edges = Files.lines(mapFile)
                                          .filter(r.asPredicate())
                                          .map(s -> {
                                              final List<String> splat = Arrays.asList(s.split("\\t"));
                                              return new Edge(splat.get(1), vertexMap.get(splat.get(2)), vertexMap.get(splat.get(3)));
                                          })
                                          .collect(Collectors.toList());

            Graph model = new Graph(vertices, edges, vertexMap);

            shortestPath = new ArrayList<>();

            if (directions) {
                // args 3 and 4
                shortestPath = model.shortestPath(args.get(3), args.get(4));
            }

            if (show) {
                new Window(model, shortestPath);
            }
        }
    }
}

