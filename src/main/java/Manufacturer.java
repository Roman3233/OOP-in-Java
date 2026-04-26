import java.time.Year;

public class Manufacturer {
    private String name;
    private String country;
    private int foundedYear;

    public Manufacturer(String name, String country, int foundedYear) {
        setName(name);
        setCountry(country);
        setFoundedYear(foundedYear);
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public int getFoundedYear() {
        return foundedYear;
    }

    public void setName(String name) {
        this.name = validateTextField(name, "Name");
    }

    public void setCountry(String country) {
        this.country = validateTextField(country, "Country");
    }

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
