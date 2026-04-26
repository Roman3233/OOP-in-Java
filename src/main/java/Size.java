public enum Size {
    XS,
    S,
    M,
    L,
    XL,
    XXL;

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
