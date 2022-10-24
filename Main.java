import algorithms.Genetic;
import algorithms.Greedy;
import models.Individual;
import models.Parameter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
  static List<Parameter> parameters = new ArrayList<>();
  static Scanner in = new Scanner(System.in);

  public static void main(String[] args) throws IOException {

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

    parameters.add(new Parameter("K", 0.0, 100.0, 2.0));
    parameters.add(new Parameter("N", 0.0, 100.0, 2.0));
    parameters.add(new Parameter("P", 0.0, 100.0, 2.0));

    int option;
    do {
      System.out.println("\nMain menu:\n");
      System.out.println("1 -> parameters");
      System.out.println("2 -> solve");
      System.out.println("3 -> exit");
      System.out.print("\nselect: ");

      option = in.nextInt();

      switch (option) {
        case 1 -> showParametersMenu();
        case 2 -> showSolverMenu();
      }
    } while (option != 3);
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

    System.out.print("Inform the minimum value: ");
    double minValue = in.nextDouble();

    System.out.print("Inform the maximum value: ");
    double maxValue = in.nextDouble();

    System.out.print("Inform the parameter cost: ");
    double cost = in.nextDouble();

    parameters.add(new Parameter(name, minValue, maxValue, cost));
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

    System.out.print("Inform the minimum value: ");
    double minValue = in.nextDouble();

    System.out.print("Inform the maximum value: ");
    double maxValue = in.nextDouble();

    System.out.print("Inform the parameter cost: ");
    double cost = in.nextDouble();

    parameters.get(index).setName(name);
    parameters.get(index).setMinValue(minValue);
    parameters.get(index).setMaxValue(maxValue);
    parameters.get(index).setCost(cost);

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

  public static void showSolverMenu() {
    int option;
    do {
      System.out.println("\nSolver menu:\n");
      System.out.println("0 <- back");
      System.out.println("1 -> greedy");
      System.out.println("2 -> genetic");
      System.out.println("3 -> immunological");
      System.out.println("4 -> ant colony");
      System.out.print("\nselect: ");

      option = in.nextInt();

      switch (option) {
        case 1 -> greedyMenu();
        case 2 -> geneticMenu();
        case 3 -> immunologicalMenu();
        case 4 -> antColonyMenu();
      }
    } while (option != 0);
  }

  public static void greedyMenu() {
    System.out.println("\nGreedy algorithm:\n");

    System.out.print("Inform the step (e.g. 0.25): ");
    double step = in.nextDouble();

    System.out.print("Inform the the individual value (e.g. 14.00): ");
    double individualValue = in.nextDouble();

    Greedy greedy = new Greedy(parameters);
    Individual result = greedy.solve(individualValue, step);

    System.out.println("\nThe solution is:");
    System.out.println(result);
    System.out.println(result.getValues());
  }

  public static void geneticMenu() {
    System.out.println("\nGenetic algorithm:\n");

    System.out.print("Inform the population size (e.g. 20): ");
    int populationSize = in.nextInt();

    System.out.print("Inform the the individual value (e.g. 14.00): ");
    double individualValue = in.nextDouble();

    System.out.print("Inform the the mutation rate (e.g. 0.1): ");
    double mutationRate = in.nextDouble();

    System.out.print("Inform the the generation number (e.g. 100): ");
    int generationNumber = in.nextInt();

    Genetic genetic = new Genetic(populationSize, parameters);
    Individual result = genetic.solve(individualValue, mutationRate, generationNumber);

    System.out.println("\nThe solution is:");
    System.out.println(result);
    System.out.println(result.getValues());
  }

  public static void immunologicalMenu() {
    System.out.println("\nImmunological algorithm:\n");
    System.out.println("The immunological algorithm is not implemented yet");
  }

  public static void antColonyMenu() {
    System.out.println("\nAnt Colony algorithm:\n");
    System.out.println("The ant colony algorithm is not implemented yet");
  }
}
