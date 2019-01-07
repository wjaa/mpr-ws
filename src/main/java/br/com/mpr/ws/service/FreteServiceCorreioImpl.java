package br.com.mpr.ws.service;

import br.com.mpr.ws.frete.correios.CResultado;
import br.com.mpr.ws.frete.correios.CServico;
import br.com.mpr.ws.frete.correios.CalcPrecoPrazoWS;
import br.com.mpr.ws.utils.DateUtils;
import br.com.mpr.ws.utils.NumberUtils;
import br.com.mpr.ws.vo.ResultFreteVo;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 */
@Service("FreteServiceCorreioImpl")
public class FreteServiceCorreioImpl implements FreteService {


    @Override
    public ResultFreteVo calcFrete(String cepOrigem, String cepDestino) {
        CalcPrecoPrazoWS ws = new CalcPrecoPrazoWS();
        CResultado resultado = ws.getCalcPrecoPrazoWSSoap().calcPrecoPrazoData(
                "", //codigo da empresa
                "", //senha da empresa
                "41106", //40010 sedex | 41106 pac
                cepOrigem, //cep de origem (nosso cep)
                cepDestino, //cep do cliente
                "0.3", //peso do produto
                1, // 1 é pacote.
                new BigDecimal(30), //Comprimento da encomenda (incluindo embalagem),em centímetros
                new BigDecimal(4), //Altura da encomenda (incluindo embalagem), em centímetros.
                new BigDecimal(25), //Largura da encomenda (incluindo embalagem), em centímetros.
                new BigDecimal(0), //Diâmetro da encomenda (incluindo embalagem), em centímetros.
                "N", // Indica se a encomenda será entregue com o serviço adicional mão própria. Valores possíveis: S ou N (S – Sim, N – Não)
                new BigDecimal(0), //Indica se a encomenda será entregue com o serviço adicional valor declarado. Neste campo deve ser apresentado o valor declarado desejado, em Reais.
                "S", // Indica se a encomenda será entregue com o serviço adicional aviso de recebimento. Valores possíveis: S ou N (S – Sim, N – Não)
                DateUtils.formatddMMyyyy(new Date())//Data do calculo.
        );
        return convertResultFrete(resultado);
    }

    private ResultFreteVo convertResultFrete(CResultado resultado) {
        ResultFreteVo result = new ResultFreteVo();
        for (CServico servico : resultado.getServicos().getCServico()){
            System.out.println("preco: " +servico.getValor());
            result.setValor(NumberUtils.convertNumberPtBr(servico.getValor()));
            result.setDiasUteis(Integer.valueOf(servico.getPrazoEntrega()));
        }
        return result;
    }
}
