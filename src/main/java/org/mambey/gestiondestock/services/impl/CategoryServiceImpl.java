package org.mambey.gestiondestock.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.mambey.gestiondestock.dto.CategoryDto;
import org.mambey.gestiondestock.exception.EntityNotFoundException;
import org.mambey.gestiondestock.exception.ErrorCodes;
import org.mambey.gestiondestock.exception.InvalidOperationException;
import org.mambey.gestiondestock.exception.InvalidEntityException;
import org.mambey.gestiondestock.model.Article;
import org.mambey.gestiondestock.model.Category;
import org.mambey.gestiondestock.repository.ArticleRepository;
import org.mambey.gestiondestock.repository.CategoryRepository;
import org.mambey.gestiondestock.services.CategoryService;
import org.mambey.gestiondestock.services.ObjectsValidator;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService{

    private final CategoryRepository categoryRepository;
    private final ArticleRepository articleRepository;
    private final ObjectsValidator<CategoryDto> categoryValidator;

    @Override
    public CategoryDto save(CategoryDto dto) {
        
        dto.setIdEntreprise(Integer.parseInt(MDC.get("idEntreprise")));

        var violations = categoryValidator.validate(dto);

        if(!violations.isEmpty()){
            log.error("La categorie n'est pas valide {}", dto);
            throw new InvalidEntityException("Categorie invalide", ErrorCodes.CATEGORY_NOT_VALID, violations);
        }

        return CategoryDto.fromEntity(
            categoryRepository.save(CategoryDto.toEntity(dto))
        );
    }

    @Override
    public CategoryDto findById(Integer id) {
        if(id == null){
            log.error("Category ID is null");
            return null;
        }

        Optional<Category> category = categoryRepository.findById(id);

        return category.map(CategoryDto::fromEntity)
                      .orElseThrow(() -> new EntityNotFoundException(
                        "Aucune categorie avec l'ID " + id + " n'a été trouvé dans la BDD", 
                        ErrorCodes.CATEGORY_NOT_FOUND));
    }

    @Override
    public List<CategoryDto> findAll() {
        return categoryRepository.findAll().stream()
                                .map(CategoryDto::fromEntity)
                                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if(id == null){
            log.error("Category ID is null");
            return;
        }

        findById(id);
        
        List<Article> articles = articleRepository.findAllByCategoryId(id);
        
        if(!articles.isEmpty()){
            throw new InvalidOperationException(
                "Impossible de supprimer une catégorie déjà utilisé",
                ErrorCodes.CATEGORY_ALREADY_IN_USE
            );
        }

        categoryRepository.deleteById(id);
    }
    
}
