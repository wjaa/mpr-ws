package br.com.mpr.ws.service;

import br.com.mpr.image.service.ImageService;
import br.com.mpr.ws.entity.UploadEntity;
import br.com.mpr.ws.vo.UploadForm;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

/**
 *
 */
@Service
public class UploadServiceImpl implements UploadService {


    @Override
    public UploadEntity upload(UploadForm form) {
        ImageService imageService = new ImageService();
        form.getImagens().forEach( i -> {
            try {
                File fileImage = File.createTempFile("mpr-image",".tmp");
                FileUtils.copyInputStreamToFile(i.getInputStream(),
                        fileImage);
                imageService.adjustAndResize(fileImage,200,
                        new File("/home/wagner/Downloads/" + i.getOriginalFilename()+ ".jpg"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        UploadEntity uploadEntity = new UploadEntity();
        uploadEntity.setToken("fjaksdlfjdsaklfjsdkl");
        uploadEntity.setId(12l);

        return uploadEntity;
    }
}
