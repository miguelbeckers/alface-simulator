package algorithms;

import models.Individual;
import models.Parameter;
import models.Tree;

import java.util.List;
import java.util.ArrayList;

public class BackTracking {
  private List<Parameter> parameters = new ArrayList<>();
  private Individual bestSolution;

  public BackTracking(List<Parameter> parameters) {
    this.parameters = parameters;
    Individual individual = new Individual(parameters);
    bestSolution = individual;
  }

  public Individual solve(double individualValue, double step) {
    bestSolution.fitness(individualValue);

    Tree tree = new Tree(parameters.size(), step);
    List<List<Double>> allCombos = tree.getAllCombos();
    
    for(int i = 0; i < allCombos.size(); i++){
      System.out.println(allCombos.get(i));
    }

    for (int i = 0; i < allCombos.size(); i++) {
      Individual individual = new Individual(parameters);
      List<Double> chromosome = new ArrayList<>(allCombos.get(i));

      individual.setChromosome(chromosome);
      individual.fitness(individualValue);
      individual.setGeneration(i);
      System.out.println(individual);

      if (bestSolution.compareTo(individual) > 0) {
        bestSolution = individual;
      }
    }

    return bestSolution;
  }
}
