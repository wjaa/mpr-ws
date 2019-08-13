package br.com.mpr.ws.service;

import br.com.mpr.ws.dao.CommonDao;
import br.com.mpr.ws.entity.ImagemEntity;
import br.com.mpr.ws.entity.UploadEntity;
import br.com.mpr.ws.exception.UploadServiceException;
import br.com.mpr.ws.utils.StringUtils;
import br.com.mpr.ws.vo.UploadForm;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 */
@Service
public class UploadServiceImpl implements UploadService {

    private static final Log LOG = LogFactory.getLog(UploadServiceImpl.class);

    @Autowired
    private ImagemService imagemService;

    @Autowired
    private CommonDao commonDao;

    @Override
    public UploadEntity upload(UploadForm form) throws UploadServiceException {
        Assert.notNull(form.getImagens(),"Imagens do upload estão vazias!!!");

        List<ImagemEntity> imagens = new ArrayList<>(form.getImagens().size());
        try {

            for (MultipartFile i : form.getImagens()) {
                ImagemEntity imagemEntity = new ImagemEntity();
                String imageHi = imagemService.uploadFotoCliente(i.getBytes(), i.getOriginalFilename());
                File fileImageCliente = imagemService.getFileFotoCliente(imageHi);
                String imageThumb = imagemService.createThumbnailFotoCliente(fileImageCliente, 600);
                imagemEntity.setImagemHi(imageHi);
                imagemEntity.setImagemThumb(imageThumb);
                imagens.add(imagemEntity);

            }
        } catch (Exception e) {
            LOG.error("Erro ao gravar a imagem do cliente", e);
            throw new UploadServiceException("Erro no upload da imagem",e);
        }
        return saveUploadData(imagens);
    }

    @Override
    public UploadEntity getUploadByToken(String uploadToken) {
        UploadEntity uploadEntity = commonDao.findByPropertiesSingleResult(UploadEntity.class,
                new String[]{"token"},
                new Object[]{uploadToken});
        uploadEntity.setImagens(getImagensByIdUpload(uploadEntity.getId()));
        return uploadEntity;
    }

    private List<ImagemEntity> getImagensByIdUpload(Long idUpload) {
        return commonDao.findByProperties(ImagemEntity.class,
                new String[]{"idUpload"},
                new Object[]{idUpload});
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    private UploadEntity saveUploadData(List<ImagemEntity> imagens){
        UploadEntity uploadEntity = new UploadEntity();
        uploadEntity.setToken(StringUtils.createMD5(StringUtils.createRandomHash() + new Date().getTime()));
        uploadEntity = commonDao.save(uploadEntity);

        for(ImagemEntity imagemEntity: imagens){
            imagemEntity.setIdUpload(uploadEntity.getId());
            commonDao.save(imagemEntity);
        }

        return uploadEntity;
    }
}
