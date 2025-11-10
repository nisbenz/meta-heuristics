package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        // Initialize positions (list of cities)
        Positions positions = new Positions();

        // Create and test RandomParkour
        RandomParkour randomParkour = new RandomParkour();

        // Run the random generation method
        for (int i = 0; i < 10000; i++) {
            randomParkour.RandomSolution();

            // Print the resulting parkour route
            System.out.println("=== Best Parkour Found ===");
            for (City c : randomParkour.parkour.getParkour()) {
                System.out.println(c.name() + " (" + c.lat() + ", " + c.lng() + ")");
            }

            // Print the total distance of the route
            double totalDistance = Evaluation.TotalDistance(randomParkour.parkour);
            System.out.println("\nTotal Distance: " + totalDistance + " km");
        }
    }
        }

