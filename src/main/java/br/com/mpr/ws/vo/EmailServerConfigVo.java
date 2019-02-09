package br.com.mpr.ws.vo;

/**
 * Created by wagner on 08/02/19.
 */
public class EmailServerConfigVo {

    private String smtp;
    private Integer port;
    private String user;
    private String pass;
    private Boolean ssl;
    private String from;
    private String name;

    public Boolean getSsl() {
        return ssl;
    }
    public void setSsl(Boolean ssl) {
        this.ssl = ssl;
    }
    public String getSmtp() {
        return smtp;
    }
    public void setSmtp(String smtp) {
        this.smtp = smtp;
    }
    public Integer getPort() {
        return port;
    }
    public void setPort(Integer port) {
        this.port = port;
    }
    public String getUser() {
        return user;
    }
    public void setUser(String user) {
        this.user = user;
    }
    public String getPass() {
        return pass;
    }
    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
