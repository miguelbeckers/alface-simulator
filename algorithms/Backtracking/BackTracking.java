package algorithms.Backtracking;

import general.Product;
import general.Parameter;
import general.Setting;

import javax.swing.*;
import java.util.List;
import java.util.ArrayList;

public class BackTracking {
  private List<Setting> settings;
  private List<Parameter> parameters;
  private Product bestSolution;

  public BackTracking(List<Setting> settings, List<Parameter> parameters) {
    this.settings = settings;
    this.parameters = parameters;
    initializeBestSolution();
  }

  private void initializeBestSolution(){
    List<Double> quantities = new ArrayList<>();

    for(int i = 0; i < parameters.size(); i ++){
      quantities.add((double) 0);
    }

    bestSolution = new Product(settings, parameters, quantities);
  }

  public Product solve(double valuePerUnit, double step) {
    bestSolution.fitness(valuePerUnit);

    Tree tree = new Tree(parameters.size(), step);
    List<List<Double>> allCombos = tree.getAllCombos();

    for (int i = 0; i < allCombos.size(); i++) {
      List<Double> chromosome = new ArrayList<>(allCombos.get(i));
      Product product = new Product(settings, parameters, chromosome);

      product.fitness(valuePerUnit);
      product.setId((long) i);

      if(settings.get(9).getValue()) {
        System.out.println(product);
      }

      if (bestSolution.compareTo(product) > 0) {
        bestSolution = product;
      }
    }

    return bestSolution;
  }
}
