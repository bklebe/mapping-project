package map.model;

/*
 * Author: Beatrice Klebe
 * NetID: bklebe
 */

import java.security.InvalidParameterException;
import java.util.*;
import java.util.stream.Collectors;

public final class Graph {
    private final List<Vertex> vertices;
    private final Map<String, Vertex> vertexMap;
    private final List<Edge> edges;
    private final double longRange;
    private final double latMin;
    private final double longMin;
//    private List<Edge> spanningTree;

    public Graph(final List<Vertex> vertices, final List<Edge> edges, final Map<String, Vertex> vertexMap) {
        this.vertices = vertices;
        this.edges = edges;
        this.vertexMap = vertexMap;

        final Comparator<Vertex> latComp = Comparator.comparingDouble(Vertex::getLat);
        final Comparator<Vertex> longComp = Comparator.comparingDouble(Vertex::getLong);

        final double border = 0.25;

        latMin = Collections.min(vertices, latComp).getLat() - border;
//        final double latMax = Collections.max(vertices, latComp).getLat() + border;
        longMin = Collections.min(vertices, longComp).getLong() - border;
        final double longMax = Collections.max(vertices, longComp).getLong() + border;

        longRange = longMax - longMin;
    }


    public List<Vertex> shortestPath(String i1, String i2) {
        final Vertex start = vertexMap.get(i1);
        final Vertex target = vertexMap.get(i2);
        final Map<Vertex, Double> distanceMap = vertices.stream().collect(Collectors.toMap(v -> v, v -> Double.MAX_VALUE));
        distanceMap.put(start, 0.0);
        final Comparator<Vertex> distanceComp = Comparator.comparingDouble(distanceMap::get);
        final Comparator<Vertex> idComp = distanceComp.thenComparing(Vertex::getId);
        final Map<Vertex, Vertex> prev = new HashMap<>();

        PriorityQueue<Vertex> unvisited = new PriorityQueue<>(idComp);
        unvisited.addAll(vertices);

        if (vertexMap.get(i1) == null || vertexMap.get(i2) == null) {
            throw new InvalidParameterException("At least one of the provided intersections is invalid.");
        }

        Vertex current = start;
        do {
            if (current.equals(target)) {
                break;
            }
            updateNearestNeighbors(prev, unvisited, distanceMap, current);
            current = unvisited.poll();

        } while (!unvisited.isEmpty());

        List<Vertex> path = new ArrayList<>();

        Vertex u = target;

        while (prev.get(u) != null) {
            path.add(u);
            u = prev.get(u);
        }

        path.add(start);
        Collections.reverse(path);
        path.forEach(System.out::println);

        System.out.println("Total Distance: " + calculateDistance(path));
        return path;
    }

    private double calculateDistance(final List<Vertex> path) {
        double sum = 0;
        for (int i = 1; i < path.size(); i++) {
            sum += path.get(i - 1).distanceTo(path.get(i));
        }

//        path.stream().mapToInt(v -> v.distanceTo())

        return sum;
    }

    private void updateNearestNeighbors(final Map<Vertex, Vertex> prev, final PriorityQueue<Vertex> unvisited, final Map<Vertex, Double> distanceMap, final Vertex current) {
        current.getAdjacent().forEach(n -> {
            final double tentative = distanceMap.get(current) + current.distanceTo(n);
            if (tentative < distanceMap.get(n)) {
                distanceMap.put(n, tentative);
                unvisited.add(n);
                prev.put(n, current);
            }
        });
    }

//    private void minimumSpanningTree() {
//    }


//    private void printVertices() {
//        vertices.forEach(Vertex::printEdges);
//    }

    public List<Edge> getEdges() {
        return edges;
    }

    public double getLatMin() {
        return latMin;
    }

    public double getLongMin() {
        return longMin;
    }

//    public double getLatRange() {
//        return latRange;
//    }

    public double getLongRange() {
        return longRange;
    }
}
