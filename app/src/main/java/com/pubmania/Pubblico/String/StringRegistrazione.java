package com.pubmania.Pubblico.String;

public class StringRegistrazione {

    public StringRegistrazione(){

    }

    public String id,nome,cogonome,fotoProfilo,email,miPiacciono;

    public StringRegistrazione(String id, String nome, String cogonome, String fotoProfilo, String email,String miPiacciono) {
        this.id = id;
        this.miPiacciono = miPiacciono;
        this.nome = nome;
        this.cogonome = cogonome;
        this.fotoProfilo = fotoProfilo;
        this.email = email;
    }

    public String getMiPiacciono() {
        return miPiacciono;
    }

    public void setMiPiacciono(String miPiacciono) {
        this.miPiacciono = miPiacciono;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCogonome() {
        return cogonome;
    }

    public void setCogonome(String cogonome) {
        this.cogonome = cogonome;
    }

    public String getFotoProfilo() {
        return fotoProfilo;
    }

    public void setFotoProfilo(String fotoProfilo) {
        this.fotoProfilo = fotoProfilo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
