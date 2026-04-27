/**
 * Представляє штани з додатковою властивістю обхвату талії.
 */
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
            throw new IllegalArgumentException("Waist size must be greater than 0.");
        }
        this.waistSize = waistSize;
    }

    @Override
    public String getType() {
        return "Pants";
    }

    @Override
    public String toString() {
        return super.toString() + ", waistSize=" + waistSize;
    }
}
