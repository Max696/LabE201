package com.example.lam_m.laboratorio1;

import android.support.annotation.NonNull;

public class Nodo implements Comparable<Nodo>{

    private Nodo hijoIzquierdo;
    private Nodo hijoDerecho;
    private double probabilidad;
    private Simbolo simbolo;

    Nodo(Nodo hijoIzquierdo, Nodo hijoDerecho){
        this.hijoIzquierdo = hijoIzquierdo;
        this.hijoDerecho = hijoDerecho;
        this.probabilidad = hijoIzquierdo.probabilidad + hijoDerecho.probabilidad;
        this.simbolo = null;
    }

    Nodo(Simbolo simbolo){
        this.simbolo = simbolo;
        this.probabilidad = simbolo.getFrecuencia();
        this.hijoIzquierdo = null;
        this.hijoDerecho = null;
    }

    public double getProbabilidad(){
        return this.probabilidad;
    }

    @Override
    public int compareTo(@NonNull Nodo o) {
        if(this.probabilidad < o.probabilidad) {
            return -1;
        } else if (o.probabilidad < this.probabilidad) {
            return 1;
        } else {
            if (this.simbolo.getLetra() < o.simbolo.getLetra()) {
                return -1;
            } else {
                return 1;
            }
        }
    }
}
