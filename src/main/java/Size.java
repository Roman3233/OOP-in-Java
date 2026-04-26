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
     * Перетворює текст на значення {@link Size}.
     * <p>
     * Розбір не залежить від регістру та ігнорує пробіли на початку/в кінці.
     *
     * @param value текст розміру (наприклад: {@code "m"}, {@code " XL "})
     * @return відповідна константа перерахування
     * @throws IllegalArgumentException якщо {@code value} дорівнює {@code null}, порожній/з пробілів
     *                                  або не належить до: XS, S, M, L, XL, XXL
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
