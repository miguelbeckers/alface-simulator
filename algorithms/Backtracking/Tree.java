package algorithms.Backtracking;

import java.util.ArrayList;
import java.util.List;

public class Tree {
  private Node root;
  private int levelNumber;
  private double descendantNumber;

  public Tree(int levelNumber, double descendantNumber) {
    this.root = new Node(-1);
    this.levelNumber = levelNumber;
    this.descendantNumber = descendantNumber;

    build();
  }

  private void build() {
    createDescendants(root, 1);
  }

  private void createDescendants(Node root, int level) {
    for (double i = 0; i <= 1; i += descendantNumber) {
      Node node = new Node((double) Math.round(i * 100) / 100);
      root.getChilds().add(node);

      if (level < levelNumber) {
        createDescendants(node, level + 1);
      }
    }
  }

  public List<List<Double>> getAllCombos() {
    List<List<Double>> allCombos = new ArrayList<>();
    getCombo(root, new ArrayList<>(), allCombos);

    for(int i = 0; i < allCombos.size(); i++){
      allCombos.get(i).remove(0);
    }

    return allCombos;
  }

  private void getCombo(Node node, List<Double> combo, List<List<Double>> allCombos) {
    combo.add(node.getValue());

    for (int i = 0; i < node.getChilds().size(); i++) {
      getCombo(node.getChilds().get(i), combo, allCombos);
    }

    if (combo.size() - 1 == levelNumber ) {
      allCombos.add(new ArrayList<>(combo));
    }

    combo.remove(combo.size() - 1);
  }
}