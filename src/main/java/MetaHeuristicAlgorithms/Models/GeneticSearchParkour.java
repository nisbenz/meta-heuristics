package MetaHeuristicAlgorithms.Models;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class GeneticSearchParkour extends MetaheuristicSearches {

    int Gen = 100;
    double crossoverRate = 0.25;
    double mutationRate = 0.1;
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
            double fitness = 1/ (1 + dist);
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

                if (size <= 2) {
                    nextGen.add(copySolution(parent1));
                    nextGen.add(copySolution(parent2));
                    continue;
                }

                int breakPoint = g.nextInt(size - 2) + 1;

                City depot = p1.getFirst();
                CreateChild(nextGen, p1, p2, size, breakPoint,depot);
                CreateChild(nextGen, p2, p1, size, breakPoint,depot);

            } else {
                nextGen.add(copySolution(parent1));
                nextGen.add(copySolution(parent2));
            }
        }
        return nextGen;
    }

    private void CreateChild(List<MetaheuristicSearches> nextGen, ArrayList<City> p1, ArrayList<City> p2, int size, int breakPoint, City depot) {
        MetaheuristicSearches child = new RandomParkour();
        child.parkour = new Parkour();
        ArrayList<City> childRoute = child.parkour.getParkour();

        HashSet<City> visitedCities = new HashSet<>();

        for (int k = 0; k <= breakPoint; k++) {
            City c = p1.get(k);
            childRoute.add(c);
            visitedCities.add(c);
        }


        for (int k = 1; k < p2.size()-1; k++) {
            City c = p2.get(k);

            if (c.equals(depot)) continue;

            if (!visitedCities.contains(c)) {
                childRoute.add(c);
                visitedCities.add(c);
            }
        }


        if (childRoute.size() < p1.size()) {
            childRoute.add(depot);
        }

        nextGen.add(child);
    }
    void Mutation(List<MetaheuristicSearches> population) {
        Random rand = new Random();

        for (MetaheuristicSearches ind : population) {
            if (rand.nextDouble() < mutationRate) {
                ArrayList<City> route = ind.parkour.getParkour();
                int size = route.size();

                if (size > 2) {
                    int idx1 = rand.nextInt(size - 2) + 1;
                    int idx2 = rand.nextInt(size - 2) + 1;
                    City temp = route.get(idx1);
                    route.set(idx1, route.get(idx2));
                    route.set(idx2, temp);
                } else {
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
        Parkour bestParkour = pop.getFirst().parkour;

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
