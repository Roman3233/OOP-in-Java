/**
 * Представляє штани з додатковою властивістю обхвату талії.
 */
import java.util.Objects;

public class Pants extends Clothes {
    private double waistSize;

    /**
     * Створює об'єкт штанів.
     *
     * @param name назва виробу
     * @param size розмір виробу
     * @param price ціна виробу
     * @param material матеріал виробу
     * @param waistSize обхват талії
     * @throws IllegalArgumentException якщо {@code name} або {@code material} є {@code null} / порожніми,
     *                                  якщо {@code size} є {@code null}, якщо {@code price <= 0},
     *                                  якщо {@code waistSize <= 0}
     */
    public Pants(String name, Size size, double price, String material, double waistSize) {
        super(name, size, price, material);
        setWaistSize(waistSize);
    }

    /**
     * Повертає обхват талії.
     *
     * @return обхват талії
     */
    public double getWaistSize() {
        return waistSize;
    }

    /**
     * Встановлює значення обхвату талії.
     *
     * @param waistSize значення обхвату талії
     * @throws IllegalArgumentException якщо {@code waistSize <= 0}
     */
    public void setWaistSize(double waistSize) {
        if (waistSize <= 0) {
            throw new InvalidFieldValueException("Waist size must be greater than 0.");
        }
        this.waistSize = waistSize;
    }

    @Override
    public String getType() {
        return "Pants";
    }

    @Override
    protected int compareSpecificFields(Clothes other) {
        Pants otherPants = (Pants) other;
        return Double.compare(waistSize, otherPants.waistSize);
    }

    @Override
    public String toString() {
        return super.toString() + ", waistSize=" + waistSize;
    }

    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) {
            return false;
        }

        Pants other = (Pants) obj;
        return Double.compare(waistSize, other.waistSize) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), waistSize);
    }
}
