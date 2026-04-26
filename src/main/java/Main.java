import java.util.Scanner;

/**
 * Консольна точка входу: зчитує дані про одяг з {@link System#in} та виводить створені об'єкти.
 */
public class Main {
    /**
     * Точка входу програми.
     *
     * @param args аргументи командного рядка (не використовуються)
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int n = readPositiveInt(scanner, "Enter the number of clothes: ");
        Clothes[] clothesArray = new Clothes[n];

        for (int i = 0; i < n; i++) {
            System.out.println("\nClothes #" + (i + 1));

            String name = readNonEmptyString(scanner, "Name: ");
            Size size = readSize(scanner, "Size (XS/S/M/L/XL/XXL): ");
            double price = readPositiveDouble(scanner, "Price: ");
            String color = readNonEmptyString(scanner, "Color: ");
            String material = readNonEmptyString(scanner, "Material: ");

            Clothes clothes = new Clothes(name, size, price, color, material);

            System.out.println("Manufacturer for clothes #" + (i + 1));
            String manufacturerName = readNonEmptyString(scanner, "Manufacturer name: ");
            String manufacturerCountry = readNonEmptyString(scanner, "Manufacturer country: ");
            int foundedYear = readYear(scanner, "Manufacturer founded year: ");

            clothes.setManufacturer(new Manufacturer(manufacturerName, manufacturerCountry, foundedYear));
            clothesArray[i] = clothes;
        }

        System.out.println("\nAll clothes (with manufacturers):");
        for (Clothes clothes : clothesArray) {
            System.out.println(clothes);
        }

        System.out.println("\nTotal clothes created: " + Clothes.getCount());

        scanner.close();
    }

    /**
     * Зчитує непорожній рядок з консолі.
     *
     * @param scanner сканер вводу
     * @param prompt  запрошення (prompt) перед зчитуванням
     * @return непорожній рядок без пробілів на краях (trim)
     */
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

    /**
     * Зчитує додатне ціле число з консолі.
     *
     * @param scanner сканер вводу
     * @param prompt  запрошення (prompt) перед зчитуванням
     * @return ціле число &gt; 0
     */
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

    /**
     * Зчитує додатне число з плаваючою комою з консолі.
     *
     * @param scanner сканер вводу
     * @param prompt  запрошення (prompt) перед зчитуванням
     * @return число &gt; 0
     */
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

    /**
     * Зчитує рік, який не може бути в майбутньому.
     *
     * @param scanner сканер вводу
     * @param prompt  запрошення (prompt) перед зчитуванням
     * @return рік у межах 1..поточний рік (включно)
     */
    private static int readYear(Scanner scanner, String prompt) {
        int currentYear = java.time.Year.now().getValue();
        while (true) {
            int year = readPositiveInt(scanner, prompt);
            if (year > currentYear) {
                System.out.println("Error: year cannot be in the future.");
                continue;
            }
            return year;
        }
    }

    /**
     * Зчитує значення {@link Size} з консолі.
     *
     * @param scanner сканер вводу
     * @param prompt  запрошення (prompt) перед зчитуванням
     * @return зчитаний {@link Size}
     */
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
}
