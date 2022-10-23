package com.pubmania.Pubblico.String;

import java.util.List;

public class StringPost_coupon {

    public String id,descrizione,like,pinnato,categoria,token,fotoProfilo,nomeLocale;
    public List<String> foto;
    public String titolo,email,tipo,giorno,mese,ora,prezzo,quanteVolte,chi,qualeProdotto,volteUtilizzate;


    public StringPost_coupon(){

    }

    public StringPost_coupon(String fotoProfilo, String nomeLocale, String token,String id, String descrizione, String like, String pinnato, String categoria, List<String> foto, String titolo, String email, String tipo, String giorno, String mese, String ora, String prezzo, String quanteVolte, String chi, String qualeProdotto, String volteUtilizzate, String categoria1) {
        this.id = id;
        this.token = token;
        this.fotoProfilo = fotoProfilo;
        this.nomeLocale = nomeLocale;
        this.descrizione = descrizione;
        this.like = like;
        this.pinnato = pinnato;
        this.categoria = categoria;
        this.foto = foto;
        this.titolo = titolo;
        this.email = email;
        this.tipo = tipo;
        this.giorno = giorno;
        this.mese = mese;
        this.ora = ora;
        this.prezzo = prezzo;
        this.quanteVolte = quanteVolte;
        this.chi = chi;
        this.qualeProdotto = qualeProdotto;
        this.volteUtilizzate = volteUtilizzate;
        this.categoria = categoria1;
    }


    public String getFotoProfilo() {
        return fotoProfilo;
    }

    public void setFotoProfilo(String fotoProfilo) {
        this.fotoProfilo = fotoProfilo;
    }

    public String getNomeLocale() {
        return nomeLocale;
    }

    public void setNomeLocale(String nomeLocale) {
        this.nomeLocale = nomeLocale;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getLike() {
        return like;
    }

    public void setLike(String like) {
        this.like = like;
    }

    public String getPinnato() {
        return pinnato;
    }

    public void setPinnato(String pinnato) {
        this.pinnato = pinnato;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public List<String> getFoto() {
        return foto;
    }

    public void setFoto(List<String> foto) {
        this.foto = foto;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getGiorno() {
        return giorno;
    }

    public void setGiorno(String giorno) {
        this.giorno = giorno;
    }

    public String getMese() {
        return mese;
    }

    public void setMese(String mese) {
        this.mese = mese;
    }

    public String getOra() {
        return ora;
    }

    public void setOra(String ora) {
        this.ora = ora;
    }

    public String getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(String prezzo) {
        this.prezzo = prezzo;
    }

    public String getQuanteVolte() {
        return quanteVolte;
    }

    public void setQuanteVolte(String quanteVolte) {
        this.quanteVolte = quanteVolte;
    }

    public String getChi() {
        return chi;
    }

    public void setChi(String chi) {
        this.chi = chi;
    }

    public String getQualeProdotto() {
        return qualeProdotto;
    }

    public void setQualeProdotto(String qualeProdotto) {
        this.qualeProdotto = qualeProdotto;
    }

    public String getVolteUtilizzate() {
        return volteUtilizzate;
    }

    public void setVolteUtilizzate(String volteUtilizzate) {
        this.volteUtilizzate = volteUtilizzate;
    }
}
