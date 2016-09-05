package com.example.shif.mazing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * The graph representation of the maze, using an adjacency list.
 * The constructor creates a graph where each vertex is a cell in a grid,
 * and each vertex (cell) has edges to all of its neighboring vertices (cells).
 * Method negateGraph creates a negative of an existing graph:
 * considering each vertex represents a cell in a grid,
 * for each vertex (cell) the method adds edges to neighboring cells (vertices) that are not connected
 * and removes existing edges.
 */
public class Graph {

    int size;
    Vertex[] V;

    public Graph(int n) {
        this.size = n*n;
        this.V = new Vertex[size];
        int counter = 0;

        for (int r = 0; r < n; r++) {
            for (int c = 0; c < n; c++) {

                V[counter] = new Vertex(counter);

                boolean top = (r == 0) ? false : true;
                boolean right = (c == n - 1) ? false : true;
                boolean bottom = (r == n - 1) ? false : true;
                boolean left = (c == 0) ? false : true;

                ArrayList<Integer> edgesArrayList = new ArrayList<Integer>();
                if (top) {
                    edgesArrayList.add(n * (r - 1) + c); // n * r + c
                }
                if (right) {
                    edgesArrayList.add(n * r + (c + 1));
                }
                if (bottom) {
                    edgesArrayList.add(n * (r + 1) + c);
                }
                if (left) {
                    edgesArrayList.add(n * r + (c - 1));
                }
                V[counter].edges = new Integer[edgesArrayList.size()];
                Collections.shuffle(edgesArrayList);
                V[counter].edges = edgesArrayList.toArray(V[counter].edges);

                counter++;
            }
        }
    }

    public Graph negateGraph() {

        int n = ( (Double) Math.sqrt(this.size) ).intValue();

        Graph result = new Graph(n); // At this point the result graph has all possible edges

        // Iterate over all vertices and negate the adjacency lists
        for (int i = 0; i < result.size; i++) {

            Integer[] oldEdges = this.V[i].edges;

            Integer[] newAdjacencyList = result.V[i].edges;

            ArrayList<Integer> edgesToRemove = new ArrayList<Integer>();
            // Iterate over the current vertex adjacency list
            for (int e = 0; e < newAdjacencyList.length; e++) {
                // If the original graph contains the current edge, delete it from the new graph
                if (Arrays.asList(oldEdges).contains(newAdjacencyList[e])) {
                    edgesToRemove.add(newAdjacencyList[e]);
                }
            }
            // Remove the edges to remove from the new adjacency list
            for (int newE = 0; newE < edgesToRemove.size(); newE++) {
                newAdjacencyList = MainActivity.removeValueFromArray(newAdjacencyList, edgesToRemove.get(newE));
            }

            result.V[i].edges = newAdjacencyList;
        }
        return result;
    }
}
