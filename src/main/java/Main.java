import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int n;

        // input size
        do {
            System.out.print("Enter number of clothes: ");
            n = scanner.nextInt();

            if (n <= 0) {
                System.out.println("Error: must be > 0");
            }
        } while (n <= 0);

        Clothes[] clothesArray = new Clothes[n];

        // fill array
        for (int i = 0; i < n; i++) {
            System.out.println("\nClothes #" + (i + 1));

            System.out.print("Name: ");
            String name = scanner.next();

            System.out.print("Size: ");
            String size = scanner.next();

            System.out.print("Price: ");
            double price = scanner.nextDouble();

            System.out.print("Color: ");
            String color = scanner.next();

            clothesArray[i] = new Clothes(name, size, price, color);
        }

        // output
        System.out.println("\nAll clothes:");
        for (Clothes c : clothesArray) {
            System.out.println(c);
        }

        scanner.close();
    }
}