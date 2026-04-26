import java.time.Year;

/**
 * Представляє виробника одягу.
 */
public class Manufacturer {
    private String name;
    private String country;
    private int foundedYear;

    /**
     * Створює виробника.
     *
     * @param name        назва виробника (не порожня)
     * @param country     країна (не порожня)
     * @param foundedYear рік заснування (1..поточний рік)
     * @throws IllegalArgumentException якщо будь-який аргумент некоректний
     */
    public Manufacturer(String name, String country, int foundedYear) {
        setName(name);
        setCountry(country);
        setFoundedYear(foundedYear);
    }

    /**
     * Конструктор копіювання.
     *
     * @param other об'єкт-джерело (не {@code null})
     * @throws IllegalArgumentException якщо {@code other} дорівнює {@code null}
     */
    public Manufacturer(Manufacturer other) {
        if (other == null) {
            throw new IllegalArgumentException("Manufacturer cannot be null.");
        }
        this.name = other.name;
        this.country = other.country;
        this.foundedYear = other.foundedYear;
    }

    /**
     * @return назва виробника
     */
    public String getName() {
        return name;
    }

    /**
     * @return країна
     */
    public String getCountry() {
        return country;
    }

    /**
     * @return рік заснування
     */
    public int getFoundedYear() {
        return foundedYear;
    }

    /**
     * Встановлює назву виробника.
     *
     * @param name назва (не порожня)
     * @throws IllegalArgumentException якщо {@code name} дорівнює {@code null} або порожня/з пробілів
     */
    public void setName(String name) {
        this.name = validateTextField(name, "Name");
    }

    /**
     * Встановлює країну.
     *
     * @param country країна (не порожня)
     * @throws IllegalArgumentException якщо {@code country} дорівнює {@code null} або порожня/з пробілів
     */
    public void setCountry(String country) {
        this.country = validateTextField(country, "Country");
    }

    /**
     * Встановлює рік заснування.
     *
     * @param foundedYear рік заснування (1..поточний рік)
     * @throws IllegalArgumentException якщо {@code foundedYear} поза допустимим діапазоном
     */
    public void setFoundedYear(int foundedYear) {
        int currentYear = Year.now().getValue();
        if (foundedYear <= 0 || foundedYear > currentYear) {
            throw new IllegalArgumentException("Founded year must be between 1 and " + currentYear + ".");
        }
        this.foundedYear = foundedYear;
    }

    @Override
    public String toString() {
        return "Manufacturer: " +
                "name='" + name + '\'' +
                ", country='" + country + '\'' +
                ", foundedYear=" + foundedYear;
    }

    /**
     * Перевіряє, що текстове поле не дорівнює {@code null} та не є порожнім/з пробілів.
     *
     * @param value     значення
     * @param fieldName назва поля для повідомлення про помилку
     * @return обрізане значення (trim)
     * @throws IllegalArgumentException якщо {@code value} дорівнює {@code null} або порожнє/з пробілів
     */
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
