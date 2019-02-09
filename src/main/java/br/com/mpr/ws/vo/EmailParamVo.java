package br.com.mpr.ws.vo;

/**
 * Created by wagner on 08/02/19.
 */
public class EmailParamVo {
    private String email;
    private String title;
    private String body;

    public EmailParamVo(String email,String title, String body) {
        this.email = email;
        this.title = title;
        this.body = body;
    }

    public EmailParamVo(){}

    public String getTitle() {
        return title;
    }
    public EmailParamVo setTitle(String title) {
        this.title = title;
        return this;
    }
    public String getBody() {
        return body;
    }
    public EmailParamVo setBody(String body) {
        this.body = body;
        return this;
    }
    public String getEmail() {
        return email;
    }
    public EmailParamVo setEmail(String email) {
        this.email = email;
        return this;
    }
}
