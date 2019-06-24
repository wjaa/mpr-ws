package br.com.mpr.ws.service;

import br.com.mpr.ws.exception.ImagemServiceException;

import java.io.File;
import java.util.List;

/**
 *
 */
public interface ImagemService {


    String uploadFotoCliente(byte[] img, String originName) throws ImagemServiceException;

    String uploadFotoDestaqueProduto(byte[] img, String originName) throws ImagemServiceException;

    String uploadFotoPreviewProduto(byte[] img, String originName) throws ImagemServiceException;

    String uploadFotoCatalogo(byte[] img, String originName) throws ImagemServiceException;

    String getUrlFotoCatalogo(String foto);

    String getUrlFotoCliente(String foto);

    String getUrlPreviewProduto(String foto);

    String getUrlImagemDestaque(String foto);

    String getUrlPreviewCliente(String foto);

    File getFileDestaqueProduto(String foto);

    File getFilePreviewProduto(String foto);

    File getFileFotoCatalogo(String foto);

    File getFileFotoCliente(String foto);

    File getFileFotoPreviewCliente(String foto);

    void removePreviewCliente(String foto);

    void removeFotoCliente(String foto);

    String createPreviewCliente(String previewProduto, List<String> fotosCliente, List<String> fotosCatalogo)
            throws ImagemServiceException;
}
