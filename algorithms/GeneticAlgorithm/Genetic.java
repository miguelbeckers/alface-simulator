package algorithms.GeneticAlgorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import general.Parameter;
import general.Config;
import java.lang.System;

public class Genetic {
    private final List<Config> configs;
    private List<Individual> population = new ArrayList<>();
    private Individual bestSolution;

    public Genetic(List<Config> configs, List<Parameter> parameters, int populationSize) {
        this.configs = configs;

        for (int i = 0; i < populationSize; i++) {
            List<Double> chromosome = getRandomChromosome(parameters);
            population.add(new Individual(configs, parameters, chromosome));
        }
        bestSolution = population.get(0);
    }

    private List<Double> getRandomChromosome(List<Parameter> parameters) {
        List<Double> chromosome = new ArrayList<>();
        for (int i = 0; i < parameters.size(); i++) {
            chromosome.add((double) Math.round(Math.random() * 100) / 100);
        }
        return chromosome;
    }

    private void sortPopulation() {
        Collections.sort(population);
    }

    private int getParentIndex(double sumOfProfits) {
        int index = -1;
        double sum = 0.0;
        double randomValue = Math.random() * sumOfProfits;

        while (index < population.size() && sum < randomValue) {
            index++;
            sum += population.get(index).getProfit();
        }
        return index;
    }

    public Individual solve(double value, double mutationRate, int generations) {
        for (Individual individual : population) {
            individual.fitness(value);
        }

        sortPopulation();
        bestSolution = population.get(0);

        if (configs.get(8).getValue()) {
            for (Individual individual : population) {
                System.out.println(individual);
            }
        }

        for (int i = 0; i < generations; i++) {
            double sumOfProfits = 0.0;

            for (Individual individual : population) {
                sumOfProfits += individual.getProfit();
            }

            List<Individual> newPopulation = new ArrayList<>();

            for (int j = 0; j < population.size() / 2; j++) {
                int parentIndex1 = getParentIndex(sumOfProfits);
                int parentIndex2 = getParentIndex(sumOfProfits);

                Individual parent1 = population.get(parentIndex1);
                Individual parent2 = population.get(parentIndex2);

                List<Individual> children = parent1.crossover(parent2);

                children.get(0).mutation(mutationRate);
                children.get(1).mutation(mutationRate);

                newPopulation.add(children.get(0));
                newPopulation.add(children.get(1));
            }

            setPopulation(newPopulation);

            for (Individual individual : population) {
                individual.fitness(value);
            }

            sortPopulation();

            if (configs.get(8).getValue()) {
                for (Individual individual : population) {
                    System.out.println(individual);
                }
            }

            if (configs.get(9).getValue()) {
                System.out.println(population.get(0));
            }

            if (bestSolution.compareTo(population.get(0)) > 0) {
                bestSolution = population.get(0);
            }
        }

        return bestSolution;
    }

    private void setPopulation(List<Individual> population) {
        this.population = population;
    }
}
