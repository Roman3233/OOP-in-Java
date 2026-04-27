/**
 * Представляє куртку з додатковою властивістю кількості кишень.
 */
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
     */
    public Jacket(String name, Size size, double price, String material, int pocketCount) {
        super(name, size, price, material);
        setPocketCount(pocketCount);
    }

    public int getPocketCount() {
        return pocketCount;
    }

    /**
     * Встановлює кількість кишень куртки.
     *
     * @param pocketCount кількість кишень
     * @throws IllegalArgumentException якщо {@code pocketCount < 0}
     */
    public void setPocketCount(int pocketCount) {
        if (pocketCount < 0) {
            throw new IllegalArgumentException("Pocket count cannot be negative.");
        }
        this.pocketCount = pocketCount;
    }

    /**
     * Повертає {@code true}, якщо куртка має щонайменше дві кишені.
     *
     * @return {@code true}, якщо кількість кишень не менша за 2
     */
    public boolean hasPracticalPockets() {
        return pocketCount >= 2;
    }

    @Override
    public String getType() {
        return "Jacket";
    }

    @Override
    public String toString() {
        return super.toString() + ", pocketCount=" + pocketCount;
    }
}
