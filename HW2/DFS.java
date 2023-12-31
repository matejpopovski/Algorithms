
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

/**
 * This program implements a depth-first search algorithm. The user provides 
 * input to construct one or more graphs, and the program outputs the result of 
 * the DFS traversal of the graph(s).
 */
public class DFS {

        public static void main(String[] args) {
        parse_input();

        for (Graph graph : graphs) {
            topOrder(graph);
        }
    }

    public static class Graph {

        private LinkedHashMap<String, Vertex> vertices;
        private List<String> verticesData;
    
        /*
         * A constructor with no arguments, as a default.
         */
        public Graph() {
            
            vertices = new LinkedHashMap<String, Vertex>();
            verticesData = new ArrayList<String>();
        }
    
        /**
         * A private class for inner vertices.
         *
         * @author Matej Popovski
         *
         */
        private class Vertex {
            List<String> adjacencies; 
    
            /**
             * Vertex constructor 
             *
             * @param s String data to hold
             */
            Vertex () {
                adjacencies = new ArrayList<String>();
            }
        }
    
        /**
         * Adding a new vertex to the graph.
         *
         * If vertex is null or already exists,
         * method ends without adding a vertex or 
         * throwing an exception.
         *
         * Valid argument conditions:
         * 1. vertex is non-null
         * 2. vertex is not already in the graph 
         */
        public void addVertex(String vertex) {
            Vertex v = new Vertex();
            vertices.put(vertex, v);
            verticesData.add(vertex);
        }
    
        /**
         * Modifying the adjacency list of vertex1 by adding vertex2 to it.
         *
         */
        public void addEdge(String vertex1, String vertex2) {
    
            if (vertex1 == null || vertex2 == null) {
                return;
            }
    
           
            Vertex v1 = getVertex(vertex1);
            v1.adjacencies.add(vertex2);
        }
    
        /**
         * Returns a List that contains all the vertices
         *
         */
        public List<String> getAllVertices() {
            return verticesData;
        }
    
        /**
         * Gets the first vertex in the graph, which should be lexographically the least
         * 
         * @return the first vertex
         */
        public String getFirstVertex() {
            List<String> v = verticesData;
            return v.get(0);
        }
    
        /**
         * Get the adjacency list of a vertex 
         * 
         * @param vertex to get list for 
         * @return the adjacency list of vertex
         */
        public List<String> getAdjacencyList(String vertex) {
            Vertex v = getVertex(vertex);
            return v.adjacencies;
        }
    
        /**
         * Gets the vertex node from graph given string
         *
         * @param data string to search for in vertex set
         * @return vertex object with the given string
         */
        private Vertex getVertex(String data) {
            return vertices.get(data);
        }
    }
    
    private static Graph[] graphs;  

    private static void parse_input() {
        Scanner input = new Scanner(System.in);

        int numInstances = input.nextInt();
        graphs = new Graph[numInstances]; 

        for (int numInstance = 0; numInstance < numInstances; numInstance++) {
            graphs[numInstance] = new Graph();
            int numNodes = input.nextInt(); 
            input.nextLine();
            
            while (numNodes > 0) {
                String line = input.nextLine();
                String[] vertices = line.split(" ");
                if (vertices.length > 1) {
                    graphs[numInstance].addVertex(vertices[0]);
                    for (int v = 1; v < vertices.length; v++) {
                        graphs[numInstance].addEdge(vertices[0], vertices[v]);
                    }
                } else { 
                    graphs[numInstance].addVertex(vertices[0]);
                }
                numNodes--;
            }
        }

        input.close();
    }

    /**
     * To iterate through all nodes in a graph, even if disconnected, we'll need to implement
     * topological ordering. 
     * 
     * @param graph the graph to iterate through
     */
    private static void topOrder(Graph graph) {
        HashMap<String, String> visited = new HashMap<String, String>(); 
        List<String> vertices = graph.getAllVertices(); 
        StringBuilder result = new StringBuilder(); 

        for (String vertex : vertices) {
            if (!visited.containsKey(vertex)) {
                depthFirstSearch(graph, visited, vertex, result); 
            }
        }

        String output = result.toString(); 
        System.out.println(output.substring(0, output.length() - 1)); 
    }

    /**
     * Iterative depth-first search. Prints DFS order of graph.
     * 
     * @param graph the graph to search over
     * @param visited list of nodes already visited
     * @param vertex the central node we are traversing from
     * @param result DFS result stored in string builder
     */
    private static void depthFirstSearch(Graph graph, HashMap<String, String> visited, 
    String vertex, StringBuilder result) {
        Stack<String> s = new Stack<String>();

        s.push(vertex);

        while(!s.isEmpty()) {
            String u = s.pop();
            
            if (!visited.containsKey(u)) {
                result.append(u + " ");
                visited.put(u, "1"); 
            }

            List<String> neighbors = graph.getAdjacencyList(u);

            for (int i = neighbors.size() - 1; i >= 0; i--) { 
                String neighbor = neighbors.get(i);
                if (!visited.containsKey(neighbor)) {
                    s.push(neighbor);
                }
            }
        }
    }
}