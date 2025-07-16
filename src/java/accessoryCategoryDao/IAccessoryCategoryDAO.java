
package accessoryCategoryDao;


import java.sql.SQLException;
import java.util.List;
import model.AccessoryCategory;

public interface IAccessoryCategoryDAO {
    List<AccessoryCategory> getAllCategories() throws SQLException;
    AccessoryCategory getById(int id) throws SQLException;
}
