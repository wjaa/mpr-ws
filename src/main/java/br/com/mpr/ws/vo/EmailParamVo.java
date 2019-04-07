package br.com.mpr.ws.vo;

import java.util.Map;

/**
 * Created by wagner on 08/02/19.
 */
public class EmailParamVo {
    private String to;
    private String template;
    private Map<String,String> params;
    private String title;


    public EmailParamVo(){}


    public EmailParamVo(String to, String template, Map<String, String> params) {
        this.to = to;
        this.template = template;
        this.params = params;
    }


    public String getTo() {
        return to;
    }

    public EmailParamVo setTo(String to) {
        this.to = to;
        return this;
    }

    public String getTemplate() {
        return template;
    }

    public EmailParamVo setTemplate(String template) {
        this.template = template;
        return this;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public EmailParamVo setParams(Map<String, String> params) {
        this.params = params;
        return this;
    }


    public String getTitle() {
        return title;
    }

    public EmailParamVo setTitle(String title) {
        this.title = title;
        return this;
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
