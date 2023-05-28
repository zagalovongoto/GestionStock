package org.mambey.gestiondestock.services.impl;

import java.util.List;

import org.mambey.gestiondestock.dto.CategoryDto;
import org.mambey.gestiondestock.exception.ErrorCodes;
import org.mambey.gestiondestock.exception.InvaliddEntityException;
import org.mambey.gestiondestock.repository.CategoryRepository;
import org.mambey.gestiondestock.services.CategoryService;
import org.mambey.gestiondestock.services.ObjectsValidator;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService{

    private final CategoryRepository categoryRepository;

    private final ObjectsValidator<CategoryDto> categoryValidator;

    @Override
    public CategoryDto save(CategoryDto dto) {
        
        var violations = categoryValidator.validate(dto);

        if(!violations.isEmpty()){
            log.error("La categorie n'est pas valide {}", dto);
            throw new InvaliddEntityException("Categorie invalide", ErrorCodes.CATEGORY_NOT_VALID, violations);
        }

        return CategoryDto.fromEntity(
            categoryRepository.save(CategoryDto.toEntity(dto))
        );
    }

    @Override
    public CategoryDto findById(Integer id) {
        return null;
    }

    @Override
    public List<CategoryDto> findAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    @Override
    public void delete(Integer id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }
    
}
