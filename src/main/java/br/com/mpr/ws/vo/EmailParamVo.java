package br.com.mpr.ws.vo;

import java.util.Map;

/**
 * Created by wagner on 08/02/19.
 */
public class EmailParamVo {
    private String to;
    private String template;
    private Map<String,String> params;


    public EmailParamVo(){}


    public EmailParamVo(String to, String template, Map<String, String> params) {
        this.to = to;
        this.template = template;
        this.params = params;
    }


    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }


    @Override
    public String toString() {
        return "EmailParamVo{" +
                "to='" + to + '\'' +
                ", template='" + template + '\'' +
                ", params=" + params +
                '}';
    }
}
