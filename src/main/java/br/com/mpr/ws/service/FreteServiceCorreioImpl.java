package br.com.mpr.ws.service;

import br.com.mpr.ws.entity.FreteType;
import br.com.mpr.ws.frete.correios.CResultado;
import br.com.mpr.ws.frete.correios.CServico;
import br.com.mpr.ws.frete.correios.CalcPrecoPrazoWS;
import br.com.mpr.ws.utils.DateUtils;
import br.com.mpr.ws.utils.NumberUtils;
import br.com.mpr.ws.vo.ResultFreteVo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.Date;

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

    @Override
    public ResultFreteVo calcFrete(FreteParam param){
        CalcPrecoPrazoWS ws = new CalcPrecoPrazoWS();
        CResultado resultado = null;

        try{

            resultado = ws.getCalcPrecoPrazoWSSoap().calcPrecoPrazoData(
                    "", //codigo da empresa
                    "", //senha da empresa
                    FreteType.ECONOMICO.equals(param.getFreteType()) ? PAC :
                            SEDEX , //40010 sedex | 41106 pac
                    parameterService.getParameter(MprParameterService.MprParameter.CEP_ORIGEM, "07093090"), //cep de origem (nosso cep)
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
        }catch(Exception ex){
            //TODO: NOTIFICAR SISTEMA DE MONITORACAO. ERRO CRITICO
            LOG.error("m=calcFrete, error=Erro ao calcular o frente", ex);
            return this.getResultFreteGenerico();
        }

        if (resultado.getServicos().getCServico().size() == 0 ||
                !StringUtils.isEmpty(resultado.getServicos().getCServico().get(0).getMsgErro()) ){
            //TODO: NOTIFICAR SISTEMA DE MONITORACAO. ERRO CRITICO
            LOG.error("Erro no calculo do frete, " +
                    (resultado.getServicos().getCServico().size() > 0 ?
                            "cod: " + resultado.getServicos().getCServico().get(0).getErro() +
                                    " message: " + resultado.getServicos().getCServico().get(0).getMsgErro() :
                            " Nenhum resultado foi encontrado.") );
            return this.getResultFreteError(resultado.getServicos().getCServico().size() > 0 ?
                    resultado.getServicos().getCServico().get(0) : null);
        }
        try{
            return this.convertResultFrete(resultado);
        }catch (Exception ex){
            //TODO: NOTIFICAR SISTEMA DE MONITORACAO. ERRO CRITICO
            LOG.error("Erro ao converter o resultado do frente.", ex);
            return this.getResultFreteGenerico();
        }


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
        result.setValor(50.0);
        result.setPrevisaoEntrega(DateUtils.addDays(new Date(),30));
        return result;
    }

    private ResultFreteVo convertResultFrete(CResultado resultado) {
        ResultFreteVo result = new ResultFreteVo();
        for (CServico servico : resultado.getServicos().getCServico()){
            result.setValor(NumberUtils.convertNumberPtBr(servico.getValor()));
            result.setDiasUteis(Integer.valueOf(servico.getPrazoEntrega()));
            result.setPrevisaoEntrega(DateUtils.addDays(new Date(), Integer.valueOf(servico.getPrazoEntrega())));
        }
        LOG.debug("m=convertResultFrete, " + result);
        return result;
    }
}
