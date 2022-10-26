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
    int arrayWidth = 7;
    int populationSize = 50;
    List<Integer> lightChain = Arrays.asList(0, 0, 0, 0, 0, 0, 0,
        0, 0, 1, 1, 1, 0, 0,
        0, 1, 0, 0, 0, 1, 0,
        0, 0, 1, 1, 1, 0, 0,
        0, 1, 0, 0, 0, 1, 0,
        0, 0, 1, 1, 1, 0, 0,
        0, 0, 0, 0, 0, 0, 0);

    antigen = new Antibody(arrayWidth);
    antigen.setLightChain(lightChain);

    for (int i = 0; i < populationSize; i++) {
      population.add(new Antibody(arrayWidth));
    }

    antigen.print(arrayWidth);

    for (Antibody antibody : population) {
      antibody.print(arrayWidth);
    }

    for (Antibody antibody : population) {
      antibody.fitness(antigen.getLithChain());
    }

    System.out.println("Max fitness: " + (arrayWidth * arrayWidth));
    List<Antibody> bestResults = getBestResults(4);

    System.out.println("Best results: ");
    for (Antibody antibody: bestResults){
      System.out.println(antibody.getLithChain());
    }

  }

  public List<Antibody> getBestResults(int quantity){
    sortPopulation();

    for(Antibody antibody : population){
      System.out.println(antibody.getAfinity());
    }

    List<Antibody> bestResults = new ArrayList<>();
    for(int i = 0; i < quantity; i ++){
      bestResults.add(population.get(i));
    }

    return bestResults;
  }

  public void sortPopulation() {
    Collections.sort(population);
  }

  public void cloneRate() {

  }

  public void mutation() {

  }
}
