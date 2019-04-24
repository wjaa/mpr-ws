package br.com.mpr.ws.vo;

import br.com.mpr.ws.entity.CatalogoEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 */
public class ImagemExclusivaVo {


    private Long idCatalogo;
    private Long idCatalogoGrupo;
    private String urlImg;
    private String descricao;
    private String nomeCatalogo;


    public ImagemExclusivaVo() {}

    public ImagemExclusivaVo(CatalogoEntity c) {
        BeanUtils.copyProperties(c,this);
        this.idCatalogo = c.getId();
        this.urlImg = c.getImg();
        this.nomeCatalogo = c.getCatalogoGrupo().getNome();
    }


    @JsonIgnore
    public static List<ImagemExclusivaVo> toVo(List<CatalogoEntity> list) {
        if (CollectionUtils.isEmpty(list)){
            return Collections.emptyList();
        }

        List<ImagemExclusivaVo> listVo = new ArrayList<>(list.size());

        for(CatalogoEntity c : list){
            listVo.add(new ImagemExclusivaVo(c));
        }

        return listVo;
    }

    public Long getIdCatalogo() {
        return idCatalogo;
    }

    public void setIdCatalogo(Long idCatalogo) {
        this.idCatalogo = idCatalogo;
    }

    public Long getIdCatalogoGrupo() {
        return idCatalogoGrupo;
    }

    public void setIdCatalogoGrupo(Long idCatalogoGrupo) {
        this.idCatalogoGrupo = idCatalogoGrupo;
    }

    public String getUrlImg() {
        return urlImg;
    }

    public void setUrlImg(String urlImg) {
        this.urlImg = urlImg;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getNomeCatalogo() {
        return nomeCatalogo;
    }

    public void setNomeCatalogo(String nomeCatalogo) {
        this.nomeCatalogo = nomeCatalogo;
    }
}
