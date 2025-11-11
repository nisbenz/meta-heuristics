package org.example;

import java.util.ArrayList;

public class SimulatedAnnealingParkour extends MetaheuristicSearches{
    double Temp = 50000;
    void SimulatedAnnealingSolution() {
        Parkour best = new Parkour();
        best.setParkour(parkour.getParkour()); // Start with the current best route

        ArrayList<City> bestRoute = new ArrayList<>(best.getParkour());
        boolean improved = true;

        while (improved) {
            improved = false;

            for (int i = 1; i < bestRoute.size() - 2; i++) {
                for (int j = i + 1; j < bestRoute.size() - 1; j++) {

                    ArrayList<City> newRoute = new ArrayList<>(bestRoute);
                    reverseSegment(newRoute, i, j);

                    Parkour candidate = new Parkour();
                    candidate.setParkour(newRoute);

                    Parkour better = Evaluation.TheBestParkour(candidate, best);

                    if (better != best) {
                        best = better;
                        bestRoute = new ArrayList<>(best.getParkour());
                        improved = true;
                    }else{
                        double prob =Math.exp((-(Evaluation.TotalDistance(candidate)-Evaluation.TotalDistance(best))/Temp));
                        Temp *= 0.97;
                        if(prob>0.5){
                            best = candidate;
                            bestRoute = new ArrayList<>(best.getParkour());
                            improved = true;
                        }
                    }
                }
            }
        }

        parkour = best;
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
