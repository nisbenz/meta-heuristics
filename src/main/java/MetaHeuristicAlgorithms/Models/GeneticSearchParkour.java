package MetaHeuristicAlgorithms.Models;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class GeneticSearchParkour extends MetaheuristicSearches {

    int Gen = 100;
    double crossoverRate = 0.8;
    double mutationRate = 0.05;
    List<MetaheuristicSearches> chromosomes = new ArrayList<>();

    public void GeneticSearchSolution() {
        init();

        for (int j = 0; j < Gen; j++) {
            List<Double> pb = CalculateSelectionProbability(chromosomes);
            ArrayList<Integer> selectedIndices = new ArrayList<>();
            Random rand = new Random();

            for (int k = 0; k < chromosomes.size(); k++) {
                double r = rand.nextDouble();
                double cumulative = 0.0;
                int selected = chromosomes.size() - 1;

                for (int i = 0; i < pb.size(); i++) {
                    cumulative += pb.get(i);
                    if (r <= cumulative) {
                        selected = i;
                        break;
                    }
                }
                selectedIndices.add(selected);
            }

            List<MetaheuristicSearches> nextGeneration = Crossover(chromosomes, selectedIndices);
            Mutation(nextGeneration);
            chromosomes = nextGeneration;
            parkour = getBestScore(chromosomes);
        }
    }

    void init() {
        chromosomes.clear();
        for (int i = 0; i < 100; i++) {
            RandomParkour g = new RandomParkour();
            Random k = new Random();
            g.RandomSolution(k.nextInt(10000));
            chromosomes.add(g);
        }
    }

    List<Double> CalculateSelectionProbability(List<MetaheuristicSearches> chromosomes) {
        List<Double> probability = new ArrayList<>();
        double sum = 0;

        for (MetaheuristicSearches chromosome : chromosomes) {
            double dist = Evaluation.TotalDistance(chromosome.parkour);
            double fitness = 1.0 / (1.0 + dist);
            sum += fitness;
            probability.add(fitness);
        }

        for (int i = 0; i < probability.size(); i++) {
            probability.set(i, probability.get(i) / sum);
        }

        return probability;
    }

    List<MetaheuristicSearches> Crossover(List<MetaheuristicSearches> oldPopulation, ArrayList<Integer> selectedIndices) {
        List<MetaheuristicSearches> nextGen = new ArrayList<>();
        Random g = new Random();

        for (int i = 0; i < selectedIndices.size(); i += 2) {
            if (i + 1 >= selectedIndices.size()) {
                nextGen.add(copySolution(oldPopulation.get(selectedIndices.get(i))));
                break;
            }

            MetaheuristicSearches parent1 = oldPopulation.get(selectedIndices.get(i));
            MetaheuristicSearches parent2 = oldPopulation.get(selectedIndices.get(i + 1));

            if (g.nextDouble() < crossoverRate) {
                ArrayList<City> p1 = parent1.parkour.getParkour();
                ArrayList<City> p2 = parent2.parkour.getParkour();
                int size = p1.size();

                // Removed: if (size < 4) check - not needed with 20 cities

                int breakPoint = g.nextInt(size - 3) + 2;
                City depot = p1.getFirst();

                // === Création Enfant 1 ===
                MetaheuristicSearches child1 = new RandomParkour();
                child1.parkour = new Parkour();
                ArrayList<City> route1 = child1.parkour.getParkour();

                route1.add(depot);

                for (int k = 1; k < breakPoint; k++) {
                    route1.add(p1.get(k));
                }

                for (int k = 1; k < size - 1; k++) {
                    City city = p2.get(k);
                    boolean exists = false;
                    for (City c : route1) {
                        if (c.name().equals(city.name())) {
                            exists = true;
                            break;
                        }
                    }
                    if (!exists) {
                        route1.add(city);
                    }
                }

                route1.add(depot);
                nextGen.add(child1);

                // === Création Enfant 2 ===
                MetaheuristicSearches child2 = new RandomParkour();
                child2.parkour = new Parkour();
                ArrayList<City> route2 = child2.parkour.getParkour();

                route2.add(depot);

                for (int k = 1; k < breakPoint; k++) {
                    route2.add(p2.get(k));
                }

                for (int k = 1; k < size - 1; k++) {
                    City city = p1.get(k);
                    boolean exists = false;
                    for (City c : route2) {
                        if (c.name().equals(city.name())) {
                            exists = true;
                            break;
                        }
                    }
                    if (!exists) {
                        route2.add(city);
                    }
                }

                route2.add(depot);
                nextGen.add(child2);

            } else {
                nextGen.add(copySolution(parent1));
                nextGen.add(copySolution(parent2));
            }
        }
        return nextGen;
    }



    void Mutation(List<MetaheuristicSearches> population) {
        Random rand = new Random();

        for (MetaheuristicSearches ind : population) {
            if (rand.nextDouble() < mutationRate) {
                ArrayList<City> route = ind.parkour.getParkour();
                int size = route.size();

                if (size > 3) {
                    int idx1 = rand.nextInt(size - 2) + 1;
                    int idx2 = rand.nextInt(size - 2) + 1;

                    City temp = route.get(idx1);
                    route.set(idx1, route.get(idx2));
                    route.set(idx2, temp);
                }
            }
        }
    }

    MetaheuristicSearches copySolution(MetaheuristicSearches original) {
        MetaheuristicSearches copy = new RandomParkour();
        copy.parkour = new Parkour();
        copy.parkour.getParkour().addAll(original.parkour.getParkour());
        return copy;
    }

    Parkour getBestScore(List<MetaheuristicSearches> pop) {
        double min = Double.MAX_VALUE;
        Parkour bestParkour = pop.get(0).parkour;

        for (MetaheuristicSearches s : pop) {
            double d = Evaluation.TotalDistance(s.parkour);
            if (d < min) {
                min = d;
                bestParkour = s.parkour;
            }
        }
        return bestParkour;
    }
}
