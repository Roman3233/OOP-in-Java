public class Clothes {
    private String name;
    private String size;
    private double price;
    private String color;
    private String material;

    // Constructor
    public Clothes(String name, String size, double price, String color, String material) {
        this.name = name;
        this.size = size;
        this.price = price;
        this.color = color;
        this.material = material;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getSize() {
        return size;
    }

    public String getMaterial() {
        return material;
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

    public void setMaterial(String material) {
        this.material = material;
    }

    // toString
    @Override
    public String toString() {
        return "Clothes: " +
                "name='" + name + '\'' +
                ", size='" + size + '\'' +
                ", price=" + price +
                ", color='" + color + '\'' +
                ", material='" + material + '\'';
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