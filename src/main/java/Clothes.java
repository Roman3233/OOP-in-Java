/**
 * Базовий абстрактний клас для всіх видів одягу в застосунку.
 * Містить спільні властивості, які успадковують похідні класи.
 */
import java.util.Objects;

public abstract class Clothes {
    private String name;
    private Size size;
    private double price;
    private String material;

    /**
     * Створює об'єкт одягу.
     *
     * @param name назва виробу
     * @param size розмір виробу
     * @param price ціна виробу
     * @param material матеріал виробу
     * @throws IllegalArgumentException якщо {@code name} або {@code material} є {@code null} / порожніми,
     *                                  якщо {@code size} є {@code null} або якщо {@code price <= 0}
     */
    public Clothes(String name, Size size, double price, String material) {
        setName(name);
        setSize(size);
        setPrice(price);
        setMaterial(material);
    }

    /**
     * Повертає назву виробу.
     *
     * @return назва виробу
     */
    public String getName() {
        return name;
    }

    /**
     * Повертає розмір виробу.
     *
     * @return розмір виробу
     */
    public Size getSize() {
        return size;
    }

    /**
     * Повертає ціну виробу.
     *
     * @return ціна виробу
     */
    public double getPrice() {
        return price;
    }

    /**
     * Повертає матеріал виробу.
     *
     * @return матеріал виробу
     */
    public String getMaterial() {
        return material;
    }

    /**
     * Встановлює назву виробу.
     *
     * @param name назва виробу
     * @throws IllegalArgumentException якщо {@code name} є {@code null} або порожнім
     */
    public void setName(String name) {
        this.name = validateTextField(name, "Name");
    }

    /**
     * Встановлює розмір виробу.
     *
     * @param size розмір виробу
     * @throws IllegalArgumentException якщо {@code size} є {@code null}
     */
    public void setSize(Size size) {
        if (size == null) {
            throw new IllegalArgumentException("Size cannot be null.");
        }
        this.size = size;
    }

    /**
     * Встановлює ціну виробу.
     *
     * @param price ціна виробу
     * @throws IllegalArgumentException якщо {@code price <= 0}
     */
    public void setPrice(double price) {
        if (price <= 0) {
            throw new IllegalArgumentException("Price must be greater than 0.");
        }
        this.price = price;
    }

    /**
     * Встановлює матеріал виробу.
     *
     * @param material матеріал виробу
     * @throws IllegalArgumentException якщо {@code material} є {@code null} або порожнім
     */
    public void setMaterial(String material) {
        this.material = validateTextField(material, "Material");
    }

    /**
     * Повертає логічну назву типу для об'єкта похідного класу.
     *
     * @return назва типу одягу
     */
    public abstract String getType();

    @Override
    public String toString() {

        String base =
                "name='" + name + '\'' +
                ", size=" + size +
                ", price=" + price +
                ", material='" + material + '\'';
        return getType() + ": " + base;
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

    @Override
    public int hashCode() {
        return Objects.hash(getClass(), name, size, price, material);
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
