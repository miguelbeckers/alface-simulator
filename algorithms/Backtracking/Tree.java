package algorithms.Backtracking;

import java.util.ArrayList;
import java.util.List;

public class Tree {
  private Node root;
  private int levelNumber;
  private double childNumber;

  public Tree(int levelNumber, double childNumber) {
    this.root = new Node(-1);
    this.levelNumber = levelNumber;
    this.childNumber = childNumber;

    build();
  }

  private void build() {
    createChilds(root, 1);
  }

  private void createChilds(Node root, int level) {
    for (double i = 0; i <= 1; i += childNumber) {
      Node node = new Node(i);
      root.getChilds().add(node);

      if (level < levelNumber) {
        createChilds(node, level + 1);
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