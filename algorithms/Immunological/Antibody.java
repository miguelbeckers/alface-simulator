package algorithms.Immunological;

import model.Parameter;
import model.Product;

import java.util.List;
import java.util.ArrayList;

public class Antibody extends Product {
    public Antibody(List<Parameter> parameters, List<Double> lightChain) {
        super(parameters, lightChain);
    }

    public Double getAffinity() {
        return profit;
    }

    public List<Double> getLightChain() {
        return quantities;
    }

    public void setLightChain(List<Double> lightChain) {
        quantities = lightChain;
    }

    public void hypermutation(double value, double factor, double objective) {
        double mutationRate = (1 - (getAffinity() / objective) * factor);

        List<Double> lightChain = new ArrayList<>();

        for (int i = 0; i < getLightChain().size(); i++) {
            double randomValue = (double) Math.round(Math.random() * 100) / 100;

            if (randomValue <= mutationRate) {
                lightChain.add(randomValue);
            } else {
                lightChain.add(getLightChain().get(i));
            }
        }

        setLightChain(lightChain);
        fitness(value);
    }
}
