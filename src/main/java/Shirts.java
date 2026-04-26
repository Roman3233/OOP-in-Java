public class Shirts extends Clothes {
    private double sleeveLength;

    public Shirts(String name, Size size, double price, String color, String material, double sleeveLength) {
        super(name, size, price, color, material);
        setSleeveLength(sleeveLength);
    }

    public Shirts(String name, Size size, double price, String color, String material,
                  Manufacturer manufacturer, double sleeveLength) {
        super(name, size, price, color, material, manufacturer);
        setSleeveLength(sleeveLength);
    }

    public double getSleeveLength() {
        return sleeveLength;
    }

    public void setSleeveLength(double sleeveLength) {
        if (sleeveLength <= 0) {
            throw new IllegalArgumentException("Sleeve length must be greater than 0.");
        }
        this.sleeveLength = sleeveLength;
    }

    @Override
    public String toString() {
        return super.toString() + ", sleeveLength=" + sleeveLength;
    }
}
