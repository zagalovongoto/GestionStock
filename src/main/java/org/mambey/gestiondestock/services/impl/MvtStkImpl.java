package org.mambey.gestiondestock.services.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.mambey.gestiondestock.dto.MvtStkDto;
import org.mambey.gestiondestock.exception.ErrorCodes;
import org.mambey.gestiondestock.exception.InvalidEntityException;
import org.mambey.gestiondestock.model.TypeMvtStk;
import org.mambey.gestiondestock.repository.MvtStkRepository;
import org.mambey.gestiondestock.services.ArticleService;
import org.mambey.gestiondestock.services.MvtStkService;
import org.mambey.gestiondestock.services.ObjectsValidator;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class MvtStkImpl implements MvtStkService{

    private final MvtStkRepository mvtStkRepository;
    private final ArticleService articleService;
    private final ObjectsValidator<MvtStkDto> mvtStkValidator;

    @Override
    public BigDecimal stockReelArticle(Integer idArticle) {
        if(idArticle == null){
            log.warn("ID article is null");
            return BigDecimal.valueOf(-1);
        }

        articleService.findById(idArticle);//lève une exception si l'article n'existe pas
        return mvtStkRepository.stockReelArticle(idArticle);
    }

    @Override
    public List<MvtStkDto> mvtStkArticle(Integer idArticle) {
        return mvtStkRepository.findAllByArticleId(idArticle).stream()
            .map(MvtStkDto::fromEntity)
            .collect(Collectors.toList());
    }

    @Override
    public MvtStkDto entreeStock(MvtStkDto dto) {
        var violations = mvtStkValidator.validate(dto);

        if(!violations.isEmpty()){
            log.error("Article is not valid {}", dto);
            throw new InvalidEntityException("Données invalides", ErrorCodes.MVT_STK_NOT_VALID, violations);
        }

        dto.setQuantite(
            BigDecimal.valueOf(
                Math.abs(dto.getQuantite().doubleValue())
            )
        );
        dto.setTypeMvt(TypeMvtStk.ENTREE);
        return MvtStkDto.fromEntity(
            mvtStkRepository.save(MvtStkDto.toEntity(dto))
        );
    }

    @Override
    public MvtStkDto sortieStock(MvtStkDto dto) {
        
        var violations = mvtStkValidator.validate(dto);

        if(!violations.isEmpty()){
            log.error("Article is not valid {}", dto);
            throw new InvalidEntityException("Données invalides", ErrorCodes.MVT_STK_NOT_VALID, violations);
        }

        dto.setQuantite(
            BigDecimal.valueOf(
                Math.abs(dto.getQuantite().doubleValue()) * -1
            )
        );
        dto.setTypeMvt(TypeMvtStk.SORTIE);
        return MvtStkDto.fromEntity(
            mvtStkRepository.save(MvtStkDto.toEntity(dto))
        );
    }

    @Override
    public MvtStkDto correctionStockPos(MvtStkDto dto) {
        
        var violations = mvtStkValidator.validate(dto);

        if(!violations.isEmpty()){
            log.error("Article is not valid {}", dto);
            throw new InvalidEntityException("Données invalides", ErrorCodes.MVT_STK_NOT_VALID, violations);
        }

        dto.setQuantite(
            BigDecimal.valueOf(
                Math.abs(dto.getQuantite().doubleValue()) * -1
            )
        );
        dto.setTypeMvt(TypeMvtStk.CORRECTION_POS);
        return MvtStkDto.fromEntity(
            mvtStkRepository.save(MvtStkDto.toEntity(dto))
        );
    }

    @Override
    public MvtStkDto correctionStockNeg(MvtStkDto dto) {
        
        var violations = mvtStkValidator.validate(dto);

        if(!violations.isEmpty()){
            log.error("Article is not valid {}", dto);
            throw new InvalidEntityException("Données invalides", ErrorCodes.MVT_STK_NOT_VALID, violations);
        }

        dto.setQuantite(
            BigDecimal.valueOf(
                Math.abs(dto.getQuantite().doubleValue()) * -1
            )
        );
        dto.setTypeMvt(TypeMvtStk.CORRECTION_NEG);
        return MvtStkDto.fromEntity(
            mvtStkRepository.save(MvtStkDto.toEntity(dto))
        );
    }
}
