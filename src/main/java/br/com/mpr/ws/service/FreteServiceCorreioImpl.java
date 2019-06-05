package br.com.mpr.ws.service;

import br.com.mpr.ws.dao.CommonDao;
import br.com.mpr.ws.entity.FreteCepEntity;
import br.com.mpr.ws.entity.FreteType;
import br.com.mpr.ws.entity.MprParameterType;
import br.com.mpr.ws.frete.correios.CResultado;
import br.com.mpr.ws.frete.correios.CServico;
import br.com.mpr.ws.frete.correios.CalcPrecoPrazoWS;
import br.com.mpr.ws.frete.correios.CalcPrecoPrazoWSSoap;
import br.com.mpr.ws.utils.DateUtils;
import br.com.mpr.ws.utils.NumberUtils;
import br.com.mpr.ws.vo.ResultFreteVo;
import com.sun.xml.ws.client.BindingProviderProperties;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.xml.ws.BindingProvider;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
 *
 */
@Service("FreteServiceCorreioImpl")
public class FreteServiceCorreioImpl implements FreteService {

    private final String PAC = "41106";
    private final String SEDEX = "40010";

    private static final Log LOG = LogFactory.getLog(FreteServiceCorreioImpl.class);

    @Autowired
    private MprParameterService parameterService;

    @Autowired
    private CommonDao commonDao;

    private final int readTimeoutInMS = 10000;
    private final int connectTimeoutInMS = 10000;
    private static CalcPrecoPrazoWS ws;

    @PostConstruct
    private void construct(){
        ws = new CalcPrecoPrazoWS();
    }

    @Override
    public ResultFreteVo calcFrete(FreteParam param){

        //PEGANDO CONFIGURACAO DE LIGA E DESLIGA DO FRETE CACHE
        Boolean usaFreteCache = parameterService.getParameterBoolean(MprParameterType.USA_FRETE_CACHE,false);


        //BUSCA UM FRETE CACHE
        FreteCepEntity freteCache = commonDao.findByPropertiesSingleResult(FreteCepEntity.class,
                new String[]{"cep","tipoFrete","peso"},
                new Object[]{param.getCepDestino(),param.getFreteType(),param.getPeso() <= 1 ? 1.0 : 2.0});


        if (usaFreteCache && freteCache != null){
            return getResultCache(freteCache);
        }


        CResultado resultado = null;

        try{
            CalcPrecoPrazoWSSoap soap = ws.getCalcPrecoPrazoWSSoap();
            Map requestCtx = ((BindingProvider) soap).getRequestContext();
            requestCtx.put(BindingProviderProperties.REQUEST_TIMEOUT, readTimeoutInMS);
            requestCtx.put(BindingProviderProperties.CONNECT_TIMEOUT, connectTimeoutInMS);
            resultado = this.calcPrecoFrete(param, soap);
        }catch(Exception ex){
            //TODO: NOTIFICAR SISTEMA DE MONITORACAO. ERRO CRITICO
            LOG.error("m=calcFrete, error=Erro ao calcular o frente", ex);

            return freteCache == null ? this.getResultFreteGenerico() : this.getResultCache(freteCache);
        }

        if (this.estaComErro(resultado)){
            //TODO: NOTIFICAR SISTEMA DE MONITORACAO. ERRO CRITICO
            LOG.error("Erro no calculo do frete, " +
                    (resultado.getServicos().getCServico().size() > 0 ?
                            "cod: " + resultado.getServicos().getCServico().get(0).getErro() +
                                    " message: " + resultado.getServicos().getCServico().get(0).getMsgErro() :
                            " Nenhum resultado foi encontrado.") );


            return freteCache == null ? this.getResultFreteError(resultado.getServicos().getCServico().size() > 0 ?
                    resultado.getServicos().getCServico().get(0) : null) : this.getResultCache(freteCache);
        }
        try{
            return this.convertResultFrete(resultado, param.getFreteType());
        }catch (Exception ex){
            //TODO: NOTIFICAR SISTEMA DE MONITORACAO. ERRO CRITICO
            LOG.error("Erro ao converter o resultado do frente.", ex);
            return freteCache == null ? this.getResultFreteGenerico() : this.getResultCache(freteCache);
        }


    }

