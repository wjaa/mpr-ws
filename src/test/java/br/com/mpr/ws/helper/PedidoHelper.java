package br.com.mpr.ws.helper;

import br.com.mpr.ws.entity.PagamentoType;
import br.com.mpr.ws.exception.CarrinhoServiceException;
import br.com.mpr.ws.exception.CheckoutServiceException;
import br.com.mpr.ws.exception.ProdutoPreviewServiceException;
import br.com.mpr.ws.service.CarrinhoService;
import br.com.mpr.ws.service.CheckoutService;
import br.com.mpr.ws.service.ProdutoPreviewService;
import br.com.mpr.ws.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class PedidoHelper {

    @Autowired
    private CarrinhoService carrinhoService;

    @Autowired
    private CheckoutService checkoutService;

    @Autowired
    private ProdutoPreviewService produtoPreviewService;

    public static final Long ID_CLIENTE = 1L;
    public static final Long ID_PRODUTO = 4L;



    public CheckoutForm createCheckoutForm() throws CheckoutServiceException, ProdutoPreviewServiceException, CarrinhoServiceException {
        PreviewForm form = new PreviewForm();
        form.setIdCliente(ID_CLIENTE);
        form.setIdProduto(ID_PRODUTO);
        form.setAnexos(new ArrayList<>());
        AnexoVo anexo = new AnexoVo();
        anexo.setFoto(new byte[]{0, 0, 0, 0});
        anexo.setNomeArquivo("semluz.jpg");
        form.getAnexos().add(anexo);

        produtoPreviewService.addFoto(form);

        CarrinhoVo carrinho = carrinhoService.addCarrinho(ID_CLIENTE);

        //TODO CONTINUAR AQUI CRIANDO UM NOVO CARRINHO PQ O TESTE DE CIMA REMOVE O CARRINHO NO FINAL.
        //PRECISA TER UM CARRINHO PARA CADA TESTE.

        CheckoutVo checkout = this.checkoutService.checkout(carrinho.getIdCarrinho());
        CheckoutForm checkoutForm = new CheckoutForm();
        FormaPagamentoVo formaPagamentoVo = new FormaPagamentoVo();
        formaPagamentoVo.setPagamentoType(PagamentoType.CARTAO_CREDITO);
        checkoutForm.setFormaPagamento(formaPagamentoVo);
        checkoutForm.setIdCheckout(checkout.getId());
        return checkoutForm;
    }

}
