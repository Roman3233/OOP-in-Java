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
        if (foundedYear <= 0) {
            throw new IllegalArgumentException("Founded year must be greater than 0.");
        }
        if (foundedYear > currentYear) {
            throw new IllegalArgumentException("Founded year cannot be in the future.");
        }
        this.foundedYear = foundedYear;
    }

    private String validateTextField(String value, String fieldName) {
        if (value == null) {
            throw new IllegalArgumentException(fieldName + " cannot be null.");
        }
        if (value.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " cannot be empty.");
        }
        return value.trim();
    }
}
