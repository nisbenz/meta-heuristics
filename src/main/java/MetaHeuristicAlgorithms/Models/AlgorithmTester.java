package MetaHeuristicAlgorithms.Models;

import java.util.Scanner;

public class AlgorithmTester {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            displayMenu();
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            System.out.println();

            switch (choice) {
                case 1:
                    System.out.println("Running Random Search...");
                    testRandomSearch();
                    break;
                case 2:
                    System.out.println("Running Hill Climb...");
                    testHillClimb();
                    break;
                case 3:
                    System.out.println("Running Local Search...");
                    testLocalSearch();
                    break;
                case 4:
                    System.out.println("Running Simulated Annealing...");
                    testSimulatedAnnealing();
                    break;
                case 5:
                    System.out.println("Running Tabu Search...");
                    testTabuSearch();
                    break;
                case 6:
                    System.out.println("Running Genetic Algorithm...");
                    testGeneticSearch();
                    break;
                case 7:
                    System.out.println("Running ALL algorithms...");
                    testAllAlgorithms();
                    break;
                case 0:
                    System.out.println("Exiting...");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }

            if (running) {
                System.out.println("\n" + "=".repeat(50) + "\n");
            }
        }

        scanner.close();
    }

    private static void displayMenu() {
        System.out.println("=== Metaheuristic Algorithms Tester ===");
        System.out.println("1. Random Search");
        System.out.println("2. Hill Climb");
        System.out.println("3. Local Search");
        System.out.println("4. Simulated Annealing");
        System.out.println("5. Tabu Search");
        System.out.println("6. Genetic Algorithm");
        System.out.println("7. Run ALL Algorithms");
        System.out.println("0. Exit");
        System.out.println("=======================================");
    }

    private static void testAllAlgorithms() {
        System.out.println("\n1. Random Search:");
        testRandomSearch();

        System.out.println("\n2. Hill Climb:");
        testHillClimb();

        System.out.println("\n3. Local Search:");
        testLocalSearch();

        System.out.println("\n4. Simulated Annealing:");
        testSimulatedAnnealing();

        System.out.println("\n5. Tabu Search:");
        testTabuSearch();

        System.out.println("\n6. Genetic Algorithm:");
        testGeneticSearch();

        System.out.println("\n=== All tests completed ===");
    }

    private static void testRandomSearch() {
        long startTime = System.currentTimeMillis();
        RandomParkour random = new RandomParkour();
        random.RandomSolution(5000);
        long endTime = System.currentTimeMillis();

        printResults("Random Search", random, endTime - startTime);
    }

    private static void testHillClimb() {
        long startTime = System.currentTimeMillis();
        HillClimbParkour hillClimb = new HillClimbParkour();
        hillClimb.HillClimbSolution();
        long endTime = System.currentTimeMillis();

        printResults("Hill Climb", hillClimb, endTime - startTime);
    }

    private static void testLocalSearch() {
        long startTime = System.currentTimeMillis();
        LocalParkour local = new LocalParkour();
        local.localSolution();
        long endTime = System.currentTimeMillis();

        printResults("Local Search", local, endTime - startTime);
    }

    private static void testSimulatedAnnealing() {
        long startTime = System.currentTimeMillis();
        SimulatedAnnealingParkour sa = new SimulatedAnnealingParkour();
        sa.SimulatedAnnealingSolution();
        long endTime = System.currentTimeMillis();

        printResults("Simulated Annealing", sa, endTime - startTime);
    }

    private static void testTabuSearch() {
        long startTime = System.currentTimeMillis();
        TabuSearchParkour tabu = new TabuSearchParkour();
        tabu.TabuSearchSolution();
        long endTime = System.currentTimeMillis();

        printResults("Tabu Search", tabu, endTime - startTime);
    }

    private static void testGeneticSearch() {
        long startTime = System.currentTimeMillis();
        GeneticSearchParkour genetic = new GeneticSearchParkour();
        genetic.GeneticSearchSolution();
        long endTime = System.currentTimeMillis();

        printResults("Genetic Algorithm", genetic, endTime - startTime);
    }

    private static void printResults(String algorithmName, MetaheuristicSearches algorithm, long executionTime) {
        Parkour bestParkour = algorithm.parkour;
        double distance = Evaluation.TotalDistance(bestParkour);

        System.out.println("   Algorithm: " + algorithmName);
        System.out.println("   Best Distance: " + String.format("%.2f", distance) + " km");
        System.out.println("   Execution Time: " + executionTime + " ms");
        System.out.println("   Route: " + formatRoute(bestParkour));
    }

    private static String formatRoute(Parkour parkour) {
        StringBuilder route = new StringBuilder();
        var cities = parkour.getParkour();

        for (int i = 0; i < cities.size(); i++) {
            route.append(cities.get(i).name());
            if (i < cities.size() - 1) {
                route.append(" -> ");
            }
        }

        return route.toString();
    }
}
