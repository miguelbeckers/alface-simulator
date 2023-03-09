package model;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Product implements Cloneable, Comparable<Product> {
    protected Long id = 0L;
    protected List<Parameter> parameters;
    protected Double cost;
    protected Double value;
    protected Double profit;
    protected Double mass;
    protected List<Double> quantities;

    public Product(List<Parameter> parameters, List<Double> quantities) {
        this.parameters = parameters;
        this.quantities = quantities;
    }

    private void calculateCost() {
        double cost = 0;
        for (int i = 0; i < parameters.size(); i++) {
            double realQuantity = (double) Math.round(parameters.get(i).getRealQuantity(quantities.get(i)) * 10) / 10;
            cost = parameters.get(i).getCost() * realQuantity;
        }
        this.cost = cost;
    }

    private void calculateMass() {
        mass = 0D;
        for (int i = 0; i < parameters.size(); i++) {
            double x = quantities.get(i);
            double y = 1D / 2 * Math.sin(Math.PI * x - Math.PI / 2) + 1D / 2;
            mass += y * parameters.get(i).getInfluence();
        }
        mass = (double) Math.round(mass * 1000) / 1000;
    }

    public void fitness(double valuePerKilogram) {
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

    public void setQuantities(List<Double> quantities) {
        this.quantities = quantities;
    }

    public String getQuantitiesResume() {
        StringBuilder realQuantities = new StringBuilder("realQuantities: ");

        for (int i = 0; i < quantities.size(); i++) {
            double realQuantity = (double) Math.round(parameters.get(i).getRealQuantity(quantities.get(i)) * 10) / 10;
            realQuantities.append(parameters.get(i).getName()).append(" - ").append(realQuantity);
            if (i + 1 != quantities.size()) {
                realQuantities.append(", ");
            }
        }
        return realQuantities.toString();
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        String sProfit = "";
        for(int i = 0; i < profit * 10; i ++){
            sProfit = sProfit.concat("█");
        }

        String sMass = "";
        for(int i = 0; i < mass * 100; i ++){
            sMass = sMass.concat("═");;
        }

        return "id: " + this.getId()
                + "\tMass: " + new DecimalFormat("#,##0.00 kg").format(this.getMass())
                + "\tValue: " + new DecimalFormat("R$ #,##0.00").format(this.getValue())
                + "\tCost: " + new DecimalFormat("R$ #,##0.00").format(this.getCost())
                + "\tProfit: " + new DecimalFormat("R$ #,##0.00").format(this.getProfit())
                + "\tQuantity: " + this.getQuantities()
                + "\nProf: " + sProfit
                + "\nMass: " + sMass;
    }

    @Override
    public Product clone() {
        try {
            Product clone = (Product) super.clone();
            clone.setQuantities(new ArrayList<>(quantities));
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    @Override
    public int compareTo(Product product) {
        return product.getProfit().compareTo(this.getProfit());
    }
}
