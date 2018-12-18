
package br.com.mpr.ws.frete.correios;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected         content contained within this class.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ListaServicosResult" type="{http://tempuri.org/}cResultadoServicos"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "listaServicosResult"
})
@XmlRootElement(name = "ListaServicosResponse")
public class ListaServicosResponse {

    @XmlElement(name = "ListaServicosResult", required = true)
    protected CResultadoServicos listaServicosResult;

    /**
     * Gets the value of the listaServicosResult property.
     * 
     * @return
     *     possible object is
     *     {@link CResultadoServicos }
     *     
     */
    public CResultadoServicos getListaServicosResult() {
        return listaServicosResult;
    }

    /**
     * Sets the value of the listaServicosResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link CResultadoServicos }
     *     
     */
    public void setListaServicosResult(CResultadoServicos value) {
        this.listaServicosResult = value;
    }

}
