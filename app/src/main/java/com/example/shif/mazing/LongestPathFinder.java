package com.example.shif.mazing;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Finds the longest path from the starting point of the maze (lower left corner)
 * to a cell in the circumference of the maze.
 * When the solution of the maze is a longer path, the maze is more satisfying to solve.
 * Returns an array with the keys of the vertices along the solution path,
 * which is then used by FingerLine to detect when the user solves the maze.
 */
public class LongestPathFinder {

    public static Integer[] findLongestPath(Graph graph) {

        Graph transposedGraph = graph.negateGraph();

        ArrayList<Integer> resultArray = new ArrayList<Integer>();

        double n = Math.sqrt(transposedGraph.size);
        int startIndex = ( (Double) (transposedGraph.size - n) ).intValue();

        Vertex start = transposedGraph.V[startIndex]; // To start from the top left corner, choose index 0

        int maxDistance = 0;
        boolean isOuter;
        boolean isStart;
        Vertex farthestVertex = start;
        start.setColor(MainActivity.Color.GRAY);
        start.setDistance(0);
        // Create an empty queue Q
        Queue<Vertex> Q = new LinkedList<Vertex>();
        // insert start to Q
        Q.add(start);

        while (!Q.isEmpty()) {
            Vertex u = Q.remove();

            for (int i = 0; i < u.edges.length; i++) {

                // First make sure the adjacency list is not empty
                if (u.edges.length > 0) {

                    if (transposedGraph.V[u.edges[i]].color == MainActivity.Color.WHITE) {
                        transposedGraph.V[u.edges[i]].setColor(MainActivity.Color.GRAY);
                        transposedGraph.V[u.edges[i]].setDistance(u.distance + 1);
                        isOuter = ((u.edges[i] + 1) % n < 2 || u.edges[i] < n || u.edges[i] > (n * (n - 1) - 1));
                        isStart = (transposedGraph.V[u.edges[i]].equals(start));

                        if (transposedGraph.V[u.edges[i]].distance > maxDistance && isOuter && !isStart) {
                            maxDistance = u.distance + 1;
                            farthestVertex = transposedGraph.V[u.edges[i]];
                        }
                        transposedGraph.V[u.edges[i]].setPrevious(u);
                        Q.add(transposedGraph.V[u.edges[i]]);
                    }
                }

            }
            u.setColor(MainActivity.Color.BLACK);
        }

        // Retrieve a list of verteces keys
        Vertex currVertex = farthestVertex;
        resultArray.add(currVertex.key);
        while (currVertex.previous != null) {
            currVertex = currVertex.previous;
            resultArray.add(currVertex.key);
        }
        Integer[] result = new Integer[resultArray.size()];
        resultArray.toArray(result);
        return result;
    }
}
