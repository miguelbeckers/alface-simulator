package algorithms.Backtracking;

import general.Lettuce;
import general.Parameter;
import general.Config;

import java.util.List;
import java.util.ArrayList;

public class BackTracking {
  private List<Config> configs;
  private List<Parameter> parameters;
  private Lettuce bestSolution;

  public BackTracking(List<Config> configs, List<Parameter> parameters) {
    this.configs = configs;
    this.parameters = parameters;
    initializeBestSolution();
  }

  private void initializeBestSolution(){
    List<Double> quantities = new ArrayList<>();

    for(int i = 0; i < parameters.size(); i ++){
      quantities.add((double) 0);
    }

    bestSolution = new Lettuce(configs, parameters, quantities);
  }

  public Lettuce solve(double valuePerUnit, double step) {
//    bestSolution.fitness(valuePerUnit);
    bestSolution.fitnessModel(valuePerUnit);

    Tree tree = new Tree(parameters.size(), step);
    List<List<Double>> allCombos = tree.getAllCombos();

    for (int i = 0; i < allCombos.size(); i++) {
      List<Double> chromosome = new ArrayList<>(allCombos.get(i));
      Lettuce lettuce = new Lettuce(configs, parameters, chromosome);

//      lettuce.fitness(valuePerUnit);
      lettuce.fitnessModel(valuePerUnit);
      lettuce.setId((long) i);

      if(configs.get(9).getValue()) {
        System.out.println(lettuce);
      }

      if (bestSolution.compareTo(lettuce) > 0) {
        bestSolution = lettuce;
      }
    }

    return bestSolution;
  }
}
