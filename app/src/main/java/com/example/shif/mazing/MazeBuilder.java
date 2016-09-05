package com.example.shif.mazing;

/**
 * Helper for building a maze from a fully-connected graph representing a full grid.
 * Contains two methods:
 * carveWay changes the visual representation of the maze.
 * It breaks down vertical and horizontal walls in the mazeView,
 * using a depth-first walk in the graph.
 * removeEdges changes the graph representation of the maze.
 * It disconnects edges in the graph representation of the maze,
 * according to the state of the walls after carveWay was called.
 * An effort to combine the two functions resulted in a less readable code.
 */
public class MazeBuilder {

    public static void carveWay(Graph graph, Vertex v, MazeView mazeView) {
        int n = (int) Math.sqrt(graph.size);

        for (int i = 0; i < v.edges.length; i++) {

            // If the current neighbor was not visited yet,
            // break the wall between the current vertex and the neighbor
            // and then recursively carve way from the neighbor to its neighbors

            if (v.edges[i] != null && !graph.V[v.edges[i]].visited) {
                // Find which is the larger-key vertex
                Vertex biggerVertex;
                Vertex smallerVertex;
                if (v.key >= graph.V[v.edges[i]].key) {
                    biggerVertex = v;
                    smallerVertex = graph.V[v.edges[i]];
                } else {
                    biggerVertex = graph.V[v.edges[i]];
                    smallerVertex = v;
                }

                int row = biggerVertex.key / n;
                int column = biggerVertex.key % n;

                // Check if wall is vertical or horizontal
                // and turn matching wall to false
                if (biggerVertex.key / n == smallerVertex.key / n) {
                    // vertical wall
                    mazeView.verticalLines[row][column] = false;
                } else {
                    // Horizontal wall
                    mazeView.horizontalLines[row][column] = false;
                }

                // Change visited to true
                graph.V[v.edges[i]].setVisitedToTrue();

                // Move on recursively
                carveWay(graph, graph.V[v.edges[i]], mazeView);
            }
        }
        // All neighbors were visited, move back in recursion
    }

    public static void removeEdges(MazeView mazeView) {
        // Iterate over the verticalLines
        int counter = 0;

        for (int linesRow = 0; linesRow < mazeView.verticalLines.length; linesRow++) {

            // Don't iterate over the first and last vertical lines as they are the maze's borders
            for (int linesColumn = 1; linesColumn < mazeView.verticalLines[0].length - 1; linesColumn++) {
                // If there is no line, remove the edge
                if (mazeView.verticalLines[linesRow][linesColumn] == false) {

                    Integer[] currentEdges = mazeView.graph.V[counter].edges;
                    Integer[] currentEdgesNextVertex = mazeView.graph.V[counter + 1].edges;

                    // Remove the relevant edge from current edges arrays
                    Integer currentVertexKey = (linesRow * mazeView.mazeSize) + (linesColumn - 1);

                    Integer neighborVertexKey = currentVertexKey + 1;

                    Integer[] newEdgesV = MainActivity.removeValueFromArray(currentEdges, neighborVertexKey);
                    Integer[] newEdgesNeighbor = MainActivity.removeValueFromArray(currentEdgesNextVertex, currentVertexKey);

                    mazeView.graph.V[counter].edges = newEdgesV;
                    mazeView.graph.V[counter + 1].edges = newEdgesNeighbor;
                }
                counter++;
            }
            counter++;
        }

        // Restart counter to zero
        // Iterate over the horizontalLines
        counter = 0;

        // Don't iterate over the first and last horizontal lines as they are the maze's borders
        for (int linesRow = 1; linesRow < mazeView.horizontalLines.length - 1; linesRow++) {

            for (int linesColumn = 0; linesColumn < mazeView.horizontalLines[0].length; linesColumn++) {

                // If there is not line, remove the edge
                if (mazeView.horizontalLines[linesRow][linesColumn] == false) {

                    Integer[] currentEdges = mazeView.graph.V[counter].edges;
                    Integer[] currentEdgesNextVertex = mazeView.graph.V[counter + mazeView.mazeSize].edges;

                    // Remove the relevant edge from current edges arrays
                    Integer currentVertexKey = ((linesRow - 1) * mazeView.mazeSize) + (linesColumn);

                    Integer neighborVertexKey = currentVertexKey + mazeView.mazeSize;

                    Integer[] newEdgesV = MainActivity.removeValueFromArray(currentEdges, neighborVertexKey);

                    Integer[] newEdgesNeighbor = MainActivity.removeValueFromArray(currentEdgesNextVertex, currentVertexKey);

                    mazeView.graph.V[counter].edges = newEdgesV;
                    mazeView.graph.V[counter + mazeView.mazeSize].edges = newEdgesNeighbor;
                }
                counter++;
            }
        }
    }
}
