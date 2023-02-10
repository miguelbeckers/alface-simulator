package model;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Product implements Comparable<Product> {
  Long id = 0L;
  List<Parameter> parameters;
  Double cost;
  Double value;
  Double profit;
  Double mass;
  List<Double> quantities = new ArrayList<>();

  public Product(List<Parameter> parameters, List<Double> quantities) {
    this.parameters = parameters;
    this.quantities = quantities;
  }

  private void calculateCost() {
    double cost = 0;
    for (int i = 0; i < parameters.size(); i++) {
      cost = parameters.get(i).getCost() * quantities.get(i);
    }
    this.cost = cost;
  }

  private void calculateMass() {
    double result = quantities.stream().reduce(0.0, (sum, element) -> sum + (element * 10));
    this.mass = Math.log(result + 1) / 3;
  }

  public void evaluate(double valuePerKilogram) {
    calculateCost();
    calculateMass();

    this.value = mass * valuePerKilogram;
    this.profit = value - cost > 0 ? value - cost : 0.01;
  }

  public Long getId() {
    return id;
  }

  public List<Parameter> getParameters() {
    return parameters;
  }

  public Double getCost() {
    return cost;
  }

  public Double getValue() {
    return value;
  }

  public Double getProfit() {
    return profit;
  }

  public Double getMass() {
    return mass;
  }

  public List<Double> getQuantities() {
    return quantities;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getValues() {
    StringBuilder values = new StringBuilder("values: ");
    for (int i = 0; i < quantities.size(); i++) {
      values
          .append(parameters.get(i).getName())
          .append(" - ")
          .append(Math.round(
              parameters.get(i).getValue(quantities.get(i)) * 10) / 10);
      if (i + 1 != quantities.size()) {
        values.append(", ");
      }
    }
    return values.toString();
  }

  @Override
  public String toString() {
    return "id: " + this.getId()
        + "\tMass: " + new DecimalFormat("#,##0.00 kg").format(this.getMass())
        + "\tValue: " + new DecimalFormat("R$ #,##0.00").format(this.getValue())
        + "\tCost: " + new DecimalFormat("R$ #,##0.00").format(this.getCost())
        + "\tProfit: " + new DecimalFormat("R$ #,##0.00").format(this.getProfit());
  }

  @Override
  public int compareTo(Product product) {
    return product.getProfit().compareTo(this.getProfit());
  }
}
