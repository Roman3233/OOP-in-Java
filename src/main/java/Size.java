/**
 * Стандартні розміри одягу.
 */
public enum Size {
    XS,
    S,
    M,
    L,
    XL,
    XXL;

    /**
     * Перетворює текстове значення на елемент переліку {@link Size}.
     * Пробіли на початку й у кінці ігноруються, регістр не має значення.
     *
     * @param value текстове значення розміру, наприклад {@code "m"} або {@code " XL "}
     * @return відповідне значення переліку
     * @throws IllegalArgumentException якщо значення null, порожнє або неприпустиме
     */
    public static Size fromString(String value) {
        if (value == null) {
            throw new IllegalArgumentException("Size cannot be null.");
        }

        String trimmed = value.trim();
        if (trimmed.isEmpty()) {
            throw new IllegalArgumentException("Size cannot be empty.");
        }

        try {
            return Size.valueOf(trimmed.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid size. Allowed: XS, S, M, L, XL, XXL.");
        }
    }
}
