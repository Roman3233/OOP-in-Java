import java.util.List;

public class Store 
{
    private String name;
    private List<Clothes> clothes;

    public Store(String name, List<Clothes> clothes) {
        this.name = ValidateName(name);
        this.clothes = clothes;
    }

    public String getName() {
        return name;
    }

    public List<Clothes> getClothes() {
        return clothes;
    }  
    
    private String ValidateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Store name cannot be null or empty.");
        }
        return name.trim();
    }
}
