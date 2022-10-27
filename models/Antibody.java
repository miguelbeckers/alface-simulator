package models;

import java.util.List;
import java.util.ArrayList;

public class Antibody implements Comparable<Antibody>{
  private List<Integer> lightChain = new ArrayList<>();
  private Integer afinity;
  
  public Antibody(int arrayWidth){
    arrayWidth *= arrayWidth;

    for (int i = 0; i < arrayWidth; i++) {
      Double randomValue = (double) Math.round(Math.random() * 100) / 100;
      lightChain.add(randomValue > 0.5 ? 1 : 0);
    }
  }

  public void print(int arrayWidth){
    System.out.println(afinity);
    
    int count = 0;
    for (int i = 0; i < lightChain.size(); i++) {
      System.out.print(lightChain.get(i) == 0 ? "." : "@");
      if (++count == arrayWidth) {
        System.out.print("\n");
        count = 0;
      }
    }
  }

  public int fitness(List<Integer> antigen) {
    int sum = 0;
    for (int i = 0; i < antigen.size(); i++) {
      if (antigen.get(i) == lightChain.get(i)) {
        sum += 1;
      }
    }
    this.afinity = sum;
    return sum;
  }

  public void setLightChain(List<Integer> lightChain){
    this.lightChain = lightChain;
  }

  public Integer getAfinity(){
    return afinity;
  }

  public List<Integer> getLithChain(){
    return lightChain;
  }

  @Override
  public int compareTo(Antibody antibody) {
    return antibody.getAfinity().compareTo(this.getAfinity());
  }
}
