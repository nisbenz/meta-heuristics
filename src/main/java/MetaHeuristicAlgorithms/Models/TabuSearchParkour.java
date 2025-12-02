package MetaHeuristicAlgorithms.Models;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

record Edge(int i, int j) {}

public class TabuSearchParkour extends MetaheuristicSearches {
    int MemorySize = 7;
    int MaxIterations = 100;
    Queue<Edge> tabuList = new LinkedList<>();

    public void TabuSearchSolution() {
        Parkour current = new Parkour();
        current.setParkour(parkour.getParkour());

        Parkour bestGlobal = new Parkour();
        bestGlobal.setParkour(parkour.getParkour());

        for (int iter = 0; iter < MaxIterations; iter++) {

            Parkour bestNeighbor = null;
            Edge bestMove = null;
            ArrayList<City> currentRoute = new ArrayList<>(current.getParkour());

            for (int i = 1; i < currentRoute.size() - 2; i++) {
                for (int j = i + 1; j < currentRoute.size() - 1; j++) {

                    Edge move = new Edge(i, j);

                    if (tabuList.contains(move)) {

                        continue;
                    }

                    ArrayList<City> neighborRoute = new ArrayList<>(currentRoute);
                    reverseSegment(neighborRoute, i, j);
                    Parkour neighbor = new Parkour();
                    neighbor.setParkour(neighborRoute);


                    if (bestNeighbor == null) {
                        bestNeighbor = neighbor;
                        bestMove = move;
                    } else {
                        Parkour better = Evaluation.TheBestParkour(neighbor, bestNeighbor);
                        if (better == neighbor) {
                            bestNeighbor = neighbor;
                            bestMove = move;
                        }
                    }
                }
            }

            if (bestNeighbor != null && bestMove != null) {
                current = bestNeighbor;

                tabuList.add(bestMove);

                if (tabuList.size() > MemorySize) {
                    tabuList.poll();
                }

                Parkour betterGlobal = Evaluation.TheBestParkour(current, bestGlobal);
                if (betterGlobal == current) {
                    bestGlobal.setParkour(current.getParkour());
                }
            } else {
                break;
            }
        }

        parkour = bestGlobal;
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
