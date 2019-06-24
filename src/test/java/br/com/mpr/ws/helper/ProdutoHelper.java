package br.com.mpr.ws.helper;

import br.com.mpr.ws.vo.ProdutoVo;
import org.junit.Assert;

public class ProdutoHelper {

    public static void validarInfoProduto(ProdutoVo p) {
        Assert.assertNotNull(p.getDescricao());
        Assert.assertNotNull(p.getPreco());
        Assert.assertNotNull(p.getImgDestaque());
        Assert.assertNotNull(p.getDescricaoDetalhada());
        Assert.assertNotNull(p.getImagensDestaque());
        Assert.assertTrue(p.getImagensDestaque().size() > 0);
    }

}
