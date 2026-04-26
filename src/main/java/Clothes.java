public abstract class Clothes {
    private String name;
    private Size size;
    private double price;
    private String material;
    private Manufacturer manufacturer;

    public Clothes(String name, Size size, double price, String material) {
        setName(name);
        setSize(size);
        setPrice(price);
        setMaterial(material);
    }

    public Clothes(String name, Size size, double price, String material, Manufacturer manufacturer) {
        this(name, size, price, material);
        setManufacturer(manufacturer);
    }

    public String getName() {
        return name;
    }

    public Size getSize() {
        return size;
    }

    public double getPrice() {
        return price;
    }

    public String getMaterial() {
        return material;
    }

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public void setName(String name) {
        this.name = validateTextField(name, "Name");
    }

    public void setSize(Size size) {
        if (size == null) {
            throw new IllegalArgumentException("Size cannot be null.");
        }
        this.size = size;
    }

    public void setPrice(double price) {
        if (price <= 0) {
            throw new IllegalArgumentException("Price must be greater than 0.");
        }
        this.price = price;
    }

    public void setMaterial(String material) {
        this.material = validateTextField(material, "Material");
    }

    public void setManufacturer(Manufacturer manufacturer) {
        if (manufacturer == null) {
            throw new IllegalArgumentException("Manufacturer cannot be null.");
        }
        this.manufacturer = manufacturer;
    }

    public abstract String getType();

    @Override
    public String toString() {
        String base = getType() + ": " +
                "name='" + name + '\'' +
                ", size=" + size +
                ", price=" + price +
                ", material='" + material + '\'';

        if (manufacturer == null) {
            return base;
        }

        return base + ", " + manufacturer;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Clothes other = (Clothes) obj;

        return name.equals(other.name) &&
                size == other.size &&
                price == other.price &&
                material.equals(other.material);
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
