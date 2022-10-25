package models;

import java.util.ArrayList;
import java.util.List;

public class Node {
  private double value;
  private List<Node> childs = new ArrayList<>();

  public Node(double value) {
    this.value = value;
  }

  public double getValue(){
    return value;
  }

  public List<Node> getChilds(){
    return childs;
  }

  public void insertChild(Node child){
    childs.add(child);
  }
}
