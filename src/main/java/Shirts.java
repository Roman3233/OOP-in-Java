/**
 * Представляє сорочку з додатковою властивістю довжини рукава.
 */
import java.util.Objects;

public class Shirts extends Clothes {
    private double sleeveLength;

    /**
     * Створює об'єкт сорочки.
     *
     * @param name назва виробу
     * @param size розмір виробу
     * @param price ціна виробу
     * @param material матеріал виробу
     * @param sleeveLength довжина рукава
     * @throws IllegalArgumentException якщо {@code name} або {@code material} є {@code null} / порожніми,
     *                                  якщо {@code size} є {@code null}, якщо {@code price <= 0},
     *                                  якщо {@code sleeveLength <= 0}
     */
    public Shirts(String name, Size size, double price, String material, double sleeveLength) {
        super(name, size, price, material);
        setSleeveLength(sleeveLength);
    }

    /**
     * Повертає довжину рукава.
     *
     * @return довжина рукава
     */
    public double getSleeveLength() {
        return sleeveLength;
    }

    /**
     * Встановлює значення довжини рукава.
     *
     * @param sleeveLength значення довжини рукава
     * @throws IllegalArgumentException якщо {@code sleeveLength <= 0}
     */
    public void setSleeveLength(double sleeveLength) {
        if (sleeveLength <= 0) {
            throw new InvalidFieldValueException("Sleeve length must be greater than 0.");
        }
        this.sleeveLength = sleeveLength;
    }

    @Override
    public String getType() {
        return "Shirt";
    }

    @Override
    protected int compareSpecificFields(Clothes other) {
        Shirts otherShirt = (Shirts) other;
        return Double.compare(sleeveLength, otherShirt.sleeveLength);
    }

    @Override
    public String toString() {
        return super.toString() + ", sleeveLength=" + sleeveLength;
    }

    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) {
            return false;
        }

        Shirts other = (Shirts) obj;
        return Double.compare(sleeveLength, other.sleeveLength) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), sleeveLength);
    }
}
