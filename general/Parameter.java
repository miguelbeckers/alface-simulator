package general;

public class Parameter {
    private String name;
    private Double cost;
    private Double minQuantity;
    private Double maxQuantity;
    private Double influence;

    public Parameter(String name, Double minQuantity, Double maxQuantity, Double cost, Double influence) {
        this.name = name;
        this.minQuantity = minQuantity;
        this.maxQuantity = maxQuantity;
        this.cost = cost;
        this.influence = influence;
    }

    public Double getRealQuantity(Double normalQuantity) {
        if (normalQuantity < 0 || normalQuantity > 1)
            throw new RuntimeException("Error: Expected a value between 0 and 1, received: " + normalQuantity);
        return (maxQuantity - minQuantity) * normalQuantity + minQuantity;
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

    public void setMinQuantity(Double minQuantity) {
        this.minQuantity = minQuantity;
    }

    public void setMaxQuantity(Double maxQuantity) {
        this.maxQuantity = maxQuantity;
    }

    public String getName() {
        return name;
    }

    public Double getMinQuantity() {
        return minQuantity;
    }

    public Double getMaxQuantity() {
        return maxQuantity;
    }

    @Override
    public String toString() {
        return "name: " + name +
                ", minQuantity: " + minQuantity +
                ", maxQuantity: " + maxQuantity +
                ", cost: " + cost +
                ", influence: " + influence;
    }
}
