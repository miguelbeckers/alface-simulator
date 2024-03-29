package algorithms.ImmunologicalSystem;

import general.Parameter;
import general.Lettuce;
import general.Config;

import java.util.List;
import java.util.ArrayList;

public class Antibody extends Lettuce {
    public Antibody(List<Config> configs, List<Parameter> parameters, List<Double> lightChain) {
        super(configs, parameters, lightChain);
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

            if (randomValue >= mutationRate) {
                randomValue = (double) Math.round(Math.random() * 100) / 100;
                lightChain.add(randomValue);

            } else {
                lightChain.add(getLightChain().get(i));
            }
        }

        setLightChain(lightChain);
//        fitness(value);
        fitnessModel(value);
    }
}
