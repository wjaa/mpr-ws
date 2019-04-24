package br.com.mpr.ws.service;

import br.com.mpr.ws.vo.ImagemExclusivaVo;

import java.util.List;

/**
 *
 */
public interface CatalogoService {


    List<ImagemExclusivaVo> listImgExclusivasByCatalogo(Long idCatalogo);
}
