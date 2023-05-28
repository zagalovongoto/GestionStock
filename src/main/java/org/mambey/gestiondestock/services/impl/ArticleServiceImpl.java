package org.mambey.gestiondestock.services.impl;

import java.util.List;
import java.util.Optional;

import org.mambey.gestiondestock.dto.ArticleDto;
import org.mambey.gestiondestock.exception.EntityNotFoundException;
import org.mambey.gestiondestock.exception.ErrorCodes;
import org.mambey.gestiondestock.exception.InvaliddEntityException;
import org.mambey.gestiondestock.model.Article;
import org.mambey.gestiondestock.repository.ArticleRepository;
import org.mambey.gestiondestock.services.ArticleService;
import org.mambey.gestiondestock.services.ObjectsValidator;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService{

    private final ArticleRepository articleRepository;

    private final ObjectsValidator<ArticleDto> articleValidator;

    @Override
    public ArticleDto save(ArticleDto dto) {

        System.out.println(dto.toString());
        var violations = articleValidator.validate(dto);

        if(!violations.isEmpty()){
            log.error("L'article n'est pas valide {}", dto);
            throw new InvaliddEntityException("Données invalides", ErrorCodes.ARTICLE_NOT_VALID, violations);
        }

        return ArticleDto.fromEntity(
            articleRepository.save(ArticleDto.toEntity(dto))
        );
    }

    @Override
    public ArticleDto findById(Integer id) {
        
        if(id == null){
            log.error("Article ID is null");
            return null;
        }

        Optional<Article> article = articleRepository.findById(id);

        return Optional.of(ArticleDto.fromEntity(article.get()))
                       .orElseThrow(() -> new EntityNotFoundException(
                            "Aucun article avec l'ID " + id + " n'a été trouvé dans la BDD", 
                            ErrorCodes.ARTICLE_NOT_FOUND)
                       );

    }

    @Override
    public ArticleDto findByCodeArticle(String codeArticle) {
        if(!StringUtils.hasLength(codeArticle)){
            log.error("Article CODE is null");
            return null;
        }

        Optional<Article> article = articleRepository.findByCodeArticle(codeArticle);

        return Optional.of(ArticleDto.fromEntity(article.get()))
                       .orElseThrow(() -> new EntityNotFoundException(
                            "Aucun article avec le CODE " + codeArticle + " n'a été trouvé dans la BDD", 
                            ErrorCodes.ARTICLE_NOT_FOUND)
                       );
    }

    @Override
    public List<ArticleDto> findAll() {
        return null;
    }

    @Override
    public void delete(Integer id) {
        
    }
    

}
