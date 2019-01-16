package br.com.mpr.ws.service;

import br.com.mpr.ws.entity.EmbalagemEntity;
import br.com.mpr.ws.entity.ProdutoEntity;
import java.util.List;

/**
 * Created by wagner on 13/01/19.
 */
public interface EmbalagemService {

    EmbalagemEntity getEmbalagem(List<ProdutoEntity> produtos);

}
