package com.pubmania.Pubblico.String;

import java.util.List;

public class StringProdotto {


    public StringProdotto(){

    }

    public String id;
    public List<String> ingredienti;
    public String nome;
    public String prezzo;
    public String giorno;
    public String mese;
    public String ora;
    public String categoria;
    public List<String> foto;


    public StringProdotto(String id, List<String> ingredienti, String nome, String prezzo, String giorno, String mese, String ora, String categoria, List<String> foto) {
        this.id = id;
        this.ingredienti = ingredienti;
        this.nome = nome;
        this.prezzo = prezzo;
        this.giorno = giorno;
        this.mese = mese;
        this.ora = ora;
        this.categoria = categoria;
        this.foto = foto;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getIngredienti() {
        return ingredienti;
    }

    public void setIngredienti(List<String> ingredienti) {
        this.ingredienti = ingredienti;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(String prezzo) {
        this.prezzo = prezzo;
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
}
