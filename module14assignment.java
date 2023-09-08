import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;

public class GraphAlgorithms {

  /**
   * Runs Prim's algorithm on the given graph and returns the Minimum
   * Spanning Tree (MST) in the form of a set of Edges. If the graph is
   * disconnected and therefore no valid MST exists, return null.
   *
   * You may assume that the passed in graph is undirected. In this framework,
   * this means that if (u, v, 3) is in the graph, then the opposite edge
   * (v, u, 3) will also be in the graph, though as a separate Edge object.
   *
   * The returned set of edges should form an undirected graph. This means
   * that every time you add an edge to your return set, you should add the
   * reverse edge to the set as well. This is for testing purposes. This
   * reverse edge does not need to be the one from the graph itself; you can
   * just make a new edge object representing the reverse edge.
   *
   * You may assume that there will only be one valid MST that can be formed.
   *
   * You should NOT allow self-loops or parallel edges in the MST.
   *
   * You may import/use java.util.PriorityQueue, java.util.Set, and any
   * class that implements the aforementioned interface.
   *
   * DO NOT modify the structure of the graph. The graph should be unmodified
   * after this method terminates.
   *
   * The only instance of java.util.Map that you may use is the adjacency
   * list from graph. DO NOT create new instances of Map for this method
   * (storing the adjacency list in a variable is fine).
   *
   * You may assume that the passed in start vertex and graph will not be null.
   * You may assume that the start vertex exists in the graph.
   *
   * @param <T>   The generic typing of the data.
   * @param start The vertex to begin Prims on.
   * @param graph The graph we are applying Prims to.
   * @return The MST of the graph or null if there is no valid MST.
   */
  public static <T> Set<Edge<T>> prims(Vertex<T> start, Graph<T> graph) {
    // initialize priority queue, visited list, and MST edge set
    Set<Vertex<T>> visitedSet = new HashSet<>();
    Set<Edge<T>> MST = new HashSet<>();
    PriorityQueue<Edge<T>> priorityQueue = new PriorityQueue<>();

    // put starting vertex's edges into priority queue
    addAdjEdgesToPQ(start, graph, priorityQueue, visitedSet);

    // mark starting vertex as visited
    visitedSet.add(start);

    while (!priorityQueue.isEmpty() && visitedSet.size() < graph.getVertices().size()) {
      Edge<T> edge = priorityQueue.poll();
      Vertex<T> destination = edge.getV();
      if (!visitedSet.contains(destination)) {
        MST.add(edge);
        MST.add(createReverseEdge(edge));
        visitedSet.add(destination);

        addAdjEdgesToPQ(destination, graph, priorityQueue, visitedSet);
      }

    }

    if (MST.size() / 2 < graph.getVertices().size() - 1) {
      return null;
    } else {
      return MST;
    }
  }

  private static <T> void addAdjEdgesToPQ(Vertex<T> vertex, Graph<T> graph, PriorityQueue<Edge<T>> priorityQueue, Set<Vertex<T>> visitedSet) {
    List<VertexDistance<T>> connectedVertices = graph.getAdjList().get(vertex);
    for (int i = 0; i < connectedVertices.size(); i++) {
      Vertex<T> destination = connectedVertices.get(i).getVertex();
      int distance = connectedVertices.get(i).getDistance();
      if (!visitedSet.contains(destination)) {
        Edge<T> edge = new Edge<>(vertex, destination, distance);
        priorityQueue.add(edge);
      }
    }
  }

  private static <T> Edge<T> createReverseEdge(Edge<T> edge) {
    Edge<T> reversedEdge = new Edge<>(edge.getV(), edge.getU(), edge.getWeight());
    return reversedEdge;
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

/**
 * A class representing a directed graph, with a vertex set, edge set, and
 * an adjacency list.
 *
 * DO NOT EDIT THIS CLASS!!!
 *
 * @author CS 1332 TAs
 * @version 1.0
 */
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

/**
 * Class representing a vertex.
 *
 * DO NOT EDIT THIS CLASS!!!
 *
 * @author CS 1332 TAs
 * @version 1.0
 */
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

/**
 * Class to store a vertex in a graph and an integer associated with it
 * representing the distance to this vertex from some other vertex.
 *
 * DO NOT EDIT THIS CLASS!!!
 *
 * @author CS 1332 TAs
 * @version 1.0
 */
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