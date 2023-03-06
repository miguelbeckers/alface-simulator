package model;

public class Parameter {
    private String name;
    private Double cost;
    private Double minValue;
    private Double maxValue;
    private Double influence;

    public Parameter(String name, Double minValue, Double maxValue, Double cost, Double influence) {
        this.name = name;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.cost = cost;
        this.influence = influence;
    }

    public Double getValue(Double normalValue) {
        if (normalValue < 0 || normalValue > 1)
            throw new RuntimeException("Error: Expected a value between 0 and 1, received: " + normalValue);
        return (maxValue - minValue) * normalValue + minValue;
    }

    public Double getInfluence() {
        return influence;
    }

    public Double getCost() {
        return cost;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public void setInfluence(Double influence) {
        this.influence = influence;
    }

    public void setMinValue(Double minValue) {
        this.minValue = minValue;
    }

    public void setMaxValue(Double maxValue) {
        this.maxValue = maxValue;
    }

    public String getName() {
        return name;
    }

    public Double getMinValue() {
        return minValue;
    }

    public Double getMaxValue() {
        return maxValue;
    }

    @Override
    public String toString() {
        return "name: " + name +
                ", minValue: " + minValue +
                ", maxValue: " + maxValue +
                ", cost: " + cost +
                ", influence: " + influence;
    }
}
