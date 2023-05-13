import algorithms.Backtracking.BackTracking;
import algorithms.Genetic.Genetic;
import algorithms.Genetic.Individual;
import algorithms.Immunological.Antibody;
import algorithms.Immunological.Immunological;
import general.Parameter;
import general.Product;
import general.Setting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static List<Parameter> parameters = new ArrayList<>();
    public static List<Setting> settings = new ArrayList<>();
    public static Scanner in = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("""

                         ┌████┐ ┌██┐   ┌█████┐ ┌████┐ ┌█████┐┌█████┐
                        ┌██┌┐██┐│██│   │██┌──┘┌██┌┐██┐│██┌──┘│██┌──┘
                        │██████││██│   │████┐ │██████││██│   │████┐
                        │██┌┐██││██│   │██┌─┘ │██┌┐██││██│   │██┌─┘
                ┌██████┐│██││██││█████┐│██│   │██││██││█████┐│█████┐┌██████┐
                └──────┘└──┘└──┘└─────┘└──┘   └──┘└──┘└─────┘└─────┘└──────┘
                        ╔═╗                   ╔══╗         ╔═╗
                ╔═══╗   ╚═╝                   ╚╗ ║   ╔═══╗ ║ ║
                ║ ══╩═╗╔══╗ ╔═════════╗╔═╗ ╔═╗ ║ ║ ╔═╩══ ║╔╝ ╚═╗╔═════╗╔════╗
                ╚═══╗ ║╚╗ ║ ║ ╔═╗ ╔═╗ ║║ ║ ║ ║ ║ ║ ║ ╔═╗ ║╚╗ ╔═╝║ ╔═╗ ║║ ╔══╝
                ╔═══╝ ║ ║ ╚╗║ ║ ║ ║ ║ ║║ ╚═╝ ║ ║ ╚╗║ ╚═╝ ║ ║ ╚╗ ║ ╚═╝ ║║ ║
                ╚═════╝ ╚══╝╚═╝ ╚═╝ ╚═╝╚═════╝ ╚══╝╚═════╝ ╚══╝ ╚═════╝╚═╝""");

        parameters.add(new Parameter("N", 0.2, 0.8, 3.52, 0.6));
        parameters.add(new Parameter("P", 0.3, 0.9, 3.15, 0.3));
        parameters.add(new Parameter("K", 0.5, 1.2, 6.65, 0.1));

        settings.add(new Setting(0L, "printMass", true));
        settings.add(new Setting(1L, "printValue", true));
        settings.add(new Setting(2L, "printCost", true));
        settings.add(new Setting(3L, "printProfit", true));
        settings.add(new Setting(4L, "printNormalQuantities", true));
        settings.add(new Setting(5L, "printRealQuantities", false));
        settings.add(new Setting(6L, "printProfitBar", true));
        settings.add(new Setting(7L, "printMassBar", true));
        settings.add(new Setting(8L, "printEachIteration", false));
        settings.add(new Setting(9L, "printTheBestOfGeneration", true));
        settings.add(new Setting(10L, "autoFitness", true));

        int option;
        do {
            System.out.println("\nMain menu:\n");
            System.out.println("0 <- exit");
            System.out.println("1 -> solver");
            System.out.println("2 -> parameters");
            System.out.println("3 -> settings");
            System.out.print("\nselect: ");

            option = in.nextInt();

            switch (option) {
                case 1 -> showSolverMenu();
                case 2 -> showParametersMenu();
                case 3 -> showSettingsMenu();
            }
        } while (option != 0);
    }

    public static void showSolverMenu() {
        int option;
        do {
            System.out.println("\nSolver menu:\n");
            System.out.println("0 <- back");
            System.out.println("1 -> backTracking");
            System.out.println("2 -> genetic");
            System.out.println("3 -> immunological");
            System.out.println("4 -> ant colony");
            System.out.print("\nselect: ");

            option = in.nextInt();

            switch (option) {
                case 1 -> backTrackingMenu();
                case 2 -> geneticMenu();
                case 3 -> immunologicalMenu();
                case 4 -> antColonyMenu();
            }
        } while (option != 0);
    }

    public static void backTrackingMenu() {
        double step = 0.25;
        double marketValue = 14;
        int iterations = 1;

        int option;
        do {
            System.out.println("\nBackTracking algorithm:\n");
            System.out.println("0 <- back");
            System.out.println("1 -> step: " + step);
            System.out.println("2 -> marketValue: " + marketValue);
            System.out.println("3 -> iterations: " + iterations);
            System.out.println("4 -> start");
            System.out.print("\nselect: ");

            option = in.nextInt();

            switch (option) {
                case 1 -> {
                    System.out.print("\nInform the step (e.g. 0.25): ");
                    step = in.nextDouble();
                }
                case 2 -> {
                    System.out.print("\nInform the market value (e.g. 14.00): ");
                    marketValue = in.nextDouble();
                }
                case 3 -> {
                    System.out.print("\nInform the number of iterations (e.g. 1): ");
                    iterations = in.nextInt();
                }
                case 4 -> {
                    for (int i = 0; i < iterations; i++) {
                        BackTracking backTrack = new BackTracking(settings, parameters);
                        Product result = backTrack.solve(marketValue, step);
                        System.out.println("Iteration: " + i + " Result: ");
                        System.out.println(result);
                    }
                    return;
                }
            }
        } while (option != 0);
    }

    public static void geneticMenu() {
        double marketValue = 14;
        int populationSize = 20;
        int generationNumber = 100;
        double mutationRate = 0.1;
        int iterations = 1;

        int option;
        do {
            System.out.println("\nGenetic algorithm:\n");
            System.out.println("0 <- back");
            System.out.println("1 -> marketValue: " + marketValue);
            System.out.println("2 -> populationSize: " + populationSize);
            System.out.println("3 -> generationNumber: " + generationNumber);
            System.out.println("4 -> mutationRate: " + mutationRate);
            System.out.println("5 -> iterations: " + iterations);
            System.out.println("6 -> start");
            System.out.print("\nselect: ");

            option = in.nextInt();

            switch (option) {
                case 1 -> {
                    System.out.print("\nInform the market value (e.g. 14.00): ");
                    marketValue = in.nextDouble();
                }
                case 2 -> {
                    System.out.print("\nInform the population size (e.g. 20): ");
                    populationSize = in.nextInt();
                }
                case 3 -> {
                    System.out.print("\nInform the the generation number (e.g. 100): ");
                    generationNumber = in.nextInt();
                }
                case 4 -> {
                    System.out.print("\nInform the the mutation rate (e.g. 0.1): ");
                    mutationRate = in.nextDouble();
                }
                case 5 -> {
                    System.out.print("\nInform the number of iterations (e.g. 1): ");
                    iterations = in.nextInt();
                }
                case 6 -> {
                    for (int i = 0; i < iterations; i++) {
                        Genetic genetic = new Genetic(settings, parameters, populationSize);
                        Individual result = genetic.solve(marketValue, mutationRate, generationNumber);
                        System.out.println("Iteration: " + i + " Result: ");
                        System.out.println(result);
                    }
                    return;
                }
            }
        } while (option != 0);
    }

    public static void immunologicalMenu() {
        double marketValue = 14;
        int populationSize = 20;
        int generationNumber = 100;
        double hyperFactor = 0.1;
        int bestsQuantity = 3;
        double objective = 15;
        int iterations = 1;

        int option;
        do {
            System.out.println("\nImmunological algorithm:\n");
            System.out.println("0 <- back");
            System.out.println("1 -> marketValue: " + marketValue);
            System.out.println("2 -> populationSize: " + populationSize);
            System.out.println("3 -> generationNumber: " + generationNumber);
            System.out.println("4 -> hyperFactor: " + hyperFactor);
            System.out.println("5 -> bestsQuantity: " + bestsQuantity);
            System.out.println("6 -> objective: " + objective);
            System.out.println("7 -> iterations: " + iterations);
            System.out.println("8 -> start");
            System.out.print("\nselect: ");

            option = in.nextInt();

            switch (option) {
                case 1 -> {
                    System.out.print("\nInform the market value (e.g. 14.00): ");
                    marketValue = in.nextDouble();
                }
                case 2 -> {
                    System.out.print("\nInform the population size (e.g. 20): ");
                    populationSize = in.nextInt();
                }
                case 3 -> {
                    System.out.print("\nInform the generation number (e.g. 100): ");
                    generationNumber = in.nextInt();
                }
                case 4 -> {
                    System.out.print("\nInform the hypermutation factor (e.g. 0.1): ");
                    hyperFactor = in.nextDouble();
                }
                case 5 -> {
                    System.out.print("\nInform the number of best antibodies to clone (e.g. 3): ");
                    bestsQuantity = in.nextInt();
                }
                case 6 -> {
                    System.out.print("\nInform the objective value (e.g. 15): ");
                    objective = in.nextDouble();
                }
                case 7 -> {
                    System.out.print("\nInform the number of iterations (e.g. 1): ");
                    iterations = in.nextInt();
                }
                case 8 -> {
                    for (int i = 0; i < iterations; i++) {
                        Immunological immunological = new Immunological(settings, parameters, populationSize);
                        Antibody result = immunological.solve(marketValue, generationNumber, bestsQuantity, hyperFactor, objective);
                        System.out.println("Iteration: " + i + " Result: ");
                        System.out.println(result);
                    }
                    return;
                }
            }
        } while (option != 0);
    }

    public static void antColonyMenu() {
        System.out.println("\nAnt Colony algorithm:\n");
        System.out.println("The ant colony algorithm is not implemented yet");
    }

    public static void showParametersMenu() {
        int option;
        do {
            System.out.println("\nParameters menu:\n");
            System.out.println("0 <- back");
            System.out.println("1 -> show");
            System.out.println("2 -> add");
            System.out.println("3 -> edit");
            System.out.println("4 -> remove");
            System.out.print("\nselect: ");

            option = in.nextInt();

            switch (option) {
                case 1 -> showParameters();
                case 2 -> addParameter();
                case 3 -> editParameter();
                case 4 -> removeParameter();
            }
        } while (option != 0);
    }

    public static void showParameters() {
        System.out.println("\nParameter list:\n");
        if (parameters.size() == 0) {
            System.out.println("The parameters list is empty");
            return;
        }
        for (int i = 0; i < parameters.size(); i++) {
            System.out.println(i + " - " + parameters.get(i).toString());
        }
    }

    public static void addParameter() {
        System.out.println("\nAdd parameter:\n");
        in.nextLine();

        System.out.print("Inform the parameter name: ");
        String name = in.nextLine();

        System.out.print("Inform the minimum quantity: ");
        double minQuantity = in.nextDouble();

        System.out.print("Inform the maximum quantity: ");
        double maxQuantity = in.nextDouble();

        System.out.print("Inform the parameter cost: ");
        double cost = in.nextDouble();

        System.out.print("Inform the parameter influence: ");
        double influence = in.nextDouble();

        parameters.add(new Parameter(name, minQuantity, maxQuantity, cost, influence));
        System.out.println("\nThe parameter has been added");
    }

    public static void editParameter() {
        System.out.println("\nEdit parameter:\n");

        System.out.print("Inform the parameter index: ");
        int index = in.nextInt();

        if (index >= parameters.size() || index < 0) {
            System.out.println("\nParameter not found");
            return;
        }

        in.nextLine();
        System.out.print("Inform the parameter name: ");
        String name = in.nextLine();

        System.out.print("Inform the minimum quantity: ");
        double minValue = in.nextDouble();

        System.out.print("Inform the maximum quantity: ");
        double maxValue = in.nextDouble();

        System.out.print("Inform the parameter cost: ");
        double cost = in.nextDouble();

        System.out.print("Inform the parameter influence: ");
        double influence = in.nextDouble();

        parameters.get(index).setName(name);
        parameters.get(index).setMinQuantity(minValue);
        parameters.get(index).setMaxQuantity(maxValue);
        parameters.get(index).setCost(cost);
        parameters.get(index).setInfluence(influence);

        System.out.println("\nThe parameter has been updated");
    }

    public static void removeParameter() {
        System.out.println("\nRemove parameter:\n");

        System.out.print("Inform the parameter index: ");
        int index = in.nextInt();

        if (index >= parameters.size() || index < 0) {
            System.out.println("\nParameter not found");
            return;
        }

        parameters.remove(index);
        System.out.println("\nThe parameter has been removed");
    }

    public static void showSettingsMenu() {
        int option;
        do {
            System.out.println("\nSettings menu:\n");
            System.out.println("0 <- back");
            for (int i = 0; i < settings.size(); i++) {
                System.out.println((i + 1) + " -> " + settings.get(i));
            }
            System.out.print("\nselect: ");

            option = in.nextInt();
            if (option != 0) {
                settings.get(option - 1).setValue(!settings.get(option - 1).getValue());
            }
        } while (option != 0);
    }
}
