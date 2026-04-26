public class Manufacturer {
    private String name;
    private String country;

    public Manufacturer(String name, String country) {
        setName(name);
        setCountry(country);
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public void setName(String name) {
        this.name = validateTextField(name, "Name");
    }

    public void setCountry(String country) {
        this.country = validateTextField(country, "Country");
    }

    @Override
    public String toString() {
        return "Manufacturer: " +
                "name='" + name + '\'' +
                ", country='" + country + '\'';
    }

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
