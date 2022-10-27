package algorithms;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

import models.Antibody;

public class Immunological {
  private Antibody antigen;
  private List<Antibody> population = new ArrayList<>();

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
    
    System.out.println("ag -> " + antigen.getLithChain());

    for (Antibody antibody : population) {
      antibody.fitness(antigen.getLithChain());
    }
    
    List<Antibody> bestResults = getBestResults(3);

    System.out.println("Population: ");
    for (Antibody antibody : population) {
      System.out.println(antibody.getAfinity() + " -> " + antibody.getLithChain());
    }

    System.out.println("Best results: ");
    for (Antibody antibody : bestResults) {
      System.out.println(antibody.getAfinity() + " -> " + antibody.getLithChain());
    }

    List<Antibody> clones = getClones(17, bestResults);
    System.out.println("Clones: ");
    for (Antibody antibody : clones) {
      System.out.println(antibody.getAfinity() + " -> " + antibody.getLithChain());
    }
  }

  public List<Antibody> getBestResults(int quantity) {
    sortPopulation();
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

    System.out.println("soma: " + sum);

    List<Integer> duplications = new ArrayList<>();
    for(Antibody antibody: selected){
      double afinity = antibody.getAfinity();
      double result = (afinity/sum)*value;
      duplications.add((int)Math.round(result));
    }

    System.out.println("duplications: " + duplications);
    
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
        double randNumer = 0.5;
        if(randNumer <= mutationRate){
          // lightChain.get(i) = 
        }
      }
    }
  }
}
