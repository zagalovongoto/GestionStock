package org.mambey.gestiondestock.controller.api;
import static org.mambey.gestiondestock.utils.Constants.APP_ROOT;

import java.math.BigDecimal;
import java.util.List;

import org.mambey.gestiondestock.dto.MvtStkDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RequestMapping(value = APP_ROOT + "/mvtstks")
@Tag(name="mvtstk")
public interface MvtStkApi {
    
    @GetMapping(value= "/stockreel/{idArticle}")
    @Operation(operationId = "stockReelArticle")
    BigDecimal stockReelArticle(@PathVariable("idArticle") Integer idArticle);

    @GetMapping(value= "/filter/article/{idArticle}")
    @Operation(operationId = "mvtStkArticle")
    List<MvtStkDto> mvtStkArticle(@PathVariable("idArticle") Integer idArticle);

    @PostMapping(value= "/entree")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(operationId = "entreeStock")
    MvtStkDto entreeStock(MvtStkDto dto);

    @PostMapping(value= "/sortie")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(operationId = "sortieStock")
    MvtStkDto sortieStock(MvtStkDto dto);

    @PostMapping(value= "/correctionpos")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(operationId = "correctionStockPos")
    MvtStkDto correctionStockPos(MvtStkDto dto);

    @PostMapping(value= "/correctionneg")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(operationId = "correctionStockNeg")
    MvtStkDto correctionStockNeg(MvtStkDto dto);
}
