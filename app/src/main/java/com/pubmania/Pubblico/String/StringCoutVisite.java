package com.pubmania.Pubblico.String;

public class StringCoutVisite {
    public String id,conteggio,emailCliente,emailPub,tisegue,ora;

    public StringCoutVisite(){

    }

    public StringCoutVisite(String ora, String id, String conteggio, String emailCliente, String emailPub, String tisegue) {
        this.id = id;
        this.ora = ora;
        this.conteggio = conteggio;
        this.emailCliente = emailCliente;
        this.emailPub = emailPub;
        this.tisegue = tisegue;
    }

    public String getOra() {
        return ora;
    }

    public void setOra(String ora) {
        this.ora = ora;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getConteggio() {
        return conteggio;
    }

    public void setConteggio(String conteggio) {
        this.conteggio = conteggio;
    }

    public String getEmailCliente() {
        return emailCliente;
    }

    public void setEmailCliente(String emailCliente) {
        this.emailCliente = emailCliente;
    }

    public String getEmailPub() {
        return emailPub;
    }

    public void setEmailPub(String emailPub) {
        this.emailPub = emailPub;
    }

    public String getTisegue() {
        return tisegue;
    }

    public void setTisegue(String tisegue) {
        this.tisegue = tisegue;
    }
}
