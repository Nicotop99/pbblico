package com.pubmania.Pubblico.String;

import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

public class StringRecensione {
    public String id,emailpub,emailPubblico,desc,titolo,valStruttura,valProdotti,valBagni,valQuantitaGente,valRagazze,valRagazzi,
            valPrezzi,valDivertimento,valServizio;
    public List<String> arrayList;

    public StringRecensione(){

    }

    public StringRecensione(List<String> arrayList, String valServizio, String id, String emailpub, String emailPubblico, String desc, String titolo, String valStruttura, String valProdotti, String valBagni,
                            String valQuantitaGente, String valRagazze, String valRagazzi, String valPrezzi, String valDivertimento) {
        this.id = id;
        this.emailpub = emailpub;
        this.arrayList = arrayList;
        this.emailPubblico = emailPubblico;
        this.desc = desc;
        this.valServizio = valServizio;
        this.titolo = titolo;
        this.valStruttura = valStruttura;
        this.valProdotti = valProdotti;
        this.valBagni = valBagni;
        this.valQuantitaGente = valQuantitaGente;
        this.valRagazze = valRagazze;
        this.valRagazzi = valRagazzi;
        this.valPrezzi = valPrezzi;
        this.valDivertimento = valDivertimento;
    }

    public String getValServizio() {
        return valServizio;
    }

    public List<String> getArrayList() {
        return arrayList;
    }

    public void setArrayList(List<String > arrayList) {
        this.arrayList = arrayList;
    }

    public void setValServizio(String valServizio) {
        this.valServizio = valServizio;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmailpub() {
        return emailpub;
    }

    public void setEmailpub(String emailpub) {
        this.emailpub = emailpub;
    }

    public String getEmailPubblico() {
        return emailPubblico;
    }

    public void setEmailPubblico(String emailPubblico) {
        this.emailPubblico = emailPubblico;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public String getValStruttura() {
        return valStruttura;
    }

    public void setValStruttura(String valStruttura) {
        this.valStruttura = valStruttura;
    }

    public String getValProdotti() {
        return valProdotti;
    }

    public void setValProdotti(String valProdotti) {
        this.valProdotti = valProdotti;
    }

    public String getValBagni() {
        return valBagni;
    }

    public void setValBagni(String valBagni) {
        this.valBagni = valBagni;
    }

    public String getValQuantitaGente() {
        return valQuantitaGente;
    }

    public void setValQuantitaGente(String valQuantitaGente) {
        this.valQuantitaGente = valQuantitaGente;
    }

    public String getValRagazze() {
        return valRagazze;
    }

    public void setValRagazze(String valRagazze) {
        this.valRagazze = valRagazze;
    }

    public String getValRagazzi() {
        return valRagazzi;
    }

    public void setValRagazzi(String valRagazzi) {
        this.valRagazzi = valRagazzi;
    }

    public String getValPrezzi() {
        return valPrezzi;
    }

    public void setValPrezzi(String valPrezzi) {
        this.valPrezzi = valPrezzi;
    }

    public String getValDivertimento() {
        return valDivertimento;
    }

    public void setValDivertimento(String valDivertimento) {
        this.valDivertimento = valDivertimento;
    }
}
