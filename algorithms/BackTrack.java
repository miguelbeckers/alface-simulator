package algorithms;

import models.Individual;
import models.Parameter;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class BackTrack {
  List<Parameter> parameters = new ArrayList<>();
  Individual bestSolution;

  public BackTrack(List<Parameter> parameters) {
    this.parameters = parameters;
    Individual individual = new Individual(parameters);
    bestSolution = individual;
  }

  private class Node {
    double value;
    List<Node> childs = new ArrayList<>();

    Node(double value) {
      this.value = value;
    }
  }

  private class Tree {
    Node root;
    int numberOfNodes;

    Tree() {
      this.root = new Node(-1);
      this.numberOfNodes = 1;
    }
  }

  private Tree buildTree(double step) {
    Tree tree = new Tree();

    buildRecursive(tree, tree.root, 1, step);
    return tree;
  }

  private void buildRecursive(Tree tree, Node root, int level, double step) {
    for (double i = 0; i <= 1; i += step) {
      Node node = new Node(i);
      root.childs.add(node);
      tree.numberOfNodes += 1;

      if (level < parameters.size()) {
        buildRecursive(tree, node, level + 1, step);
      }
    }
  }

  private void printTree(Tree tree) {
    System.out.println(tree.numberOfNodes);
    printRecursive(tree.root);
  }

  private void printRecursive(Node node) {
    System.out.println(node.value);

    for (int i = 0; i < node.childs.size(); i++) {
      printRecursive(node.childs.get(i));
    }
  }

  private void printCombinations(Tree tree) {
    printCombinationRecursive(tree.root, new ArrayList<>());
  }

  private void printCombinationRecursive(Node node, List<Double> combination) {
    combination.add(node.value);

    for (int i = 0; i < node.childs.size(); i++) {
      printCombinationRecursive(node.childs.get(i), combination);
    }

    if ((combination.size() - 1) == parameters.size()) {
      List<Double> combo = new ArrayList<>();
      for (int i = 1; i < combination.size(); i++) {
        combo.add(combination.get(i));
      }
      System.out.println(combo);
    }

    combination.remove(combination.size() - 1);
  }

  public void solveAsTree(double step) {
    Tree tree = buildTree(step);
    // printTree(tree);
    printCombinations(tree);
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
