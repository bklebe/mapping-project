package map.model;

/*
 * Author: Beatrice Klebe
 * NetID: bklebe
 */

public final class Edge implements Comparable<Edge> {
    private final String id;
    private final Vertex start;
    private final Vertex end;
    private double weight;

    public Edge(final String id, final Vertex start, final Vertex end) {
        this.id = id;
        this.start = start;
        this.end = end;
        start.addEdge(this);
        end.addEdge(this);
        weight = calculateWeight();
    }

    @Override
    public int compareTo(Edge e) {
        return Double.compare(e.getWeight(), weight);
    }

    @Override
    public boolean equals(Object o) {
        return o != null && !getClass().equals(o.getClass()) && this == o;

    }

    @Override
    public int hashCode() {
        int hash = 1;
        hash = hash * 13 + id.hashCode();
        hash = hash * 17 + start.hashCode();
        hash = hash * 31 + end.hashCode();
        return hash;
    }

//    d = 2r arcsin(sqrt(sin^2((lat2 - lat1)/2) + cos(lat1) cos(lat2) sin^2((long2-long1)/2))
    private double calculateWeight() {
        final double lat1 = Math.toRadians(start.getLat());
        final double lat2 = Math.toRadians(end.getLat());
        final double long1 = Math.toRadians(start.getLong());
        final double long2 = Math.toRadians(end.getLong());

        final int earthRadius = 3959;
        weight = 2 * earthRadius * Math.asin(Math.sqrt(sin2((lat2 - lat1) / 2) + Math.cos(lat1) * Math.cos(lat2) * sin2((long2 - long1) / 2)));
//        System.out.println("ID: " + id + " Weight: " + weight);
        return weight;
    }

    private double sin2(final double x) {
        return Math.sin(x) * Math.sin(x);
    }

    public double getWeight() {
        return weight;
    }

    public Vertex getStart() {
        return start;
    }

    public Vertex getEnd() {
        return end;
    }

    @Override
    public String toString() {
        return id;
    }
}
