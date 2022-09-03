package com.pubmania.Pubblico.String;

public class StringRegistrazionePub {

    public String email;
    public String nome;
    public String cognome;
    public String numero;
    public String id;
    public String partitaIva;
    public String follower;
    public String fotoProfilo;
    public String aLunedi,aMartedi,aMercoledi,aGiovedi,aVenerdi,aSabato,aDomenica;
    public String cLunedi,cMartedi,cMercoledi,cGiovedi,cVenerdi,cSabato,cDomenica;
    public String nomeLocale;
    public String viaLocale;


    public StringRegistrazionePub(){

    }


    public StringRegistrazionePub(String email, String nome, String cognome, String numero, String id, String partitaIva, String follower, String fotoProfilo,
                                  String aLunedi, String aMartedi, String aMercoledi, String aGiovedi, String aVenerdi, String aSabato, String aDomenica,
                                  String cLunedi, String cMartedi, String cMercoledi, String cGiovedi, String cVenerdi, String cSabato, String cDomenica,
                                  String nomeLocale,String viaLocale) {
        this.email = email;
        this.viaLocale = viaLocale;
        this.nome = nome;
        this.cognome = cognome;
        this.numero = numero;
        this.id = id;
        this.partitaIva = partitaIva;
        this.follower = follower;
        this.fotoProfilo = fotoProfilo;
        this.aLunedi = aLunedi;
        this.aMartedi = aMartedi;
        this.nomeLocale = nomeLocale;
        this.aMercoledi = aMercoledi;
        this.aGiovedi = aGiovedi;
        this.aVenerdi = aVenerdi;
        this.aSabato = aSabato;
        this.aDomenica = aDomenica;
        this.cLunedi = cLunedi;
        this.cMartedi = cMartedi;
        this.cMercoledi = cMercoledi;
        this.cGiovedi = cGiovedi;
        this.cVenerdi = cVenerdi;
        this.cSabato = cSabato;
        this.cDomenica = cDomenica;
    }

    public String getViaLocale() {
        return viaLocale;
    }

    public void setViaLocale(String viaLocale) {
        this.viaLocale = viaLocale;
    }

    public String getNomeLocale() {
        return nomeLocale;
    }

    public void setNomeLocale(String nomeLocale) {
        this.nomeLocale = nomeLocale;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPartitaIva() {
        return partitaIva;
    }

    public void setPartitaIva(String partitaIva) {
        this.partitaIva = partitaIva;
    }

    public String getFollower() {
        return follower;
    }

    public void setFollower(String follower) {
        this.follower = follower;
    }

    public String getFotoProfilo() {
        return fotoProfilo;
    }

    public void setFotoProfilo(String fotoProfilo) {
        this.fotoProfilo = fotoProfilo;
    }

    public String getaLunedi() {
        return aLunedi;
    }

    public void setaLunedi(String aLunedi) {
        this.aLunedi = aLunedi;
    }

    public String getaMartedi() {
        return aMartedi;
    }

    public void setaMartedi(String aMartedi) {
        this.aMartedi = aMartedi;
    }

    public String getaMercoledi() {
        return aMercoledi;
    }

    public void setaMercoledi(String aMercoledi) {
        this.aMercoledi = aMercoledi;
    }

    public String getaGiovedi() {
        return aGiovedi;
    }

    public void setaGiovedi(String aGiovedi) {
        this.aGiovedi = aGiovedi;
    }

    public String getaVenerdi() {
        return aVenerdi;
    }

    public void setaVenerdi(String aVenerdi) {
        this.aVenerdi = aVenerdi;
    }

    public String getaSabato() {
        return aSabato;
    }

    public void setaSabato(String aSabato) {
        this.aSabato = aSabato;
    }

    public String getaDomenica() {
        return aDomenica;
    }

    public void setaDomenica(String aDomenica) {
        this.aDomenica = aDomenica;
    }

    public String getcLunedi() {
        return cLunedi;
    }

    public void setcLunedi(String cLunedi) {
        this.cLunedi = cLunedi;
    }

    public String getcMartedi() {
        return cMartedi;
    }

    public void setcMartedi(String cMartedi) {
        this.cMartedi = cMartedi;
    }

    public String getcMercoledi() {
        return cMercoledi;
    }

    public void setcMercoledi(String cMercoledi) {
        this.cMercoledi = cMercoledi;
    }

    public String getcGiovedi() {
        return cGiovedi;
    }

    public void setcGiovedi(String cGiovedi) {
        this.cGiovedi = cGiovedi;
    }

    public String getcVenerdi() {
        return cVenerdi;
    }

    public void setcVenerdi(String cVenerdi) {
        this.cVenerdi = cVenerdi;
    }

    public String getcSabato() {
        return cSabato;
    }

    public void setcSabato(String cSabato) {
        this.cSabato = cSabato;
    }

    public String getcDomenica() {
        return cDomenica;
    }

    public void setcDomenica(String cDomenica) {
        this.cDomenica = cDomenica;
    }
}
