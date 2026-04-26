import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int pantsCount = readNonNegativeInt(scanner, "Enter the number of pants: ");
        Pants[] pantsArray = new Pants[pantsCount];
        for (int i = 0; i < pantsCount; i++) {
            System.out.println("\nPants #" + (i + 1));
            pantsArray[i] = createPants(scanner);
        }

        int shirtsCount = readNonNegativeInt(scanner, "Enter the number of shirts: ");
        Shirts[] shirtsArray = new Shirts[shirtsCount];
        for (int i = 0; i < shirtsCount; i++) {
            System.out.println("\nShirt #" + (i + 1));
            shirtsArray[i] = createShirt(scanner);
        }

        Clothes[] allClothes = new Clothes[pantsArray.length + shirtsArray.length];
        int index = 0;

        for (Pants pants : pantsArray) {
            allClothes[index++] = pants;
        }

        for (Shirts shirt : shirtsArray) {
            allClothes[index++] = shirt;
        }

        System.out.println("\nAll clothes (polymorphism demonstration):");
        for (Clothes clothes : allClothes) {
            System.out.println(clothes.getType() + " -> " + clothes);
        }

        scanner.close();
    }

    private static Pants createPants(Scanner scanner) {
        String name = readNonEmptyString(scanner, "Name: ");
        Size size = readSize(scanner, "Size (XS/S/M/L/XL/XXL): ");
        double price = readPositiveDouble(scanner, "Price: ");
        String color = readNonEmptyString(scanner, "Color: ");
        String material = readNonEmptyString(scanner, "Material: ");
        double waistSize = readPositiveDouble(scanner, "Waist size: ");
        Manufacturer manufacturer = readManufacturer(scanner);

        return new Pants(name, size, price, color, material, manufacturer, waistSize);
    }

    private static Shirts createShirt(Scanner scanner) {
        String name = readNonEmptyString(scanner, "Name: ");
        Size size = readSize(scanner, "Size (XS/S/M/L/XL/XXL): ");
        double price = readPositiveDouble(scanner, "Price: ");
        String color = readNonEmptyString(scanner, "Color: ");
        String material = readNonEmptyString(scanner, "Material: ");
        double sleeveLength = readPositiveDouble(scanner, "Sleeve length: ");
        Manufacturer manufacturer = readManufacturer(scanner);

        return new Shirts(name, size, price, color, material, manufacturer, sleeveLength);
    }

    private static Manufacturer readManufacturer(Scanner scanner) {
        String manufacturerName = readNonEmptyString(scanner, "Manufacturer name: ");
        String manufacturerCountry = readNonEmptyString(scanner, "Manufacturer country: ");
        int foundedYear = readYear(scanner, "Manufacturer founded year: ");
        return new Manufacturer(manufacturerName, manufacturerCountry, foundedYear);
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

    private static int readPositiveInt(Scanner scanner, String prompt) {
        while (true) {
            int value = readNonNegativeInt(scanner, prompt);
            if (value == 0) {
                System.out.println("Error: value must be greater than 0.");
                continue;
            }
            return value;
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
