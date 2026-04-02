public class Clothes {
    private String name;
    private String size;
    private double price;
    private String color;

    // Constructor
    public Clothes(String name, String size, double price, String color) {
        this.name = name;
        this.size = size;
        this.price = price;
        this.color = color;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getSize() {
        return size;
    }

    public double getPrice() {
        return price;
    }

    public String getColor() {
        return color;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setColor(String color) {
        this.color = color;
    }

    // toString
    @Override
    public String toString() {
        return "Clothes: " +
                "name='" + name + '\'' +
                ", size='" + size + '\'' +
                ", price=" + price +
                ", color='" + color + '\'';
    }

    // equals
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Clothes other = (Clothes) obj;

        return name.equals(other.name) &&
               size.equals(other.size) &&
               price == other.price &&
               color.equals(other.color);
    }
}