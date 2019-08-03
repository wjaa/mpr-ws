package br.com.mpr.ws.service;

import br.com.mpr.ws.entity.ProdutoPreviewEntity;
import br.com.mpr.ws.entity.UploadEntity;
import br.com.mpr.ws.exception.ProdutoPreviewServiceException;
import br.com.mpr.ws.vo.ImagemPreviewVo;
import br.com.mpr.ws.vo.PreviewForm;
import br.com.mpr.ws.vo.ProdutoVo;
import br.com.mpr.ws.vo.UploadForm;

public interface ProdutoPreviewService {

    ProdutoVo addFoto(PreviewForm form) throws ProdutoPreviewServiceException;

    ProdutoPreviewEntity getProdutoPreviewByIdCliente(Long idCliente);

    ProdutoPreviewEntity getProdutoPreviewBySessionToken(String sessionToken);

    ImagemPreviewVo generatePreview(String uploadToken, String produtoRef) throws ProdutoPreviewServiceException;
}
