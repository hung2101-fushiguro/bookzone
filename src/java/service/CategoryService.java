
package service;

import categoryDao.CategoryDao;
import categoryDao.ICategoryDAO;
import java.util.List;
import model.Category;

public class CategoryService implements ICategoryService {

    private ICategoryDAO categoryDao;

    public CategoryService() {
        this.categoryDao = new CategoryDao(); // ← khởi tạo DAO ở đây
    }

    @Override
    public List<Category> selectAllCategories() {
        return categoryDao.selectAllCategories();
    }
    
    @Override
    public Category selectCategoryById(int id) {
        return categoryDao.selectCategoryById(id);
    }
}

