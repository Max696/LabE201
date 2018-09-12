package com.example.lam_m.laboratorio1;

public class Nodo {

    private Nodo hijoIzquierdo;
    private Nodo hijoDerecho;
    private int codigo;
    private double probabilidad;
    private Simbolo simbolo;

    Nodo(Nodo hijoIzquierdo, Nodo hijoDerecho, Simbolo simbolo){
        this.hijoIzquierdo = hijoIzquierdo;
        this.hijoDerecho = hijoDerecho;
        this.probabilidad = hijoIzquierdo.probabilidad + hijoDerecho.probabilidad;
        this.simbolo = simbolo;
    }

    Nodo(Simbolo simbolo){
        this.simbolo = simbolo;
        this.probabilidad = simbolo.probablidad;
        this.hijoIzquierdo = null;
        this.hijoDerecho = null;
    }

}
