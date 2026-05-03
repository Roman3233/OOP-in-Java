import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Store 
{
    private String name;
    private List<Clothes> clothes;
    private Map<String, Integer> quantities;

    public Store(String name, List<Clothes> clothes) {
        this.name = ValidateName(name);
        this.clothes = clothes;
        this.quantities = new LinkedHashMap<>();
    }

    public String getName() {
        return name;
    }

    public List<Clothes> getClothes() {
        return clothes;
    }  

    public Map<String, Integer> getQuantities() {
        return quantities;
    }

    public void addNewClothes(Clothes item, int quantity) {
        if (item == null) {
            throw new IllegalArgumentException("Clothes item cannot be null.");
        }
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative.");
        }
        String itemName = item.getName();
        quantities.put(itemName, quantities.getOrDefault(itemName, 0) + quantity);
    }
    
    private String ValidateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Store name cannot be null or empty.");
        }
        return name.trim();
    }
}
