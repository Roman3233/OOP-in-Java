public class Clothes {
    private String name;
    private String size;
    private double price;
    private String color;
    private String material;
    private static int count = 0;

    public Clothes(String name, String size, double price, String color, String material) {
        setName(name);
        setSize(size);
        setPrice(price);
        setColor(color);
        setMaterial(material);
        count++;
    }

    public Clothes(Clothes obj) {
        this.name = obj.name;
        this.size = obj.size;
        this.price = obj.price;
        this.color = obj.color;
        this.material = obj.material;
        count++;
    }

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

    public static int getCount() {
        return count;
    }

    public void setName(String name) {
        this.name = validateTextField(name, "Name");
    }

    public void setSize(String size) {
        this.size = validateTextField(size, "Size");
    }

    public void setPrice(double price) {
        if (price <= 0) {
            throw new IllegalArgumentException("Price must be greater than 0.");
        }
        this.price = price;
    }

    public void setColor(String color) {
        this.color = validateTextField(color, "Color");
    }

    public void setMaterial(String material) {
        this.material = validateTextField(material, "Material");
    }
    

    @Override
    public String toString() {
        return "Clothes: " +
                "name='" + name + '\'' +
                ", size='" + size + '\'' +
                ", price=" + price +
                ", color='" + color + '\'' +
                ", material='" + material + '\'';
    }

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

    private String validateTextField(String value, String fieldName) {
        if (value == null) {
            throw new IllegalArgumentException(fieldName + " cannot be null.");
        }

        String trimmedValue = value.trim();
        if (trimmedValue.isEmpty()) {
            throw new IllegalArgumentException(fieldName + " cannot be empty.");
        }

        return trimmedValue;
    }
}