package MetaHeuristicAlgorithms.Models;

import java.util.ArrayList;

public class SimulatedAnnealingParkour extends MetaheuristicSearches {
    double Temp = 50000;

    public void SimulatedAnnealingSolution() {
        Parkour current = new Parkour();
        current.setParkour(parkour.getParkour());

        Parkour globalBest = new Parkour();
        globalBest.setParkour(parkour.getParkour());

        for (int k = 0; k < 1000; k++) {
            ArrayList<City> currentRoute = new ArrayList<>(current.getParkour());

            for (int i = 1; i < currentRoute.size() - 2; i++) {
                for (int j = i + 1; j < currentRoute.size() - 1; j++) {

                    ArrayList<City> newRoute = new ArrayList<>(currentRoute);
                    reverseSegment(newRoute, i, j);

                    Parkour candidate = new Parkour();
                    candidate.setParkour(newRoute);

                    Parkour better = Evaluation.TheBestParkour(candidate, current);

                    if (better != current) {
                        current = candidate;
                        currentRoute = new ArrayList<>(current.getParkour());
                    } else {
                        double prob = Math.exp((-(Evaluation.TotalDistance(candidate) - Evaluation.TotalDistance(current)) / Temp));
                        if (Math.random() < prob) {
                            current = candidate;
                            currentRoute = new ArrayList<>(current.getParkour());
                        }
                    }
                }
            }

            globalBest = Evaluation.TheBestParkour(current, globalBest);

            Temp *= 0.97;
        }

        parkour = globalBest; // Return the GLOBAL BEST
    }

    private void reverseSegment(ArrayList<City> route, int i, int j) {
        while (i < j) {
            City tmp = route.get(i);
            route.set(i, route.get(j));
            route.set(j, tmp);
            i++;
            j--;
        }
    }
}
