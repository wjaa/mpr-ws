package br.com.mpr.ws.service;

import br.com.mpr.ws.dao.CommonDao;
import br.com.mpr.ws.entity.*;
import br.com.mpr.ws.properties.MprWsProperties;
import br.com.mpr.ws.utils.NotificationUtils;
import br.com.mpr.ws.utils.NumberUtils;
import br.com.mpr.ws.utils.ObjectUtils;
import br.com.mpr.ws.vo.ClienteVo;
import br.com.mpr.ws.vo.EmailParamVo;
import br.com.mpr.ws.vo.ProdutoVo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wagner on 08/02/19.
 */
@Service
public class NotificationServiceImpl implements NotificationService {
    private static final Log LOG = LogFactory.getLog(NotificationServiceImpl.class);

    @Value("${spring.profiles.active}")
    private String activeProfile;

    @Autowired
    private MprWsProperties properties;

    @Autowired
    private CommonDao commonDao;

    @Autowired
    private ProdutoService produtoService;

    @Override
    public void sendTransactionCriadaBoleto(ClienteEntity cliente, PedidoEntity pedido, String urlBoleto) {
        //nao faz isso no ambiente de
        if ("test".equalsIgnoreCase(activeProfile)){
            return;
        }

        try{

            Map<String, String> params = new HashMap<>();
            params.put("{NOME_CLIENTE}",cliente.getNome());
            params.put("{IMG_TIPO_PAGAMENTO}" , properties.getImgIconeBoletoEmail());
            params.put("{TIPO_PAGAMENTO}" , "Boleto");
            params.put("{LINK_BOLETO}", urlBoleto);
            params.putAll(getParamsPedido(pedido));

            NotificationUtils.sendEmail(new EmailParamVo()
                            .setTo(cliente.getEmail())
                            .setTemplate("PEDIDO_CRIADO_BOLETO")
                            .setParams(params)
                            .setTitle(String.format("Pedido %s Criado!",pedido.getCodigoPedido()))
                    ,
                    properties
            );
        }catch (Exception ex){
            LOG.error("m=sendTransactionCriadaBoleto, erro ao montar o email de pedido criado.", ex);
        }

    }



    private String createJsonItens(PedidoEntity pedido) {
        ItemPedidoEmailParam itemPedidoEmailParam = new ItemPedidoEmailParam();
        for (ItemPedidoEntity item : pedido.getItens() ){
            itemPedidoEmailParam.putItem(item);
        }
        return ObjectUtils.toJson(itemPedidoEmailParam);
    }

    @Override
    public void sendTransactionCriadaCartao(ClienteEntity cliente, PedidoEntity pedido) {
        //nao faz isso no ambiente de
        if ("test".equalsIgnoreCase(activeProfile)){
            return;
        }

        try{

            Map<String, String> params = new HashMap<>();
            params.put("{NOME_CLIENTE}",cliente.getNome());
            params.put("{IMG_TIPO_PAGAMENTO}" , properties.getImgIconeCartaoEmail());
            params.put("{TIPO_PAGAMENTO}" , "Cartão de Crédito");
            params.putAll(getParamsPedido(pedido));

            NotificationUtils.sendEmail(new EmailParamVo()
                            .setTo(cliente.getEmail())
                            .setTemplate("PEDIDO_CRIADO_CARTAO")
                            .setParams(params)
                            .setTitle(String.format("Pedido %s Criado!",pedido.getCodigoPedido()))
                    ,
                    properties
            );
        }catch (Exception ex){
            LOG.error("m=sendTransactionCriadaCartao, erro ao montar o email de pedido criado.", ex);
        }
    }

    @Override
    public void sendPedidoCancelado(ClienteEntity cliente, PedidoEntity pedido) {
        //nao faz isso no ambiente de
        if ("test".equalsIgnoreCase(activeProfile)){
            return;
        }

        Map<String, String> params = new HashMap<>();
        params.put("{NOME_CLIENTE}",cliente.getNome());
        params.put("{NUMERO_PEDIDO}", pedido.getCodigoPedido());
        params.put("{LINK_PEDIDO}",properties.getLinkPedido() + pedido.getCodigoPedido());
        params.put("{JSON_ITEMS}",this.createJsonItens(pedido));

        NotificationUtils.sendEmail(new EmailParamVo()
                        .setTo(cliente.getEmail())
                        .setTemplate("PEDIDO_CANCELADO")
                        .setParams(params)
                        .setTitle(String.format("Pedido %s Cancelado!",pedido.getCodigoPedido()))
                        ,
                properties
        );
    }

    @Override
    public void sendEsqueceuSenha(ClienteEntity cliente, String hashTrocaSenha) {
        //nao faz isso no ambiente de
        if ("test".equalsIgnoreCase(activeProfile)){
            return;
        }

        Map<String, String> params = new HashMap<>();
        params.put("{NOME_CLIENTE}",cliente.getNome());
        params.put("{LINK_TROCA_SENHA}",properties.getLinkTrocaSenha() + hashTrocaSenha);

        NotificationUtils.sendEmail(new EmailParamVo()
                        .setTo(cliente.getEmail())
                        .setTemplate("ESQUECEU_SENHA")
                        .setParams(params)
                .setTitle("Altere sua senha.")
                ,
                properties
        );

    }

    @Override
    public void sendUsuarioCriado(ClienteEntity cliente) {
        //nao faz isso no ambiente de
        if ("test".equalsIgnoreCase(activeProfile)){
            return;
        }

        Map<String, String> params = new HashMap<>();
        params.put("{NOME_CLIENTE}",cliente.getNome());

        NotificationUtils.sendEmail(new EmailParamVo()
                        .setTo(cliente.getEmail())
                        .setTemplate("USUARIO_CRIADO")
                        .setParams(params)
                        .setTitle("Usuário Criado!"),
                properties
        );

    }

