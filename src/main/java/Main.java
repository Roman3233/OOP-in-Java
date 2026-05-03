import java.util.List;
import java.util.Scanner;

/**
 * Р”СЂР°Р№РІРµСЂ Р·Р°СЃС‚РѕСЃСѓРЅРєСѓ Р· РєРѕРЅСЃРѕР»СЊРЅРёРј РјРµРЅСЋ РґР»СЏ СЂРѕР±РѕС‚Рё Р· РѕР±'С”РєС‚Р°РјРё РѕРґСЏРіСѓ.
 * Р”РѕР·РІРѕР»СЏС” СЃС‚РІРѕСЂСЋРІР°С‚Рё РѕР±'С”РєС‚Рё СЂС–Р·РЅРёС… С‚РёРїС–РІ С– РїРµСЂРµРіР»СЏРґР°С‚Рё С—С… Сѓ СЃРїС–Р»СЊРЅС–Р№ РєРѕР»РµРєС†С–С—.
 */
public class Main {
    private static final String STORAGE_PATH_PROPERTY = "clothes.storage.path";
    private static final String DEFAULT_STORAGE_PATH = "input.txt";

    /**
     * Р—Р°РїСѓСЃРєР°С” РєРѕРЅСЃРѕР»СЊРЅРµ РјРµРЅСЋ РїСЂРѕРіСЂР°РјРё.
     *
     * @param args Р°СЂРіСѓРјРµРЅС‚Рё РєРѕРјР°РЅРґРЅРѕРіРѕ СЂСЏРґРєР°
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ClothesFileStorage storage = new ClothesFileStorage(resolveStoragePath());
        StoreService storeService = new StoreService(new Store("Clothes store"), storage);
        storeService.loadFromStorage();

        boolean running = true;
        while (running) {
            printMainMenu();
            int choice = readMainMenuChoice(scanner);

            switch (choice) {
                case 1:
                    searchClothesFromSubmenu(scanner, storeService);
                    break;
                case 2:
                    createClothesFromSubmenu(scanner, storeService);
                    break;
                case 3:
                    printAllClothes(storeService);
                    break;
                case 4:
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
        System.out.println("1. Search object");
        System.out.println("2. Create new object");
        System.out.println("3. Show all clothes");
        System.out.println("4. Exit");
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

    private static void printSearchSubmenu() {
        System.out.println("\nSearch by:");
        System.out.println("1. Name");
        System.out.println("2. Size");
        System.out.println("3. Material");
        System.out.println("4. Maximum price");
        System.out.println("0. Return to main menu");
    }

    private static void searchClothesFromSubmenu(Scanner scanner, StoreService storeService) {
        boolean inSubmenu = true;

        while (inSubmenu) {
            printSearchSubmenu();
            int choice = readNonNegativeInt(scanner, "Choose search criterion: ");

            switch (choice) {
                case 1:
                    searchByName(scanner, storeService);
                    break;
                case 2:
                    searchBySize(scanner, storeService);
                    break;
                case 3:
                    searchByMaterial(scanner, storeService);
                    break;
                case 4:
                    searchByMaximumPrice(scanner, storeService);
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

    private static void createClothesFromSubmenu(Scanner scanner, StoreService storeService) {
        boolean inSubmenu = true;

        while (inSubmenu) {
            printCreateSubmenu();
            int choice = readNonNegativeInt(scanner, "Choose clothes type: ");

            switch (choice) {
                case 1:
                    System.out.println("\nCreating pants:");
                    storeService.addClothes(createPants(scanner));
                    System.out.println("Pants added successfully.");
                    inSubmenu = false;
                    break;
                case 2:
                    System.out.println("\nCreating shirt:");
                    storeService.addClothes(createShirt(scanner));
                    System.out.println("Shirt added successfully.");
                    inSubmenu = false;
                    break;
                case 3:
                    System.out.println("\nCreating jacket:");
                    storeService.addClothes(createJacket(scanner));
                    System.out.println("Jacket added successfully.");
                    inSubmenu = false;
                    break;
                case 4:
                    System.out.println("\nCreating hat:");
                    storeService.addClothes(createHat(scanner));
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

    private static void printAllClothes(StoreService storeService) {
        List<Clothes> clothesList = storeService.getAllClothes();
        if (clothesList.isEmpty()) {
            System.out.println("\nThe clothes list is empty.");
            return;
        }

        System.out.println("\nAll clothes:");
        for (Clothes clothes : clothesList) {
            System.out.println(clothes);
        }
    }

    private static void searchByName(Scanner scanner, StoreService storeService) {
        String query = readNonEmptyString(scanner, "Enter name to search: ");
        List<Clothes> foundClothes = storeService.findClothesByName(query);
        printSearchResults(foundClothes, "name");
    }

    private static void searchBySize(Scanner scanner, StoreService storeService) {
        Size size = readSize(scanner, "Enter size (XS/S/M/L/XL/XXL): ");
        List<Clothes> foundClothes = storeService.findClothesBySize(size);
        printSearchResults(foundClothes, "size");
    }

    private static void searchByMaterial(Scanner scanner, StoreService storeService) {
        String material = readNonEmptyString(scanner, "Enter material to search: ");
        List<Clothes> foundClothes = storeService.findClothesByMaterial(material);
        printSearchResults(foundClothes, "material");
    }

    private static void searchByMaximumPrice(Scanner scanner, StoreService storeService) {
        double maximumPrice = readPositiveDouble(scanner, "Enter maximum price: ");
        List<Clothes> foundClothes = storeService.findClothesByMaximumPrice(maximumPrice);
        printSearchResults(foundClothes, "maximum price");
    }

    private static void printSearchResults(List<Clothes> foundClothes, String criterionName) {
        if (foundClothes.isEmpty()) {
            System.out.println("\nNo clothes found for the selected " + criterionName + ".");
            return;
        }

        System.out.println("\nSearch results:");
        for (Clothes clothes : foundClothes) {
            System.out.println(clothes);
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

    private static String resolveStoragePath() {
        return System.getProperty(STORAGE_PATH_PROPERTY, DEFAULT_STORAGE_PATH);
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
