/**
 * Представляє головний убір з додатковою властивістю ширини полів.
 */
public class Hat extends Clothes {
    private double brimWidth;

    /**
     * Створює об'єкт головного убору.
     *
     * @param name назва виробу
     * @param size розмір виробу
     * @param price ціна виробу
     * @param material матеріал виробу
     * @param brimWidth ширина полів
     */
    public Hat(String name, Size size, double price, String material, double brimWidth) {
        super(name, size, price, material);
        setBrimWidth(brimWidth);
    }

    public double getBrimWidth() {
        return brimWidth;
    }

    /**
     * Встановлює ширину полів.
     *
     * @param brimWidth ширина полів
     * @throws IllegalArgumentException якщо {@code brimWidth <= 0}
     */
    public void setBrimWidth(double brimWidth) {
        if (brimWidth <= 0) {
            throw new IllegalArgumentException("Brim width must be greater than 0.");
        }
        this.brimWidth = brimWidth;
    }

    /**
     * Повертає {@code true}, якщо головний убір дає виражений захист від сонця.
     *
     * @return {@code true}, якщо ширина полів не менша за 7
     */
    public boolean hasSunProtection() {
        return brimWidth >= 7;
    }

    @Override
    public String getType() {
        return "Hat";
    }

    @Override
    public String toString() {
        return super.toString() + ", brimWidth=" + brimWidth;
    }
}
