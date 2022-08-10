package com.cg.service.category;
import com.cg.model.Category;
import com.cg.model.dto.CategoryDTO;
import com.cg.service.IGeneralService;

import java.util.List;

public interface CategoryService extends IGeneralService<Category> {


    Category save(Category category);


    Category findByTitle(String title);

    List<CategoryDTO> findAllCategoryDTO();
}