    @Override
    public void sendPagamentoConfirmado(ClienteEntity cliente, PedidoEntity pedido) {
        //nao faz isso no ambiente de
        if ("test".equalsIgnoreCase(activeProfile)){
            return;
        }

        try{

            Map<String, String> params = new HashMap<>();
            params.put("{NOME_CLIENTE}",cliente.getNome());
            params.putAll(this.getParamTipoPagamento(pedido));
            params.putAll(getParamsPedido(pedido));

            NotificationUtils.sendEmail(new EmailParamVo()
                            .setTo(cliente.getEmail())
                            .setTemplate("PEDIDO_CONFIRMADO")
                            .setParams(params)
                            .setTitle(String.format("Pedido %s Confirmado!",pedido.getCodigoPedido()))
                    ,
                    properties
            );
        }catch (Exception ex){
            LOG.error("m=sendTransactionCriadaCartao, erro ao montar o email de pedido criado.", ex);
        }

    }

    private Map<String,String> getParamTipoPagamento(PedidoEntity pedido) {
        Map<String, String> params = new HashMap<>(2);
        if (PagamentoType.BOLETO.equals(pedido.getPagamentoType())){
            params.put("{IMG_TIPO_PAGAMENTO}" , properties.getImgIconeBoletoEmail());
            params.put("{TIPO_PAGAMENTO}" , "Boleto");
        }else{
            params.put("{IMG_TIPO_PAGAMENTO}" , properties.getImgIconeCartaoEmail());
            params.put("{TIPO_PAGAMENTO}" , "Cartão de Crédito");
        }
        return params;
    }

    class ItemPedidoEmailParam{
        private List<ParamValues> items;

        public List<ParamValues> getItems() {
            return items;
        }

        public void setItems(List<ParamValues> items) {
            this.items = items;
        }

        public void putItem(ItemPedidoEntity itemPedido){
            if (items == null){
                this.items = new ArrayList<>();
            }
            items.add(new ParamValues(itemPedido));

            if (itemPedido.getAnexos() != null){
                for (ItemPedidoAnexoEntity anexo : itemPedido.getAnexos()){
                    items.add(new ParamValues(anexo));
                }
            }
        }

    }

    class ParamValues {
        private List<KeyValue> values;

        public ParamValues(ItemPedidoEntity itemPedido) {
            values = new ArrayList<>();
            ProdutoVo produto = produtoService.getProdutoById(itemPedido.getIdProduto());
            values.add(new KeyValue("{URL_IMAGEM}",produto.getImgDestaque()));
            //TODO ALTERAR AQUI QUANDO FINALIZAR A TASK DE QUANTIDADE DO MESMO ITEM.
            values.add(new KeyValue("{QUANTIDADE}","1"));

            values.add(new KeyValue("{VALOR_PRODUTO}", NumberUtils.formatPTbr(itemPedido.getValor())));
            values.add(new KeyValue("{DESCRICAO_PRODUTO}", produto.getDescricao()));
        }

        public ParamValues(ItemPedidoAnexoEntity anexo) {

            //TODO O ANEXO SÓ SERÁ IMPRESSAO DE FOTO ????

            values = new ArrayList<>();
            values.add(new KeyValue("{URL_IMAGEM}",properties.getImgImpressaoFoto()));
            //TODO ALTERAR AQUI QUANDO FINALIZAR A TASK DE QUANTIDADE DO MESMO ITEM.
            values.add(new KeyValue("{QUANTIDADE}","1"));

            values.add(new KeyValue("{VALOR_PRODUTO}", "0,00"));

            //TODO ALTERAR AQUI QUANDO O ANEXO NÃO FOR DE IMPRESSAO.
            values.add(new KeyValue("{DESCRICAO_PRODUTO}", "Impressão de foto"));
        }

        public List<KeyValue> getValues() {
            return values;
        }

        public void setValues(List<KeyValue> values) {
            this.values = values;
        }
    }

    private class KeyValue {
        private String key;
        private String value;

        public KeyValue(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }


    private Map<String,String> getParamsPedido(PedidoEntity pedido) {
        Map<String, String> params = new HashMap<>();
        params.put("{NUMERO_PEDIDO}", pedido.getCodigoPedido());
        params.put("{LINK_PEDIDO}",properties.getLinkPedido() + pedido.getCodigoPedido());
        params.put("{JSON_ITEMS}",this.createJsonItens(pedido));
        params.put("{SUB_TOTAL}", NumberUtils.formatPTbr(pedido.getValorTotalItens()));
        params.put("{FRETE}", NumberUtils.formatPTbr(pedido.getValorFrete()));
        params.put("{TOTAL}", NumberUtils.formatPTbr(pedido.getValorTotal()));
        EnderecoEntity endereco = commonDao.get(EnderecoEntity.class, pedido.getIdEndereco());
        if (endereco != null){
            params.put("{NOME_ENDERECO}", endereco.getDescricao());
            params.put("{ENDERECO_1}", endereco.getEnderecoPart1());
            params.put("{ENDERECO_2}", endereco.getEnderecoPart2());
            params.put("{ENDERECO_REFERENCIA}", "");
        }
        params.put("{TIPO_FRETE}", pedido.getTipoFrete().getDescricao());
        params.put("{DIAS_ENTREGA}", pedido.getTipoFrete().getDescricao());
        return params;
    }
}
