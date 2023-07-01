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

@RequestMapping(value = APP_ROOT + "/mvtstks")
public interface MvtStkApi {
    
    @GetMapping(value= "/stockreel/{idArticle}")
    BigDecimal stockReelArticle(@PathVariable("idArticle") Integer idArticle);

    @GetMapping(value= "/filter/article/{idArticle}")
    List<MvtStkDto> mvtStkArticle(@PathVariable("idArticle") Integer idArticle);

    @PostMapping(value= "/entree")
    @ResponseStatus(HttpStatus.CREATED)
    MvtStkDto entreeStock(MvtStkDto dto);

    @PostMapping(value= "/sortie")
    @ResponseStatus(HttpStatus.CREATED)
    MvtStkDto sortieStock(MvtStkDto dto);

    @PostMapping(value= "/correctionpos")
    @ResponseStatus(HttpStatus.CREATED)
    MvtStkDto correctionStockPos(MvtStkDto dto);

    @PostMapping(value= "/correctionneg")
    @ResponseStatus(HttpStatus.CREATED)
    MvtStkDto correctionStockNeg(MvtStkDto dto);
}
