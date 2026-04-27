import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Консольна точка входу для роботи з об'єктами одягу.
 * Дозволяє створювати об'єкти похідних класів і переглядати їх в одному списку.
 */
public class Main {
    /**
     * Запускає консольне меню програми.
     *
     * @param args аргументи командного рядка
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Clothes> clothesList = new ArrayList<>();

        boolean running = true;
        while (running) {
            printMainMenu();
            int choice = readMainMenuChoice(scanner);

            switch (choice) {
                case 1:
                    createClothesFromSubmenu(scanner, clothesList);
                    break;
                case 2:
                    printAllClothes(clothesList);
                    break;
                case 3:
                    running = false;
                    System.out.println("Program finished.");
                    break;
                default:
                    System.out.println("Error: unknown menu option.");
            }
        }

        scanner.close();
    }

    private static void printMainMenu() {
        System.out.println("\nMain menu:");
        System.out.println("1. Create new object");
        System.out.println("2. Show all clothes");
        System.out.println("3. Exit");
    }

    private static int readMainMenuChoice(Scanner scanner) {
        return readNonNegativeInt(scanner, "Choose an option: ");
    }

    private static void printCreateSubmenu() {
        System.out.println("\nChoose clothes type:");
        System.out.println("1. Pants");
        System.out.println("2. Shirt");
        System.out.println("3. Jacket");
        System.out.println("4. Hat");
        System.out.println("0. Return to main menu");
    }

    private static void createClothesFromSubmenu(Scanner scanner, List<Clothes> clothesList) {
        boolean inSubmenu = true;

        while (inSubmenu) {
            printCreateSubmenu();
            int choice = readNonNegativeInt(scanner, "Choose clothes type: ");

            switch (choice) {
                case 1:
                    System.out.println("\nCreating pants:");
                    clothesList.add(createPants(scanner));
                    System.out.println("Pants added successfully.");
                    inSubmenu = false;
                    break;
                case 2:
                    System.out.println("\nCreating shirt:");
                    clothesList.add(createShirt(scanner));
                    System.out.println("Shirt added successfully.");
                    inSubmenu = false;
                    break;
                case 3:
                    System.out.println("\nCreating jacket:");
                    clothesList.add(createJacket(scanner));
                    System.out.println("Jacket added successfully.");
                    inSubmenu = false;
                    break;
                case 4:
                    System.out.println("\nCreating hat:");
                    clothesList.add(createHat(scanner));
                    System.out.println("Hat added successfully.");
                    inSubmenu = false;
                    break;
                case 0:
                    System.out.println("Returning to main menu.");
                    inSubmenu = false;
                    break;
                default:
                    System.out.println("Error: unknown menu option.");
            }
        }
    }

    private static void printAllClothes(List<Clothes> clothesList) {
        if (clothesList.isEmpty()) {
            System.out.println("\nThe clothes list is empty.");
            return;
        }

        System.out.println("\nAll clothes:");
        for (Clothes clothes : clothesList) {
            System.out.println(clothes.getType() + " -> " + clothes);
        }
    }

    private static Pants createPants(Scanner scanner) {
        String name = readNonEmptyString(scanner, "Name: ");
        Size size = readSize(scanner, "Size (XS/S/M/L/XL/XXL): ");
        double price = readPositiveDouble(scanner, "Price: ");
        String material = readNonEmptyString(scanner, "Material: ");
        double waistSize = readPositiveDouble(scanner, "Waist size: ");
        return new Pants(name, size, price, material, waistSize);
    }

    private static Shirts createShirt(Scanner scanner) {
        String name = readNonEmptyString(scanner, "Name: ");
        Size size = readSize(scanner, "Size (XS/S/M/L/XL/XXL): ");
        double price = readPositiveDouble(scanner, "Price: ");
        String material = readNonEmptyString(scanner, "Material: ");
        double sleeveLength = readPositiveDouble(scanner, "Sleeve length: ");
        return new Shirts(name, size, price, material, sleeveLength);
    }

    private static Jacket createJacket(Scanner scanner) {
        String name = readNonEmptyString(scanner, "Name: ");
        Size size = readSize(scanner, "Size (XS/S/M/L/XL/XXL): ");
        double price = readPositiveDouble(scanner, "Price: ");
        String material = readNonEmptyString(scanner, "Material: ");
        int pocketCount = readPocketCount(scanner, "Pocket count: ");
        return new Jacket(name, size, price, material, pocketCount);
    }

    private static Hat createHat(Scanner scanner) {
        String name = readNonEmptyString(scanner, "Name: ");
        Size size = readSize(scanner, "Size (XS/S/M/L/XL/XXL): ");
        double price = readPositiveDouble(scanner, "Price: ");
        String material = readNonEmptyString(scanner, "Material: ");
        double brimWidth = readPositiveDouble(scanner, "Brim width: ");
        return new Hat(name, size, price, material, brimWidth);
    }

    private static String readNonEmptyString(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                System.out.println("Error: input cannot be empty.");
                continue;
            }

            return input;
        }
    }

    private static int readNonNegativeInt(Scanner scanner, String prompt) {
        while (true) {
            String input = readNonEmptyString(scanner, prompt);

            try {
                int value = Integer.parseInt(input);
                if (value < 0) {
                    System.out.println("Error: value cannot be negative.");
                    continue;
                }
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Error: please enter a valid integer.");
            }
        }
    }

    private static double readPositiveDouble(Scanner scanner, String prompt) {
        while (true) {
            String input = readNonEmptyString(scanner, prompt);

            try {
                double value = Double.parseDouble(input);
                if (value <= 0) {
                    System.out.println("Error: value must be greater than 0.");
                    continue;
                }
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Error: please enter a valid number.");
            }
        }
    }

    private static Size readSize(Scanner scanner, String prompt) {
        while (true) {
            String input = readNonEmptyString(scanner, prompt);
            try {
                return Size.fromString(input);
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
    private static int readPocketCount(Scanner scanner, String prompt) {
        while (true) {
            String input = readNonEmptyString(scanner, prompt);

            try {
                int value = Integer.parseInt(input);
                if (value < 0) {
                    System.out.println("Error: pocket count cannot be negative.");
                    continue;
                }
                if (value > 10) {
                    System.out.println("Error: pocket count cannot be greater than 10.");
                    continue;
                }
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Error: please enter a valid integer for pocket count.");
            }
        }
    }
}
