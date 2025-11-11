package MetaHeuristicAlgorithms.Models;

import java.util.ArrayList;

public class HillClimbParkour extends MetaheuristicSearches {
    public void HillClimbSolution() {
        Parkour pk = new Parkour();
        ArrayList<City> bestRoute = new ArrayList<>(parkour.getParkour());

        boolean improved = true;
        while (improved) {
            improved = false;

            for (int i = 1; i < bestRoute.size() - 1; i++) {
                for (int j = 1; j < bestRoute.size()-1; j++) {
                    if (i == j) continue;

                    ArrayList<City> candidateRoute = new ArrayList<>(bestRoute);
                    City moved = candidateRoute.remove(i);
                    candidateRoute.add(j, moved);

                    Parkour candidate = new Parkour();
                    candidate.setParkour(candidateRoute);

                    if (Evaluation.TheBestParkour(candidate, parkour) != parkour) {
                        parkour = candidate;
                        bestRoute = new ArrayList<>(candidateRoute);
                        improved = true;
                    }
                }
            }
        }
    }
}

