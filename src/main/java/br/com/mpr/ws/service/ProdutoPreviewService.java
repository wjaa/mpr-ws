package br.com.mpr.ws.service;

import br.com.mpr.ws.entity.ProdutoPreviewEntity;
import br.com.mpr.ws.exception.ProdutoPreviewServiceException;
import br.com.mpr.ws.vo.PreviewForm;
import br.com.mpr.ws.vo.ProdutoVo;

public interface ProdutoPreviewService {

    ProdutoVo addFoto(PreviewForm form) throws ProdutoPreviewServiceException;

    ProdutoPreviewEntity getProdutoPreviewByIdCliente(Long idCliente);

    ProdutoPreviewEntity getProdutoPreviewBySessionToken(String sessionToken);
}
