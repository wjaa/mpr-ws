package br.com.mpr.ws.vo;

import br.com.mpr.ws.entity.CatalogoGrupoEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Grupo de catalogo
 */
public class CatalogoVo {

    private Long id;
    private String nome;

    public CatalogoVo(){}

    public CatalogoVo(CatalogoGrupoEntity c) {
        this.id = c.getId();
        this.nome = c.getNome();
    }

    public static List<CatalogoVo> toVo(List<CatalogoGrupoEntity> list) {
        List<CatalogoVo> catalogoVos = new ArrayList<>(list.size());

        list.stream().forEach( c -> {
            catalogoVos.add(new CatalogoVo(c));
        });


        return catalogoVos;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
