package general;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Lettuce implements Cloneable, Comparable<Lettuce> {
    protected Long id = 0L;
    protected List<Config> configs;
    protected List<Parameter> parameters;
    protected List<Double> quantities;
    protected Double cost;
    protected Double value;
    protected Double profit;
    protected Double mass;
    protected Scanner in = new Scanner(System.in);

    public Lettuce(List<Config> configs, List<Parameter> parameters, List<Double> quantities) {
        this.configs = configs;
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

    private void calculateMassGeneric() {
        mass = 0D;
        for (int i = 0; i < parameters.size(); i++) {
            double x = quantities.get(i);
            double y = 1D / 2 * Math.sin(Math.PI * x - Math.PI / 2) + 1D / 2;
            mass += y * parameters.get(i).getInfluence();
        }
        mass = (double) Math.round(mass * 1000) / 1000;
    }

    private void calculateMassModel(){}

    private void readMass(){
        System.out.print("Inform the mass: ");
        mass = in.nextDouble();
    }

    public void fitness(double valuePerKilogram) {
        calculateCost();

        if (configs.get(10).getValue()) {
            calculateMassGeneric();
        } else {
            readMass();
        }

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

    public String getNormalQuantitiesAsString() {
        StringBuilder realQuantities = new StringBuilder("[ ");

        for (int i = 0; i < quantities.size(); i++) {
            realQuantities.append(parameters.get(i).getName()).append(": ").append(quantities.get(i));
            if (i + 1 != quantities.size()) {
                realQuantities.append(", ");
            }
        }
        realQuantities.append(" ]");
        return realQuantities.toString();
    }

    public String getRealQuantitiesAsString() {
        StringBuilder realQuantities = new StringBuilder("[ ");

        for (int i = 0; i < quantities.size(); i++) {
            double realQuantity = (double) Math.round(parameters.get(i).getRealQuantity(quantities.get(i)) * 10) / 10;
            realQuantities.append(parameters.get(i).getName()).append(": ").append(realQuantity);
            if (i + 1 != quantities.size()) {
                realQuantities.append(", ");
            }
        }
        realQuantities.append(" ]");
        return realQuantities.toString();
    }

    public String getProfitBar() {
        StringBuilder profitBar = new StringBuilder();
        for (int i = 0; i < profit * 10; i++) {
            profitBar.append("█");
        }
        return profitBar.toString();
    }

    public String getMassBar() {
        StringBuilder massBar = new StringBuilder();
        for (int i = 0; i < mass * 100; i++) {
            massBar.append("═");
            ;
        }
        return massBar.toString();
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        String formattedMass = new DecimalFormat("#,##0.00 kg").format(this.getMass());
        String formattedValue = new DecimalFormat("R$ #,##0.00").format(this.getValue());
        String formattedCost = new DecimalFormat("R$ #,##0.00").format(this.getCost());
        String formattedProfit = new DecimalFormat("R$ #,##0.00").format(this.getProfit());

        return "id: " + this.getId()
                + (configs.get(0).getValue() ? "\tMass: " + formattedMass : "")
                + (configs.get(1).getValue() ? "\tValue: " + formattedValue : "")
                + (configs.get(2).getValue() ? "\tCost: " + formattedCost : "")
                + (configs.get(3).getValue() ? "\tProfit: " + formattedProfit : "")
                + (configs.get(4).getValue() ? "\tNormal: " + getNormalQuantitiesAsString() : "")
                + (configs.get(5).getValue() ? "\tReal: " + getRealQuantitiesAsString() : "")
                + (configs.get(6).getValue() ? "\nProf: " + getProfitBar() : "")
                + (configs.get(7).getValue() ? "\nMass: " + getMassBar() : "");
    }

    @Override
    public Lettuce clone() {
        try {
            Lettuce clone = (Lettuce) super.clone();
            clone.setQuantities(new ArrayList<>(quantities));
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    @Override
    public int compareTo(Lettuce lettuce) {
        return lettuce.getProfit().compareTo(this.getProfit());
    }
}
