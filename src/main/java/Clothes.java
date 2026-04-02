public class Clothes {
    private String name;
    private String size;
    private double price;
    private String color;
    private String material;

    // Constructor
        public Clothes(String name, String size, double price, String color, String material) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        if (size == null || size.isEmpty()) {
            throw new IllegalArgumentException("Size cannot be null or empty");
        }
        if (price <= 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
        if (color == null || color.isEmpty()) {
            throw new IllegalArgumentException("Color cannot be null or empty");
        }
        if (material == null || material.isEmpty()) {
            throw new IllegalArgumentException("Material cannot be null or empty");
        }
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
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        this.name = name;
    }

    public void setSize(String size) {
        if (size == null || size.isEmpty()) {
            throw new IllegalArgumentException("Size cannot be null or empty");
        }
        this.size = size;
    }

    public void setPrice(double price) {
        if (price <= 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
        this.price = price;
    }

    public void setColor(String color) {
        if (color == null || color.isEmpty()) {
            throw new IllegalArgumentException("Color cannot be null or empty");
        }
        this.color = color;
    }

    public void setMaterial(String material) {
        if (material == null || material.isEmpty()) {
            throw new IllegalArgumentException("Material cannot be null or empty");
        }
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