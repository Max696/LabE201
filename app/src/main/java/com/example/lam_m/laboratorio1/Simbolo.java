package com.example.lam_m.laboratorio1;

public class Simbolo {
    private char letra;
    private double frecuencia;
    private String codigo;

    Simbolo(char letra, double frecuencia){
        this.letra = letra;
        this.frecuencia = frecuencia;
    }

    Simbolo(char letra, String codigo){
        this.letra = letra;
        this.codigo = codigo;
    }

    public char getLetra() {
        return letra;
    }

    public double getFrecuencia() {
        return frecuencia;
    }

    public void setCodigo(String codigo){
        this.codigo = codigo;
    }
    public String getCodigo(){
        return this.codigo;
    }
}
