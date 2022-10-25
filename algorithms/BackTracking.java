package algorithms;

import models.Individual;
import models.Parameter;

import java.util.List;
import java.util.ArrayList;

public class BackTracking {
  private List<Parameter> parameters = new ArrayList<>();
  private Individual bestSolution;

  class Node {
    double value;
    List<Node> childs = new ArrayList<>();

    Node(double value) {
      this.value = value;
    }
  }

  class Tree {
    Node root;

    Tree() {
      this.root = new Node(-1);
    }
  }

  public BackTracking(List<Parameter> parameters) {
    this.parameters = parameters;
    Individual individual = new Individual(parameters);
    bestSolution = individual;
  }

  public Individual solve(double individualValue, double step) {
    bestSolution.fitness(individualValue);

    Tree tree = buildTree(step);
    List<List<Double>> allCombos = getallCombos(tree);

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

  private Tree buildTree(double step) {
    Tree tree = new Tree();
    createChilds(tree, tree.root, 1, step);
    return tree;
  }

  private void createChilds(Tree tree, Node root, int level, double step) {
    for (double i = 0; i <= 1; i += step) {
      Node node = new Node(i);
      root.childs.add(node);

      if (level < parameters.size()) {
        createChilds(tree, node, level + 1, step);
      }
    }
  }

  private List<List<Double>> getallCombos(Tree tree) {
    List<List<Double>> allCombos = new ArrayList<>();
    getCombo(tree.root, new ArrayList<>(), allCombos);

    for (int i = 0; i < allCombos.size(); i++) {
      allCombos.get(i).remove(0);
    }

    return allCombos;
  }

  private void getCombo(Node node, List<Double> combo, List<List<Double>> allCombos) {
    combo.add(node.value);

    for (int i = 0; i < node.childs.size(); i++) {
      getCombo(node.childs.get(i), combo, allCombos);
    }

    if ((combo.size() - 1) == parameters.size()) {
      allCombos.add(new ArrayList<>(combo));
    }

    combo.remove(combo.size() - 1);
  }
}
