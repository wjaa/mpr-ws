package br.com.mpr.ws.service;

import br.com.mpr.ws.entity.FreteType;
import br.com.mpr.ws.vo.ResultFreteVo;

/**
 *
 */
public interface FreteService {

     ResultFreteVo calcFrete(FreteParam param);

     class FreteParam{

          public FreteParam(FreteType freteType, String cepDestino, Double peso, Double comp, Double larg, Double alt) {
               this.freteType = freteType;
               this.cepDestino = cepDestino;
               this.peso = peso;
               this.comp = comp;
               this.larg = larg;
               this.alt = alt;
          }

          private FreteType freteType;
          private String cepDestino;
          private Double peso;
          private Double comp;
          private Double larg;
          private Double alt;

          public FreteType getFreteType() {
               return freteType;
          }

          public String getCepDestino() {
               return cepDestino;
          }

          public Double getPeso() {
               return peso;
          }

          public Double getComp() {
               return comp;
          }

          public Double getLarg() {
               return larg;
          }

          public Double getAlt() {
               return alt;
          }
     }

}
