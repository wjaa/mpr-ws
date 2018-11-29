package br.com.mpr.ws.service;

import br.com.mpr.ws.exception.ImagemServiceException;

/**
 *
 */
public interface ImagemService {


    String uploadFotoCliente(byte[] img, String originName) throws ImagemServiceException;

    String uploadFotoProdutoDestaque(byte[] img, String originName) throws ImagemServiceException;

    String uploadFotoProdutoPreview(byte[] img, String originName) throws ImagemServiceException;

    String uploadFotoCatalogo(byte[] img, String originName) throws ImagemServiceException;

    String getUrlFotoCatalogo(String foto);

    String getUrlFotoCliente(String foto);
}
