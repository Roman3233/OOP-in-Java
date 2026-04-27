/**
 * Представляє сорочки з додатковою властивістю довжини рукава.
 */
public class Shirts extends Clothes {
    private double sleeveLength;

    /**
     * Створює об'єкт сорочки без інформації про виробника.
     *
     * @param name назва виробу
     * @param size розмір виробу
     * @param price ціна виробу
     * @param material матеріал виробу
     * @param sleeveLength довжина рукава
     */
    public Shirts(String name, Size size, double price, String material, double sleeveLength) {
        super(name, size, price, material);
        setSleeveLength(sleeveLength);
    }

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
            throw new IllegalArgumentException("Sleeve length must be greater than 0.");
        }
        this.sleeveLength = sleeveLength;
    }

    @Override
    public String getType() {
        return "Shirt";
    }

    @Override
    public String toString() {
        return super.toString() + ", sleeveLength=" + sleeveLength;
    }
}
