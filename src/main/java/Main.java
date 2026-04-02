import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int n;
        double price = 0.0;  
        
        // input size
        while(true) {
            try {
                System.out.print("Enter the number of clothes: ");
                n = scanner.nextInt();
                if (n <= 0) {
                    System.out.println("Error: number must be => 0");
                    continue;
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println("Error: please enter a valid integer");
                scanner.nextLine(); 
            }
        }

        Clothes[] clothesArray = new Clothes[n];

        // fill array
        scanner.nextLine();
        for (int i = 0; i < n; i++) {
            System.out.println("\nClothes #" + (i + 1));

            String name;
            while (true) {
                System.out.print("Name: ");
                name = scanner.nextLine().trim();
                if (name.isEmpty()) {
                    System.out.println("Error: name cannot be empty");
                    continue;
                }
                break;
            }

            String size;
            while (true) {
                System.out.print("Size: ");
                size = scanner.nextLine().trim();
                if (size.isEmpty()) {
                    System.out.println("Error: size cannot be empty");
                    continue;
                }
                break;
            }

            while (true) {
                try {
                    System.out.print("Price: ");
                    price = scanner.nextDouble();
                    if (price <= 0) {
                        System.out.println("Error: price must be > 0");
                        continue;
                    }
                    scanner.nextLine(); // очищаємо залишок рядка
                    break;
                } catch (InputMismatchException e) {
                    System.out.println("Error: please enter a valid number");
                    scanner.nextLine();
                }
            }

            String color;
            while (true) {
                System.out.print("Color: ");
                color = scanner.nextLine().trim();
                if (color.isEmpty()) {
                    System.out.println("Error: color cannot be empty");
                    continue;
                }
                break;
            }

            String material;
            while (true) {
                System.out.print("Material: ");
                material = scanner.nextLine().trim();
                if (material.isEmpty()) {
                    System.out.println("Error: material cannot be empty");
                    continue;
                }
                break;
            }

            clothesArray[i] = new Clothes(name, size, price, color, material);
        }

        // output
        System.out.println("\nAll clothes:");
        for (Clothes c : clothesArray) {
            System.out.println(c);
        }

        scanner.close();
    }   


}