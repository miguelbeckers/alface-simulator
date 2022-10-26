package algorithms;

import models.Individual;
import models.Parameter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Genetic {
  private List<Individual> population = new ArrayList<>();
  private Individual bestSolution;

  public Genetic(int populationSize, List<Parameter> parameters) {
    for (int i = 0; i < populationSize; i++) {
      population.add(new Individual(parameters));
    }
    bestSolution = population.get(0);
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
    for (Individual individual : population)
      individual.fitness(value);

    sortPopulation();
    bestSolution = population.get(0);
    bestSolution.toString();

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
      System.out.println(population.get(0).toString());

      if (bestSolution.compareTo(population.get(0)) > 0) {
        bestSolution = population.get(0);
      }
    }

    return bestSolution;
  }

  private void setPopulation(List<Individual> population) {
    this.population = population;
  }

  public List<Individual> getPopulation() {
    return population;
  }

  public Individual getBestSolution() {
    return bestSolution;
  }
}
