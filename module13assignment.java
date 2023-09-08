import java.util.Set;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.Queue;

public class GraphAlgorithms {

    /**
     * Performs a breadth first search (bfs) on the input graph, starting at
     * the parameterized starting vertex.
     *
     * When exploring a vertex, explore in the order of neighbors returned by
     * the adjacency list. Failure to do so may cause you to lose points.
     *
     * You may import/use java.util.Set, java.util.List, java.util.Queue, and
     * any classes that implement the aforementioned interfaces, as long as they
     * are efficient.
     *
     * The only instance of java.util.Map that you should use is the adjacency
     * list from graph. DO NOT create new instances of Map for BFS
     * (storing the adjacency list in a variable is fine).
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * You may assume that the passed in start vertex and graph will not be null.
     * You may assume that the start vertex exists in the graph.
     *
     * @param <T>   The generic typing of the data.
     * @param start The vertex to begin the bfs on.
     * @param graph The graph to search through.
     * @return List of vertices in visited order.
     */
    public static <T> List<Vertex<T>> bfs(Vertex<T> start, Graph<T> graph) {
        ArrayList<Vertex<T>> visitedList = new ArrayList<>();
        Queue<Vertex<T>> queue = new LinkedList<>();
        Map<Vertex<T>, List<VertexDistance<T>>> adjList = graph.getAdjList();

        visitedList.add(start);
        queue.add(start);
        // while (queue.peek() != null && visitedList.size() !=
        // graph.getVertices().size()) {
        while (queue.peek() != null) {
            Vertex<T> v = queue.remove();
            List<VertexDistance<T>> neighbors = adjList.get(v);

            for (int i = 0; i < neighbors.size(); i++) {
                Vertex<T> neighbor = neighbors.get(i).getVertex();
                if (!visitedList.contains(neighbor)) {
                    visitedList.add(neighbor);
                    queue.add(neighbor);
                }
            }
        }

        return visitedList;
    }

    /**
     * Performs a depth first search (dfs) on the input graph, starting at
     * the parameterized starting vertex.
     *
     * When exploring a vertex, explore in the order of neighbors returned by
     * the adjacency list. Failure to do so may cause you to lose points.
     *
     * NOTE: This method should be implemented recursively. You may need to
     * create a helper method.
     *
     * You may import/use java.util.Set, java.util.List, and any classes that
     * implement the aforementioned interfaces, as long as they are efficient.
     *
     * The only instance of java.util.Map that you may use is the adjacency list
     * from graph. DO NOT create new instances of Map for DFS
     * (storing the adjacency list in a variable is fine).
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * You may assume that the passed in start vertex and graph will not be null.
     * You may assume that the start vertex exists in the graph.
     *
     * @param <T>   The generic typing of the data.
     * @param start The vertex to begin the dfs on.
     * @param graph The graph to search through.
     * @return List of vertices in visited order.
     */
    public static <T> List<Vertex<T>> dfs(Vertex<T> start, Graph<T> graph) {
        ArrayList<Vertex<T>> visitedList = new ArrayList<>();
        dfsHelper(start, graph, visitedList);
        return visitedList;
    }

    private static <T> void dfsHelper(Vertex<T> vertex, Graph<T> graph, List<Vertex<T>> visitedList) {
        visitedList.add(vertex);

        List<VertexDistance<T>> neighbors = graph.getAdjList().get(vertex);
        for (int i = 0; i < neighbors.size(); i++) {
            Vertex<T> neighbor = neighbors.get(i).getVertex();
            if (!visitedList.contains(neighbor)) {
                dfsHelper(neighbor, graph, visitedList);
            }
        }
    }
}

public class Edge<T> implements Comparable<Edge<? super T>> {

    private Vertex<T> u;
    private Vertex<T> v;
    private int weight;

    /**
     * Creates a directed edge from vertex u to vertex v. Any single edge is
     * always directed, so if you're trying to create an undirected edge, you
     * must create the edges (u, v, weight) and (v, u, weight) when creating the
     * graph.
     *
     * @param u      The start vertex of the edge.
     * @param v      The end vertex of the edge.
     * @param weight The weight value of the edge.
     * @throws IllegalArgumentException if any of the arguments are null.
     */
    public Edge(Vertex<T> u, Vertex<T> v, int weight) {
        if (u == null || v == null) {
            throw new IllegalArgumentException("Arguments cannot be null.");
        }
        this.u = u;
        this.v = v;
        this.weight = weight;
    }

    /**
     * Gets the weight.
     *
     * @return The weight.
     */
    public int getWeight() {
        return weight;
    }

