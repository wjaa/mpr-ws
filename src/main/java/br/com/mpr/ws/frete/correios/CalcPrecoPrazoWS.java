
package br.com.mpr.ws.frete.correios;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.3.1-SNAPSHOT
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "CalcPrecoPrazoWS", targetNamespace = "http://tempuri.org/", wsdlLocation = "http://ws.correios.com.br/calculador/CalcPrecoPrazo.asmx?wsdl")
public class CalcPrecoPrazoWS
    extends Service
{

    private final static URL CALCPRECOPRAZOWS_WSDL_LOCATION;
    private final static WebServiceException CALCPRECOPRAZOWS_EXCEPTION;
    private final static QName CALCPRECOPRAZOWS_QNAME = new QName("http://tempuri.org/", "CalcPrecoPrazoWS");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("http://ws.correios.com.br/calculador/CalcPrecoPrazo.asmx?wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        CALCPRECOPRAZOWS_WSDL_LOCATION = url;
        CALCPRECOPRAZOWS_EXCEPTION = e;
    }

    public CalcPrecoPrazoWS() {
        super(__getWsdlLocation(), CALCPRECOPRAZOWS_QNAME);

    }

    public CalcPrecoPrazoWS(WebServiceFeature... features) {
        super(__getWsdlLocation(), CALCPRECOPRAZOWS_QNAME, features);
    }

    public CalcPrecoPrazoWS(URL wsdlLocation) {
        super(wsdlLocation, CALCPRECOPRAZOWS_QNAME);
    }

    public CalcPrecoPrazoWS(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, CALCPRECOPRAZOWS_QNAME, features);
    }

    public CalcPrecoPrazoWS(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public CalcPrecoPrazoWS(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns CalcPrecoPrazoWSSoap
     */
    @WebEndpoint(name = "CalcPrecoPrazoWSSoap")
    public CalcPrecoPrazoWSSoap getCalcPrecoPrazoWSSoap() {
        return super.getPort(new QName("http://tempuri.org/", "CalcPrecoPrazoWSSoap"), CalcPrecoPrazoWSSoap.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns CalcPrecoPrazoWSSoap
     */
    @WebEndpoint(name = "CalcPrecoPrazoWSSoap")
    public CalcPrecoPrazoWSSoap getCalcPrecoPrazoWSSoap(WebServiceFeature... features) {
        return super.getPort(new QName("http://tempuri.org/", "CalcPrecoPrazoWSSoap"), CalcPrecoPrazoWSSoap.class, features);
    }

    private static URL __getWsdlLocation() {
        if (CALCPRECOPRAZOWS_EXCEPTION!= null) {
            throw CALCPRECOPRAZOWS_EXCEPTION;
        }
        return CALCPRECOPRAZOWS_WSDL_LOCATION;
    }

}
