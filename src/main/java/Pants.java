public class Pants extends Clothes {
    private double waistSize;

    public Pants(String name, Size size, double price, String material, double waistSize) {
        super(name, size, price, material);
        setWaistSize(waistSize);
    }

    public Pants(String name, Size size, double price, String material,
                 Manufacturer manufacturer, double waistSize) {
        super(name, size, price, material, manufacturer);
        setWaistSize(waistSize);
    }

    public double getWaistSize() {
        return waistSize;
    }

    public void setWaistSize(double waistSize) {
        if (waistSize <= 0) {
            throw new IllegalArgumentException("Waist size must be greater than 0.");
        }
        this.waistSize = waistSize;
    }

    @Override
    public String getType() {
        return "Pants";
    }

    @Override
    public String toString() {
        return super.toString() + ", waistSize=" + waistSize;
    }
}