    private CResultado calcPrecoFrete(FreteParam param, CalcPrecoPrazoWSSoap soap) {
        return soap.calcPrecoPrazoData(
                "", //codigo da empresa
                "", //senha da empresa
                FreteType.ECONOMICO.equals(param.getFreteType()) ? PAC :
                        SEDEX , //40010 sedex | 41106 pac
                parameterService.getParameter(MprParameterType.CEP_ORIGEM, "07093090"), //cep de origem (nosso cep)
                param.getCepDestino(), //cep do cliente
                param.getPeso() == null ? "1" : NumberUtils.formatPTbr(param.getPeso()), //peso do produto
                1, // 1 é pacote.
                param.getComp() != null ? new BigDecimal(30) : new BigDecimal(param.getComp()), //Comprimento da encomenda (incluindo embalagem),em centímetros (comp min 16cm)
                param.getAlt() != null ? new BigDecimal(4) : new BigDecimal(param.getAlt()), //Altura da encomenda (incluindo embalagem), em centímetros. (comp min 2cm)
                param.getLarg() != null ? new BigDecimal(25) : new BigDecimal(param.getLarg()), //Largura da encomenda (incluindo embalagem), em centímetros (larg min 11cm).
                new BigDecimal(0), //Diâmetro da encomenda (incluindo embalagem), em centímetros.
                "N", // Indica se a encomenda será entregue com o serviço adicional mão própria. Valores possíveis: S ou N (S – Sim, N – Não)
                new BigDecimal(0), //Indica se a encomenda será entregue com o serviço adicional valor declarado. Neste campo deve ser apresentado o valor declarado desejado, em Reais.
                "N", // Indica se a encomenda será entregue com o serviço adicional aviso de recebimento. Valores possíveis: S ou N (S – Sim, N – Não)
                DateUtils.formatddMMyyyy(new Date())//Data do calculo.
        );
    }

    private ResultFreteVo getResultCache(FreteCepEntity freteCep) {
        ResultFreteVo result = new ResultFreteVo();
        result.setValor(freteCep.getValor());
        result.setFreteType(freteCep.getTipoFrete());
        result.setMessageError("");
        Integer prazoEntrega = Integer.valueOf(freteCep.getDias());
        Integer prazoMontagem = parameterService.getParameterInteger(MprParameterType.PRAZO_MONTAGEM,2);
        Integer diasUteis = prazoEntrega + prazoMontagem;
        result.setDiasUteis(diasUteis);
        result.setPrevisaoEntrega(DateUtils.addUtilDays(new Date(),diasUteis));
        return result;
    }

    private boolean estaComErro(CResultado resultado) {
        CServico frete = resultado.getServicos().getCServico().get(0);
        return resultado.getServicos().getCServico().size() == 0 ||
                (!StringUtils.isEmpty(frete.getMsgErro()) &&
                        StringUtils.isEmpty(frete.getValor()) );
    }

    private ResultFreteVo getResultFreteError(CServico cServico) {
        if (cServico == null){
            return new ResultFreteVo().setMessageError("Erro no calculo do frete");
        }
        return new ResultFreteVo().setMessageError(cServico.getMsgErro());
    }

    private ResultFreteVo getResultFreteGenerico() {
        ResultFreteVo result = new ResultFreteVo();
        result.setDiasUteis(30);
        result.setValor(150.0);
        result.setFreteType(FreteType.ECONOMICO);
        result.setMessageError("Erro no calculo do frete, usando valor genérico.");
        result.setPrevisaoEntrega(DateUtils.addUtilDays(new Date(),30));
        return result;
    }

    private ResultFreteVo convertResultFrete(CResultado resultado, FreteType freteType) {
        ResultFreteVo result = new ResultFreteVo();
        for (CServico servico : resultado.getServicos().getCServico()){

            Double valor = NumberUtils.convertNumberPtBr(servico.getValor());
            if (valor == null && servico.getValor() != null){
                //os correios as veses manda um valor assim 198.E1982E2, então pegamos o valor inteiro.
                valor = NumberUtils.convertNumberPtBr(servico.getValor().split(".")[0]);
            }

            result.setValor(valor);

            Integer prazoEntrega = Integer.valueOf(servico.getPrazoEntrega());
            Integer prazoMontagem = parameterService.getParameterInteger(MprParameterType.PRAZO_MONTAGEM,2);
            Integer diasUteis = prazoEntrega + prazoMontagem;

            result.setDiasUteis(diasUteis);
            result.setPrevisaoEntrega(DateUtils.addUtilDays(new Date(), diasUteis));
            result.setFreteType(freteType);
            result.setMessageError(servico.getMsgErro());
            result.setObs(servico.getObsFim());
        }
        LOG.debug("m=convertResultFrete, " + result);
        return result;
    }
}
