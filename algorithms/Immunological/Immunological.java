package algorithms.Immunological;

import model.Parameter;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

public class Immunological {
    private List<Antibody> population = new ArrayList<>();
    private Antibody bestSolution;

    public Immunological(int populationSize, List<Parameter> parameters) {
        for (int i = 0; i < populationSize; i++) {
            List<Double> lightChain = getRandomLightChain(parameters);
            population.add(new Antibody(parameters, lightChain));
        }
        bestSolution = population.get(0);
    }

    private List<Double> getRandomLightChain(List<Parameter> parameters) {
        List<Double> lightChain = new ArrayList<>();
        for (int i = 0; i < parameters.size(); i++) {
            lightChain.add((double) Math.round(Math.random() * 100) / 100);
        }
        return lightChain;
    }

    private void sortPopulation() {
        Collections.sort(population);
    }

    public Antibody solve(List<Parameter> parameters, double value, int generations, int quantity, double factor, double objective) {
        for (Antibody antibody : population) {
            antibody.fitness(value);
        }

        sortPopulation();

        for (int i = 0; i < generations; i++) {
            List<Antibody> bestResults = getBestResults(quantity);
            List<Antibody> clones = getClones(population.size() - quantity, bestResults);

            for (Antibody clone : clones) {
                clone.fitness(value);
            }

            for (Antibody clone : clones) {
                clone.hypermutation(value, factor, objective);
            }

            int numberOfNewAntibodies = population.size() - clones.size();

            for (int j = 0; j < numberOfNewAntibodies; j++) {
                List<Double> lightChain = getRandomLightChain(parameters);
                clones.add(new Antibody(parameters, lightChain));
            }

            for (Antibody antibody : clones) {
                antibody.fitness(value);
            }

            for (Antibody antibody : clones) {
                antibody.setId((long) i);
            }

            population = clones;
            sortPopulation();

            System.out.println(population.get(0).toString());

            if (bestSolution.compareTo(population.get(0)) > 0) {
                bestSolution = population.get(0);
            }
        }

        return bestSolution;
    }

    public List<Antibody> getBestResults(int quantity) {
        List<Antibody> bestResults = new ArrayList<>();
        for (int i = 0; i < quantity; i++) {
            bestResults.add(population.get(i));
        }
        return bestResults;
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
                clones.add((Antibody) selected.get(i).clone());
            }
        }

        return clones;
    }
}
