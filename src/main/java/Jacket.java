/**
 * Представляє куртку з додатковою властивістю кількості кишень.
 */
import java.util.Objects;

public class Jacket extends Clothes {
    private int pocketCount;

    /**
     * Створює об'єкт куртки.
     *
     * @param name назва виробу
     * @param size розмір виробу
     * @param price ціна виробу
     * @param material матеріал виробу
     * @param pocketCount кількість кишень
     * @throws IllegalArgumentException якщо {@code name} або {@code material} є {@code null} / порожніми,
     *                                  якщо {@code size} є {@code null}, якщо {@code price <= 0},
     *                                  якщо {@code pocketCount < 0} або {@code pocketCount > 10}
     */
    public Jacket(String name, Size size, double price, String material, int pocketCount) {
        super(name, size, price, material);
        setPocketCount(pocketCount);
    }

    /**
     * Повертає кількість кишень.
     *
     * @return кількість кишень
     */
    public int getPocketCount() {
        return pocketCount;
    }

    /**
     * Встановлює кількість кишень куртки.
     *
     * @param pocketCount кількість кишень
     * @throws IllegalArgumentException якщо {@code pocketCount < 0} або {@code pocketCount > 10}
     */
    public void setPocketCount(int pocketCount) {
        if (pocketCount < 0) {
            throw new InvalidFieldValueException("Pocket count cannot be negative.");
        }
        if (pocketCount > 10) {
            throw new InvalidFieldValueException("Pocket count cannot be greater than 10.");
        }
        this.pocketCount = pocketCount;
    }

    @Override
    public String getType() {
        return "Jacket";
    }

    @Override
    protected int compareSpecificFields(Clothes other) {
        Jacket otherJacket = (Jacket) other;
        return Integer.compare(pocketCount, otherJacket.pocketCount);
    }

    @Override
    public String toString() {
        return super.toString() + ", pocketCount=" + pocketCount;
    }

    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) {
            return false;
        }

        Jacket other = (Jacket) obj;
        return pocketCount == other.pocketCount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), pocketCount);
    }
}
