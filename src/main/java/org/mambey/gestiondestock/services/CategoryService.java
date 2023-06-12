package org.mambey.gestiondestock.services;
import java.util.List;

import org.mambey.gestiondestock.dto.CategoryDto;

public interface CategoryService {
    
    CategoryDto save(CategoryDto dto);

    CategoryDto findById(Integer id);

    List<CategoryDto> findAll();

    void delete(Integer id);
}
