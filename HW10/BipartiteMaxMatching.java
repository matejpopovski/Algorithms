import java.util.Scanner;
public class BipartiteMaxMatching {

    private int numNodesA;
    private int numNodesB;
    private int[][] adjMatrix;

    private BipartiteMaxMatching(int numNodesA, int numNodesB, int numEdges) {
        this.numNodesA = numNodesA;
        this.numNodesB = numNodesB;
        this.adjMatrix = new int[numNodesA][numNodesB];
    }

    private static BipartiteMaxMatching[] parse_input() {
        Scanner input = new Scanner(System.in);

        int numInstances = input.nextInt(); // Get number of instances
        BipartiteMaxMatching instances[] = new BipartiteMaxMatching[numInstances];

        for (int numInstance = 0; numInstance < numInstances; numInstance++) {
            int numNodesA = input.nextInt(); // Get number of nodes in set A
            int numNodesB = input.nextInt(); // Get number of nodes in set B
            int numEdges = input.nextInt();
            instances[numInstance] = new BipartiteMaxMatching(numNodesA, numNodesB, numEdges); // Initialize array of instances
            input.nextLine(); // Read the rest of the line to go to the next line

            // Add edges and connect from nodes in A to B
            while (numEdges > 0) {
                int nodeA = input.nextInt();
                int nodeB = input.nextInt();

                instances[numInstance].addEdge(nodeA, nodeB);
                numEdges--;
            }            
        }
        input.close();
        return instances;
    }

    private void addEdge(int nodeA, int nodeB) {
        this.adjMatrix[nodeA - 1][nodeB - 1] = 1;
    }

    private int maxMatching() {
        int matches[] = new int[this.numNodesB]; // keep track of matches

        // Recursively find matches for each node in A
        int count = 0;
        for (int i = 0; i < this.numNodesA; i++) {
            int visited[] = new int[this.numNodesB];

            if (isMatch(visited, matches, i)) {
                count++;
            }
        }

        return count;
    }

    private boolean isMatch(int visited[], int matches[], int i) {
        for (int j = 0; j < this.numNodesB; j++) {
            if (this.adjMatrix[i][j] == 1 && visited[j] == 0) {
                visited[j] = 1;

                if (matches[j] == 0 || isMatch(visited, matches, matches[j])) {
                    matches[j] = i;
                    return true;
                }
            }
        }

        return false;
    }

    private char isPerfectMatch() {
        for (int i = 0; i < this.numNodesA; i++) {
            for (int j = 0; j < this.numNodesB; j++) {
                if (this.adjMatrix[i][j] == 0) {
                    return 'N';
                }
            }
        }

        return 'Y';
    }

    public static void main(String[] args) {
        try {
            BipartiteMaxMatching[] instances = parse_input();
            for (BipartiteMaxMatching m : instances) {
                System.out.println(m.maxMatching() + " " + m.isPerfectMatch());
            }
        }
        catch(Exception e) {
            System.out.println(e);
        }
    }
}