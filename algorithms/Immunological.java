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
    antigen.fitness(lightChain);

    for (int i = 0; i < populationSize; i++) {
      population.add(new Antibody(arrayWidth));
    }

    solve(1);
  }

  public void solve(int generations){
    int arrayWidth = 6;
    // antigen.print(arrayWidth);
    
    for (Antibody antibody : population) {
      antibody.fitness(antigen.getLithChain());
    }

    sortPopulation();

    for(int i = 0; i < generations; i++){
      // population.get(0).print(arrayWidth); 

      List<Antibody> bestResults = getBestResults(3);
      List<Antibody> clones = getClones(17, bestResults);

      hypermutation(clones, 0.1, arrayWidth);

      List<Antibody> newPopulation = clones;
      int numberOfNewAntibodies = population.size() - clones.size();

      for(int j = 0; j < numberOfNewAntibodies; j++){
        newPopulation.add(new Antibody(arrayWidth));
      }

      for (Antibody antibody : newPopulation) {
        antibody.fitness(antigen.getLithChain());
      }

      population = newPopulation;

      sortPopulation();

      bestSolution = population.get(0);
      // System.out.println(i + " -> " + bestSolution.getAfinity() + "/" + (arrayWidth*arrayWidth));
    }
  }

  public void printChains(List<Antibody> antibodies){
    for(Antibody antibody : antibodies){
      // System.out.println(antibody.getAfinity() + " -> " + antibody.getLithChain());
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
    
    for(Antibody clone : clones){
      System.out.println(clone.getAfinity() + " -> " + clone.getLithChain());
    }

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
      clone.fitness(antigen.getLithChain());
      System.out.println(clone.getAfinity() + " -> " + clone.getLithChain() + " -> " + mutationRate);
      hipermutateds.add(clone);
    }
  }

  public Antibody getBestSolution(){
    return bestSolution;
  }
}
