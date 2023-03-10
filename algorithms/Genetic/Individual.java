package algorithms.Genetic;

import general.Parameter;
import general.Product;
import general.Setting;

import java.util.ArrayList;
import java.util.List;

public class Individual extends Product {
    public Individual(List<Setting> settings, List<Parameter> parameters, List<Double> chromosome) {
        super(settings, parameters, chromosome);
    }

    public List<Individual> crossover(Individual individual) {
        int cut = (int) Math.round(Math.random() * quantities.size());

        List<Double> chromosome1 = new ArrayList<>();
        chromosome1.addAll(individual.getChromosome().subList(0, cut));
        chromosome1.addAll(quantities.subList(cut, quantities.size()));

        List<Double> chromosome2 = new ArrayList<>();
        chromosome2.addAll(quantities.subList(0, cut));
        chromosome2.addAll(individual.getChromosome().subList(cut, quantities.size()));

        Individual son1 = new Individual(settings, parameters, chromosome1);
        son1.setId(id + 1);

        Individual son2 = new Individual(settings, parameters, chromosome2);
        son2.setId(id + 1);

        List<Individual> sons = new ArrayList<>();

        sons.add(son1);
        sons.add(son2);

        return sons;
    }

    public void mutation(Double mutationRate) {
        for (int i = 0; i < quantities.size(); i++) {
            if (Math.random() < mutationRate) {
                quantities.set(i, (double) Math.round(Math.random() * 100) / 100);
            }
        }
    }

    public List<Double> getChromosome() {
        return quantities;
    }
}
