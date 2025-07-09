
package categoryDao;

import java.util.List;
import model.Category;


public interface ICategoryDAO {
     List<Category> selectAllCategories();
     Category selectCategoryById(int id); 
}
