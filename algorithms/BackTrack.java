package algorithms;

import models.Individual;
import models.Parameter;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class BackTrack {
  List<Parameter> parameters = new ArrayList<>();
  Individual bestSolution;

  class Node {
    int value;
    List<Node> childs = new ArrayList<>();
  }

  public BackTrack(List<Parameter> parameters) {
    this.parameters = parameters;
    Individual individual = new Individual(parameters);
    bestSolution = individual;
  }

  public Individual solve(double individualValue, double step) {
    bestSolution.fitness(individualValue);

    for (double i = 0; i <= 1; i += step) {
      for (double j = 0; j <= 1; j += step) {
        for (double k = 0; k <= 1; k += step) {
          Individual individual = new Individual(parameters);
          List<Double> chromosome = Arrays.asList(i, j, k);

          individual.setChromosome(chromosome);
          individual.fitness(individualValue);

          System.out.println(individual);

          if (bestSolution.compareTo(individual) > 0) {
            bestSolution = individual;
          }
        }
      }
    }

    return bestSolution;
  }
}
