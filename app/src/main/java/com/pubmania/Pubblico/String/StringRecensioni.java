package com.pubmania.Pubblico.String;

public class StringRecensioni {

    public StringRecensioni(){

    }

    public String id,emailCliente,emailPub,idPost,nomeLocale,urlFotoProfilo,token;

    public StringRecensioni(String token, String id, String emailCliente, String emailPub, String idPost,String nomeLocale,String urlFotoProfilo) {
        this.id = id;
        this.token = token;
        this.urlFotoProfilo = urlFotoProfilo;
        this.nomeLocale = nomeLocale;
        this.emailCliente = emailCliente;
        this.emailPub = emailPub;
        this.idPost = idPost;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUrlFotoProfilo() {
        return urlFotoProfilo;
    }

    public void setUrlFotoProfilo(String urlFotoProfilo) {
        this.urlFotoProfilo = urlFotoProfilo;
    }

    public String getNomeLocale() {
        return nomeLocale;
    }

    public void setNomeLocale(String nomeLocale) {
        this.nomeLocale = nomeLocale;
    }

    public String getEmailCliente() {
        return emailCliente;
    }

    public void setEmailCliente(String emailCliente) {
        this.emailCliente = emailCliente;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmailPub() {
        return emailPub;
    }

    public void setEmailPub(String emailPub) {
        this.emailPub = emailPub;
    }

    public String getIdPost() {
        return idPost;
    }

    public void setIdPost(String idPost) {
        this.idPost = idPost;
    }
}
