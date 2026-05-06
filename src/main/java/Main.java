import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

/**
 * Драйвер застосунку з консольним меню для роботи з об'єктами одягу.
 * Дозволяє створювати об'єкти різних типів і переглядати їх у спільній колекції.
 */
public class Main {
    private static final String STORAGE_PATH_PROPERTY = "clothes.storage.path";
    private static final String DEFAULT_STORAGE_PATH = "input.txt";

    /**
     * Запускає консольне меню програми.
     *
     * @param args аргументи командного рядка
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

            try {
                switch (choice) {
                    case 1:
                        searchClothesFromSubmenu(scanner, storeService);
                        break;
                    case 2:
                        createClothesFromSubmenu(scanner, storeService);
                        break;
                    case 3:
                        modifyClothes(scanner, storeService);
                        break;
                    case 4:
                        deleteClothes(scanner, storeService);
                        break;
                    case 5:
                        printAllClothes(storeService);
                        break;
                    case 6:
                        printSortedClothesFromSubmenu(scanner, storeService);
                        break;
                    case 7:
                        running = false;
                        System.out.println("Program finished.");
                        break;
                    default:
                        System.out.println("Error: unknown menu option.");
                }
            } catch (InvalidFieldValueException | ObjectNotFoundException e) {
                System.out.println("Error: " + e.getMessage());
                System.out.println("Returning to main menu.");
            }
        }

        scanner.close();
    }

    private static void printMainMenu() {
        System.out.println("\nMain menu:");
        System.out.println("1. Search object");
        System.out.println("2. Create new object");
        System.out.println("3. Modify object");
        System.out.println("4. Delete object");
        System.out.println("5. Show all clothes");
        System.out.println("6. Sort by");
        System.out.println("7. Exit");
    }

    private static int readMainMenuChoice(Scanner scanner) {
        if (!scanner.hasNextLine()) {
            return 7;
        }
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

    private static void printSortSubmenu() {
        System.out.println("\nChoose sorting criterion:");
        System.out.println("1. Sort by name");
        System.out.println("2. Sort by price");
        System.out.println("3. Sort by size");
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

    private static void modifyClothes(Scanner scanner, StoreService storeService) {
        List<Clothes> clothesList = storeService.getAllClothes();
        if (clothesList.isEmpty()) {
            System.out.println("\nThe clothes list is empty.");
            return;
        }

        Clothes selectedClothes = chooseClothesToModify(scanner, clothesList);
        Clothes updatedClothes = readUpdatedClothes(scanner, selectedClothes);
        boolean updated = storeService.updateClothes(selectedClothes, updatedClothes);

        if (updated) {
            System.out.println("Clothes updated successfully.");
        }
    }

    private static void deleteClothes(Scanner scanner, StoreService storeService) {
        List<Clothes> clothesList = storeService.getAllClothes();
        if (clothesList.isEmpty()) {
            System.out.println("\nThe clothes list is empty.");
            return;
        }

        Clothes selectedClothes = chooseClothesToDelete(scanner, clothesList);
        boolean deleted = storeService.deleteClothes(selectedClothes);
        if (deleted) {
            System.out.println("Clothes deleted successfully.");
        }
    }

    private static Clothes chooseClothesToModify(Scanner scanner, List<Clothes> clothesList) {
        System.out.println("\nChoose clothes to modify:");
        for (int index = 0; index < clothesList.size(); index++) {
            System.out.println((index + 1) + ". " + clothesList.get(index));
        }

        while (true) {
            int selectedIndex = readNonNegativeInt(scanner, "Enter object number: ");
            if (selectedIndex < 1 || selectedIndex > clothesList.size()) {
                System.out.println("Error: object number is out of range.");
                continue;
            }

            return clothesList.get(selectedIndex - 1);
        }
    }

    private static Clothes chooseClothesToDelete(Scanner scanner, List<Clothes> clothesList) {
        System.out.println("\nChoose clothes to delete:");
        for (int index = 0; index < clothesList.size(); index++) {
            System.out.println((index + 1) + ". " + clothesList.get(index));
        }

        while (true) {
            int selectedIndex = readNonNegativeInt(scanner, "Enter object number: ");
            if (selectedIndex < 1 || selectedIndex > clothesList.size()) {
                System.out.println("Error: object number is out of range.");
                continue;
            }

            return clothesList.get(selectedIndex - 1);
        }
    }

    private static Clothes readUpdatedClothes(Scanner scanner, Clothes clothes) {
        printModifySubmenu(clothes);

        while (true) {
            int attributeChoice = readNonNegativeInt(scanner, "Choose attribute to modify: ");
            Clothes updatedClothes = tryBuildUpdatedClothes(scanner, clothes, attributeChoice);

            if (updatedClothes != null) {
                return updatedClothes;
            }

            System.out.println("Error: unknown menu option.");
        }
    }

    private static void printModifySubmenu(Clothes clothes) {
        System.out.println("\nChoose attribute to modify:");
        System.out.println("1. Name");
        System.out.println("2. Size");
        System.out.println("3. Price");
        System.out.println("4. Material");

        if (clothes instanceof Pants) {
            System.out.println("5. Waist size");
        } else if (clothes instanceof Shirts) {
            System.out.println("5. Sleeve length");
        } else if (clothes instanceof Jacket) {
            System.out.println("5. Pocket count");
        } else if (clothes instanceof Hat) {
            System.out.println("5. Brim width");
        }
    }

    private static Clothes tryBuildUpdatedClothes(Scanner scanner, Clothes clothes, int attributeChoice) {
        switch (attributeChoice) {
            case 1:
                return copyClothesWithUpdatedBaseFields(
                        clothes,
                        readNonEmptyString(scanner, "New name: "),
                        clothes.getSize(),
                        clothes.getPrice(),
                        clothes.getMaterial()
                );
            case 2:
                return copyClothesWithUpdatedBaseFields(
                        clothes,
                        clothes.getName(),
                        readSize(scanner, "New size (XS/S/M/L/XL/XXL): "),
                        clothes.getPrice(),
                        clothes.getMaterial()
                );
            case 3:
                return copyClothesWithUpdatedBaseFields(
                        clothes,
                        clothes.getName(),
                        clothes.getSize(),
                        readPositiveDouble(scanner, "New price: "),
                        clothes.getMaterial()
                );
            case 4:
                return copyClothesWithUpdatedBaseFields(
                        clothes,
                        clothes.getName(),
                        clothes.getSize(),
                        clothes.getPrice(),
                        readNonEmptyString(scanner, "New material: ")
                );
            case 5:
                return copyClothesWithUpdatedSpecificField(scanner, clothes);
            default:
                return null;
        }
    }

    private static Clothes copyClothesWithUpdatedBaseFields(
            Clothes clothes,
            String name,
            Size size,
            double price,
            String material
    ) {
        if (clothes instanceof Pants pants) {
            return new Pants(name, size, price, material, pants.getWaistSize());
        }
        if (clothes instanceof Shirts shirts) {
            return new Shirts(name, size, price, material, shirts.getSleeveLength());
        }
        if (clothes instanceof Jacket jacket) {
            return new Jacket(name, size, price, material, jacket.getPocketCount());
        }
        if (clothes instanceof Hat hat) {
            return new Hat(name, size, price, material, hat.getBrimWidth());
        }

        throw new IllegalArgumentException("Unsupported clothes type: " + clothes.getClass().getSimpleName());
    }

    private static Clothes copyClothesWithUpdatedSpecificField(Scanner scanner, Clothes clothes) {
        if (clothes instanceof Pants) {
            return new Pants(
                    clothes.getName(),
                    clothes.getSize(),
                    clothes.getPrice(),
                    clothes.getMaterial(),
                    readPositiveDouble(scanner, "New waist size: ")
            );
        }
        if (clothes instanceof Shirts) {
            return new Shirts(
                    clothes.getName(),
                    clothes.getSize(),
                    clothes.getPrice(),
                    clothes.getMaterial(),
                    readPositiveDouble(scanner, "New sleeve length: ")
            );
        }
        if (clothes instanceof Jacket) {
            return new Jacket(
                    clothes.getName(),
                    clothes.getSize(),
                    clothes.getPrice(),
                    clothes.getMaterial(),
                    readPocketCount(scanner, "New pocket count: ")
            );
        }
        if (clothes instanceof Hat) {
            return new Hat(
                    clothes.getName(),
                    clothes.getSize(),
                    clothes.getPrice(),
                    clothes.getMaterial(),
                    readPositiveDouble(scanner, "New brim width: ")
            );
        }

        throw new IllegalArgumentException("Unsupported clothes type: " + clothes.getClass().getSimpleName());
    }

    private static void printSortedClothesFromSubmenu(Scanner scanner, StoreService storeService) {
        boolean inSubmenu = true;

        while (inSubmenu) {
            printSortSubmenu();
            int choice = readNonNegativeInt(scanner, "Choose sorting criterion: ");

            switch (choice) {
                case 1:
                    printSortedClothes(
                            storeService,
                            (first, second) -> first.getName().compareTo(second.getName()),
                            "name"
                    );
                    inSubmenu = false;
                    break;
                case 2:
                    printSortedClothes(
                            storeService,
                            (first, second) -> Double.compare(first.getPrice(), second.getPrice()),
                            "price"
                    );
                    inSubmenu = false;
                    break;
                case 3:
                    printSortedClothes(
                            storeService,
                            (first, second) -> first.getSize().compareTo(second.getSize()),
                            "size"
                    );
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

    private static void printSortedClothes(StoreService storeService, Comparator<Clothes> comparator, String criterionName) {
        List<Clothes> clothesList = new ArrayList<>(storeService.getAllClothes());
        if (clothesList.isEmpty()) {
            System.out.println("\nThe clothes list is empty.");
            return;
        }

        Collections.sort(clothesList, comparator);

        System.out.println("\nAll clothes sorted by " + criterionName + ":");
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
