import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int n = readPositiveInt(scanner, "Enter the number of clothes: ");
        Clothes[] clothesArray = new Clothes[n];

        for (int i = 0; i < n; i++) {
            System.out.println("\nClothes #" + (i + 1));

            String name = readNonEmptyString(scanner, "Name: ");
            String size = readNonEmptyString(scanner, "Size: ");
            double price = readPositiveDouble(scanner, "Price: ");
            String color = readNonEmptyString(scanner, "Color: ");
            String material = readNonEmptyString(scanner, "Material: ");

            clothesArray[i] = new Clothes(name, size, price, color, material);
        }

        System.out.println("\nAll clothes:");
        for (Clothes clothes : clothesArray) {
            System.out.println(clothes);
        }

        scanner.close();
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

    private static int readPositiveInt(Scanner scanner, String prompt) {
        while (true) {
            String input = readNonEmptyString(scanner, prompt);

            try {
                int value = Integer.parseInt(input);
                if (value <= 0) {
                    System.out.println("Error: value must be greater than 0.");
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
}
