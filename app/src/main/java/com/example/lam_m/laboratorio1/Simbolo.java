package com.example.lam_m.laboratorio1;

public class Simbolo {
    private char letra;
    private double frecuencia;
    private int codigo;

    Simbolo(char letra, double frecuencia){
        this.letra = letra;
        this.frecuencia = frecuencia;
    }

    public char getLetra() {
        return letra;
    }

    public double getFrecuencia() {
        return frecuencia;
    }

    public void setCodigo(int codigo){
        this.codigo = codigo;
    }
    public int getCodigo(){
        return this.codigo;
    }
}
