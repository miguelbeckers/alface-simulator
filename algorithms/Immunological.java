package algorithms;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

import models.Antibody;

public class Immunological {
  private Antibody antigen;
  private List<Antibody> population = new ArrayList<>();
  private Antibody bestSolution;

  public Immunological() {
    int arrayWidth = 6;
    int populationSize = 20;

    List<Integer> lightChain = Arrays.asList(0, 0, 0, 0, 0, 0,
        0, 0, 1, 1, 1, 0,
        0, 1, 0, 0, 0, 1,
        0, 0, 1, 1, 1, 0,
        0, 1, 0, 0, 0, 1,
        0, 0, 1, 1, 1, 0);

    antigen = new Antibody(arrayWidth);
    antigen.setLightChain(lightChain);

    for (int i = 0; i < populationSize; i++) {
      population.add(new Antibody(arrayWidth));
    }

    solve(1000);
  }

  public void solve(int generations){
    int arrayWidth = 6;
    
    for (Antibody antibody : population) {
      antibody.fitness(antigen.getLithChain());
    }

    sortPopulation();

    for(int i = 0; i < generations; i++){
      // System.out.println("########## population: " + i + " size: " + population.size());
      // printChains(population);
      
      List<Antibody> bestResults = getBestResults(3);
      // System.out.println("bestResults: ");
      // printChains(bestResults);

      List<Antibody> clones = getClones(17, bestResults);
      // System.out.println("clones: ");
      // printChains(clones);

      hypermutation(clones, 0.01, arrayWidth);
      // System.out.println("hypermuted clones: ");
      // printChains(clones);

      List<Antibody> newPopulation = clones;
      // System.out.println("new population with clones: ");
      // printChains(newPopulation);

      int numberOfNewAntibodies = population.size() - clones.size();

      for(int j = 0; j < numberOfNewAntibodies; j++){
        newPopulation.add(new Antibody(arrayWidth));
      }
      // System.out.println("new population with new antibodies: ");
      // printChains(newPopulation);

      for (Antibody antibody : newPopulation) {
        antibody.fitness(antigen.getLithChain());
      }
      // System.out.println("new population after fitness: ");
      // printChains(newPopulation);

      population = newPopulation;
      // System.out.println("updated population: ");
      // printChains(newPopulation);

      sortPopulation();
      // System.out.println("sorted population: ");
      // printChains(newPopulation);

      bestSolution = population.get(0);
      // System.out.println("best result: ");
      System.out.println(i + " -> " + bestSolution.getAfinity() + "/" + (arrayWidth*arrayWidth));

      // bestSolution.print(arrayWidth);
    }
  }

  public void printChains(List<Antibody> antibodies){
    for(Antibody antibody : antibodies){
      System.out.println(antibody.getAfinity() + " -> " + antibody.getLithChain());
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
    
    for(Antibody antibody : selected){
      sum += antibody.getAfinity();
    }

    List<Integer> duplications = new ArrayList<>();
    for(Antibody antibody: selected){
      double afinity = antibody.getAfinity();
      double result = (afinity/sum)*value;
      duplications.add((int)Math.round(result));
    }
    
    List<Antibody> clones = new ArrayList<>();
    for(int i = 0; i < selected.size(); i++){
      for(int j = 0; j < duplications.get(i); j++){
        clones.add(selected.get(i));
      }
    }
    return clones;
  }

  public void hypermutation(List<Antibody> clones, double factor, int arrayWidth) {
    arrayWidth *= arrayWidth;
    
    List<Antibody> hipermutateds = new ArrayList<>();
    for(Antibody clone : clones){
      double afinity = clone.getAfinity();
      double mutationRate = (1 - (afinity/arrayWidth)) * factor;

      List<Integer> lightChain = new ArrayList<>();
      for(int i = 0; i < clone.getLithChain().size(); i++){
        double randomValue = Math.random();

        if(randomValue <= mutationRate){
          if(clone.getLithChain().get(i) == 0){
            lightChain.add(1);
          } else {
            lightChain.add(0);
          }
        } else {
          lightChain.add(clone.getLithChain().get(i));
        }
      }
      clone.setLightChain(lightChain);
      hipermutateds.add(clone);
    }
  }

  public Antibody getBestSolution(){
    return bestSolution;
  }
}
