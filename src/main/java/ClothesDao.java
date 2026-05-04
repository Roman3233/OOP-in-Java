import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ClothesDao {
    private static final String INSERT_SQL = """
            INSERT INTO clothes (
                type, name, size, price, material,
                waist_size, sleeve_length, pocket_count, brim_width
            ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;

    private final DBConnection dbConnection;

    public ClothesDao(DBConnection dbConnection) {
        if (dbConnection == null) {
            throw new IllegalArgumentException("DBConnection cannot be null.");
        }
        this.dbConnection = dbConnection;
    }

    public void insert(Clothes clothes) {
        if (clothes == null) {
            throw new IllegalArgumentException("Clothes cannot be null.");
        }

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_SQL)) {

            statement.setString(1, clothes.getType());
            statement.setString(2, clothes.getName());
            statement.setString(3, clothes.getSize().name());
            statement.setDouble(4, clothes.getPrice());
            statement.setString(5, clothes.getMaterial());

            if (clothes instanceof Pants pants) {
                statement.setDouble(6, pants.getWaistSize());
            } else {
                statement.setNull(6, java.sql.Types.NUMERIC);
            }

            if (clothes instanceof Shirts shirts) {
                statement.setDouble(7, shirts.getSleeveLength());
            } else {
                statement.setNull(7, java.sql.Types.NUMERIC);
            }

            if (clothes instanceof Jacket jacket) {
                statement.setInt(8, jacket.getPocketCount());
            } else {
                statement.setNull(8, java.sql.Types.INTEGER);
            }

            if (clothes instanceof Hat hat) {
                statement.setDouble(9, hat.getBrimWidth());
            } else {
                statement.setNull(9, java.sql.Types.NUMERIC);
            }

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to insert clothes into database.", e);
        }
    }
}
