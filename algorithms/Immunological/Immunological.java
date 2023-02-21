package algorithms.Immunological;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

public class Immunological {
    private final Antibody antigen;
    private List<Antibody> population = new ArrayList<>();
    private Antibody bestSolution;

    public Immunological() {
        int arrayWidth = 6;
        int populationSize = 20;

        List<Integer> lightChain = Arrays.asList(
                0, 0, 0, 0, 0, 0,
                0, 0, 1, 1, 1, 0,
                0, 1, 0, 0, 0, 1,
                0, 0, 1, 1, 1, 0,
                0, 1, 0, 0, 0, 1,
                0, 0, 1, 1, 1, 0);
        antigen = new Antibody(arrayWidth);
        antigen.setLightChain(lightChain);
        antigen.fitness(lightChain);

        for (int i = 0; i < populationSize; i++) {
            population.add(new Antibody(arrayWidth));
        }

        solve(100);
    }

    public void solve(int generations) {
        int arrayWidth = 6;

        for (Antibody antibody : population) {
            antibody.fitness(antigen.getLightChain());
        }

        sortPopulation();

        for (int i = 0; i < generations; i++) {
            List<Antibody> bestResults = getBestResults(3);

            List<Antibody> clones = getClones(17, bestResults);

            hypermutation(clones, 0.1, arrayWidth);

            int numberOfNewAntibodies = population.size() - clones.size();

            for (int j = 0; j < numberOfNewAntibodies; j++) {
                clones.add(new Antibody(arrayWidth));
            }

            for (Antibody antibody : clones) {
                antibody.fitness(antigen.getLightChain());
            }

            population = clones;

            sortPopulation();

            bestSolution = population.get(0);
            bestSolution.print(arrayWidth);
            System.out.println(i + " -> " + bestSolution.getAffinity()
                    + "/" + (arrayWidth * arrayWidth) + "\n");
        }
    }

    public List<Antibody> getBestResults(int quantity) {
        List<Antibody> bestResults = new ArrayList<>();
        for (int i = 0; i < quantity; i++) {
            bestResults.add(population.get(i));
        }
        return bestResults;
    }

    public void sortPopulation() {
        Collections.sort(population);
    }

    public List<Antibody> getClones(int value, List<Antibody> selected) {
        int sum = 0;

        for (Antibody antibody : selected) {
            sum += antibody.getAffinity();
        }

        List<Integer> duplications = new ArrayList<>();

        for (Antibody antibody : selected) {
            double affinity = antibody.getAffinity();
            double result = (affinity / sum) * value;
            duplications.add((int) Math.round(result));
        }

        List<Antibody> clones = new ArrayList<>();

        for (int i = 0; i < selected.size(); i++) {
            for (int j = 0; j < duplications.get(i); j++) {
                clones.add(selected.get(i).clone());
            }
        }

        return clones;
    }

    public void hypermutation(List<Antibody> clones, double factor, int arrayWidth) {
        arrayWidth *= arrayWidth;

        for (Antibody clone : clones) {
            double affinity = clone.getAffinity();
            double mutationRate = (1 - (affinity / arrayWidth)) * factor;

            List<Integer> lightChain = new ArrayList<>();

            for (int i = 0; i < clone.getLightChain().size(); i++) {
                double randomValue = Math.random();

                if (randomValue <= mutationRate) {
                    if (clone.getLightChain().get(i) == 0) {
                        lightChain.add(1);
                    } else {
                        lightChain.add(0);
                    }
                } else {
                    lightChain.add(clone.getLightChain().get(i));
                }
            }

            clone.setLightChain(lightChain);
            clone.fitness(antigen.getLightChain());
        }
    }

    public Antibody getBestSolution() {
        return bestSolution;
    }
}
