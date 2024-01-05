package org.mambey.gestiondestock.controller;

import java.util.List;

import org.mambey.gestiondestock.controller.api.CategoryApi;
import org.mambey.gestiondestock.dto.CategoryDto;
import org.mambey.gestiondestock.services.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class CategoryController implements CategoryApi{
    
    private final CategoryService categoryService;

    @Override
    public CategoryDto save(CategoryDto dto) {
        return categoryService.save(dto);
    }

    @Override
    public CategoryDto findById(Integer id) {
        return categoryService.findById(id);
    }

    @Override
    public List<CategoryDto> findAll() {
        return categoryService.findAll();
    }

    @Override
    public ResponseEntity<?> delete(Integer id) {
        categoryService.delete(id);

        return ResponseEntity.noContent().build();
    }

    
}