    /**
     * Gets the starting vertex (u).
     *
     * @return The starting vertex (u).
     */
    public Vertex<T> getU() {
        return u;
    }

    /**
     * Gets the ending vertex(v).
     *
     * @return The ending vertex (v).
     */
    public Vertex<T> getV() {
        return v;
    }

    @Override
    public boolean equals(Object o) {
        if (o != null && o instanceof Edge<?>) {
            Edge<?> e = (Edge<?>) o;
            return weight == e.weight && u.equals(e.u) && v.equals(e.v);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return u.hashCode() ^ v.hashCode() ^ weight;
    }

    @Override
    public int compareTo(Edge<? super T> e) {
        return weight - e.getWeight();
    }

    @Override
    public String toString() {
        return "Edge from " + u + " to " + v + " with weight " + weight;
    }
}

public class Graph<T> {

    private Set<Vertex<T>> vertices;
    private Set<Edge<T>> edges;
    private Map<Vertex<T>, List<VertexDistance<T>>> adjList;

    /**
     * Builds the graph from a set of vertices and an edge list. All edges in
     * the edge set are assumed to be directed, so if you want to create an
     * undirected edge, the edge set must contain both the forward and backwards
     * edges.
     *
     * @param vertices The vertex set.
     * @param edges    The edge set.
     * @throws IllegalArgumentException If any of the arguments are null or if
     *                                  the vertex set doesn't contain all of the
     *                                  vertices.
     */
    public Graph(Set<Vertex<T>> vertices, Set<Edge<T>> edges) {
        if (vertices == null || edges == null) {
            throw new IllegalArgumentException("Arguments cannot be null.");
        }

        this.vertices = new HashSet<>(vertices);
        this.edges = new HashSet<>(edges);
        adjList = new HashMap<>();
        for (Vertex<T> v : vertices) {
            adjList.put(v, new ArrayList<>());
        }

        for (Edge<T> e : edges) {
            if (adjList.containsKey(e.getU())) {
                adjList.get(e.getU()).add(new VertexDistance<>(e.getV(), e.getWeight()));
            } else {
                throw new IllegalArgumentException("Vertex set must contain all vertices of the graph.");
            }
        }
    }

    /**
     * Gets the vertex set.
     *
     * @return The vertex set.
     */
    public Set<Vertex<T>> getVertices() {
        return vertices;
    }

    /**
     * Gets the edge set.
     *
     * @return The edge set.
     */
    public Set<Edge<T>> getEdges() {
        return edges;
    }

    /**
     * Gets the adjacency list.
     *
     * @return The adjacency list.
     */
    public Map<Vertex<T>, List<VertexDistance<T>>> getAdjList() {
        return adjList;
    }
}

public class Vertex<T> {

    private T data;

    /**
     * Creates a Vertex object holding the given data.
     *
     * @param data The object that is stored in this Vertex.
     * @throws IllegalArgumentException If data is null.
     */
    public Vertex(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null.");
        }
        this.data = data;
    }

    /**
     * Gets the data.
     *
     * @return The data of this vertex.
     */
    public T getData() {
        return data;
    }

    @Override
    public boolean equals(Object o) {
        if (o != null && o instanceof Vertex) {
            return data.equals(((Vertex<?>) o).data);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return data.hashCode();
    }

    @Override
    public String toString() {
        return data.toString();
    }
}

public final class VertexDistance<T> implements Comparable<VertexDistance<? super T>> {

    private final Vertex<T> vertex;
    private final int distance;

    /**
     * Creates a pairing of vertex and distance to that vertex.
     *
     * @param vertex   the Vertex to be stored.
     * @param distance the integer representing the distance to this Vertex
     *                 from the previous Vertex.
     */
    public VertexDistance(Vertex<T> vertex, int distance) {
        this.vertex = vertex;
        this.distance = distance;
    }

    /**
     * Gets the vertex.
     *
     * @return The vertex.
     */
    public Vertex<T> getVertex() {
        return vertex;
    }

    /**
     * Gets the distance
     *
     * @return The distance.
     */
    public int getDistance() {
        return distance;
    }

    @Override
    public boolean equals(Object o) {
        if (o != null && o instanceof VertexDistance<?>) {
            VertexDistance<?> e = (VertexDistance<?>) o;
            return distance == e.distance && vertex.equals(e.vertex);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return vertex.hashCode() ^ distance;
    }

    @Override
    public int compareTo(VertexDistance<? super T> pair) {
        return this.getDistance() - pair.getDistance();
    }

    @Override
    public String toString() {
        return "Pair with vertex " + vertex + " and distance " + distance;
    }
}