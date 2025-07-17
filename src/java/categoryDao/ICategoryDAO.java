
package categoryDao;

import java.sql.SQLException;
import java.util.List;
import model.AccessoryCategory;
import model.Category;


public interface ICategoryDAO {
     List<Category> selectAllCategories();
     Category selectCategoryById(int id); 
}
