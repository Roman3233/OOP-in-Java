/**
 * Представляє предмет одягу з базовими атрибутами (назва, розмір, ціна, колір, матеріал)
 * та опційним {@link Manufacturer}.
 */
public class Clothes {
    private String name;
    private Size size;
    private double price;
    private String color;
    private String material;
    private Manufacturer manufacturer;
    private static int count = 0;

    /**
     * Створює предмет одягу без виробника.
     *
     * @param name     назва одягу (не порожня)
     * @param size     розмір (не {@code null})
     * @param price    ціна (має бути &gt; 0)
     * @param color    колір (не порожній)
     * @param material матеріал (не порожній)
     * @throws IllegalArgumentException якщо будь-який аргумент некоректний
     */
    public Clothes(String name, Size size, double price, String color, String material) {
        setName(name);
        setSize(size);
        setPrice(price);
        setColor(color);
        setMaterial(material);
        count++;
    }

    /**
     * Створює предмет одягу з виробником.
     *
     * @param name         назва одягу (не порожня)
     * @param size         розмір (не {@code null})
     * @param price        ціна (має бути &gt; 0)
     * @param color        колір (не порожній)
     * @param material     матеріал (не порожній)
     * @param manufacturer виробник (не {@code null})
     * @throws IllegalArgumentException якщо будь-який аргумент некоректний
     */
    public Clothes(String name, Size size, double price, String color, String material, Manufacturer manufacturer) {
        this(name, size, price, color, material);
        setManufacturer(manufacturer);
    }

    /**
     * Конструктор копіювання.
     *
     * @param obj об'єкт-джерело (не {@code null})
     * @throws IllegalArgumentException якщо {@code obj} дорівнює {@code null}
     */
    public Clothes(Clothes obj) {
        if (obj == null) {
            throw new IllegalArgumentException("Clothes cannot be null.");
        }
        this.name = obj.name;
        this.size = obj.size;
        this.price = obj.price;
        this.color = obj.color;
        this.material = obj.material;
        this.manufacturer = obj.manufacturer == null ? null : new Manufacturer(obj.manufacturer);
        count++;
    }

    /**
     * @return назва одягу
     */
    public String getName() {
        return name;
    }

    /**
     * @return розмір одягу
     */
    public Size getSize() {
        return size;
    }

    /**
     * @return матеріал
     */
    public String getMaterial() {
        return material;
    }

    /**
     * @return ціна
     */
    public double getPrice() {
        return price;
    }

    /**
     * @return колір
     */
    public String getColor() {
        return color;
    }

    /**
     * @return виробник або {@code null}, якщо не задано
     */
    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    /**
     * Повертає загальну кількість екземплярів {@link Clothes}, створених будь-яким конструктором.
     *
     * @return кількість створених екземплярів
     */
    public static int getCount() {
        return count;
    }

    /**
     * Встановлює назву одягу.
     *
     * @param name назва (не порожня)
     * @throws IllegalArgumentException якщо {@code name} дорівнює {@code null} або порожня/з пробілів
     */
    public void setName(String name) {
        this.name = validateTextField(name, "Name");
    }

    /**
     * Встановлює розмір одягу.
     *
     * @param size розмір (не {@code null})
     * @throws IllegalArgumentException якщо {@code size} дорівнює {@code null}
     */
    public void setSize(Size size) {
        if (size == null) {
            throw new IllegalArgumentException("Size cannot be null.");
        }
        this.size = size;
    }

    /**
     * Встановлює ціну.
     *
     * @param price ціна (має бути &gt; 0)
     * @throws IllegalArgumentException якщо {@code price} не більша за 0
     */
    public void setPrice(double price) {
        if (price <= 0) {
            throw new IllegalArgumentException("Price must be greater than 0.");
        }
        this.price = price;
    }

    /**
     * Встановлює колір.
     *
     * @param color колір (не порожній)
     * @throws IllegalArgumentException якщо {@code color} дорівнює {@code null} або порожній/з пробілів
     */
    public void setColor(String color) {
        this.color = validateTextField(color, "Color");
    }

    /**
     * Встановлює матеріал.
     *
     * @param material матеріал (не порожній)
     * @throws IllegalArgumentException якщо {@code material} дорівнює {@code null} або порожній/з пробілів
     */
    public void setMaterial(String material) {
        this.material = validateTextField(material, "Material");
    }
    
    /**
     * Встановлює виробника.
     *
     * @param manufacturer виробник (не {@code null})
     * @throws IllegalArgumentException якщо {@code manufacturer} дорівнює {@code null}
     */
    public void setManufacturer(Manufacturer manufacturer) {
        if (manufacturer == null) {
            throw new IllegalArgumentException("Manufacturer cannot be null.");
        }
        this.manufacturer = manufacturer;
    }

    @Override
    public String toString() {
        String base = "Clothes: " +
                "name='" + name + '\'' +
                ", size=" + size +
                ", price=" + price +
                ", color='" + color + '\'' +
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
               color.equals(other.color);
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
