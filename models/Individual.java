package models;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Individual implements Comparable<Individual> {
    List<Parameter> parameters;
    Double cost;
    Double value;
    Double profit;
    Double mass;
    int generation;
    List<Double> chromosome = new ArrayList<>();

    public Individual(List<Parameter> parameters) {
        this.parameters = parameters;
        initializeChromosome();
    }

    private void initializeChromosome() {
        for (int i = 0; i < parameters.size(); i++) {
            this.chromosome.add((double) Math.round(Math.random() * 100) / 100);
        }
    }

    private void calculateCost() {
        double cost = 0;
        for (int i = 0; i < parameters.size(); i++) {
            cost = parameters.get(i).getCost() * chromosome.get(i);
        }
        this.cost = cost;
    }

    private void calculateMass() {
        double quantities = chromosome.stream().reduce(0.0, (sum, element) -> sum + (element * 10));
        this.mass = Math.log(quantities + 1) / 3;
    }

    public void fitness(double valuePerKilogram) {
        calculateCost();
        calculateMass();

        this.value = mass * valuePerKilogram;
        this.profit = value - cost > 0 ? value - cost : 0.01;
    }

    public List<Individual> crossover(Individual individual) {
        int cut = (int) Math.round(Math.random() * chromosome.size());

        List<Double> chromosome1 = new ArrayList<>();
        chromosome1.addAll(individual.getChromosome().subList(0, cut));
        chromosome1.addAll(chromosome.subList(cut, chromosome.size()));

        List<Double> chromosome2 = new ArrayList<>();
        chromosome2.addAll(chromosome.subList(0, cut));
        chromosome2.addAll(individual.getChromosome().subList(cut, chromosome.size()));

        Individual son1 = new Individual(parameters);
        son1.setChromosome(chromosome1);
        son1.setGeneration(generation + 1);

        Individual son2 = new Individual(parameters);
        son2.setChromosome(chromosome2);
        son2.setGeneration(generation + 1);

        List<Individual> sons = new ArrayList<>();

        sons.add(son1);
        sons.add(son2);

        return sons;
    }

    public void mutation(Double mutationRate) {
        for (int i = 0; i < chromosome.size(); i++) {
            if (Math.random() < mutationRate) {
                chromosome.set(i, Math.random());
            }
        }
    }

    public void setGeneration(int generation) {
        this.generation = generation;
    }

    public void setChromosome(List<Double> chromosome) {
        this.chromosome = chromosome;
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

    public int getGeneration() {
        return generation;
    }

    public List<Double> getChromosome() {
        return chromosome;
    }

    public String getValues() {
        StringBuilder quantities = new StringBuilder("values: ");
        for (int i = 0; i < chromosome.size(); i++) {
            quantities
                    .append(parameters.get(i).getName())
                    .append(" - ")
                    .append(Math.round(
                      parameters.get(i).getValue(chromosome.get(i)) * 10) / 10);
            if (i + 1 != chromosome.size()) {
                quantities.append(", ");
            }
        }
        return quantities.toString();
    }

    @Override
    public String toString() {
        return "G: " + this.getGeneration()
                + "\tMass: " + new DecimalFormat("#,##0.00 kg").format(this.getMass())
                + "\tValue: " + new DecimalFormat("R$ #,##0.00").format(this.getValue())
                + "\tCost: " + new DecimalFormat("R$ #,##0.00").format(this.getCost())
                + "\tProfit: " + new DecimalFormat("R$ #,##0.00").format(this.getProfit());
    }

    @Override
    public int compareTo(Individual individual) {
        return individual.getProfit().compareTo(this.getProfit());
    }
}


