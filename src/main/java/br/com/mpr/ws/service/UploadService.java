package br.com.mpr.ws.service;

import br.com.mpr.ws.entity.UploadEntity;
import br.com.mpr.ws.vo.UploadForm;

/**
 *
 */
public interface UploadService {


    UploadEntity upload(UploadForm form);
}
