package com.example.lam_m.laboratorio1;

import android.support.annotation.NonNull;

public class Simbolo implements Comparable<Simbolo>{
    public char letra;
    public double probablidad;

    Simbolo(char letra, double probablidad){
        this.letra = letra;
        this.probablidad = probablidad;
    }

    @Override
    public int compareTo(Simbolo o) {
        if(this.probablidad < o.probablidad)
        {
            return -1;
        }
        else if (o.probablidad < this.probablidad)
        {
            return 1;
        }
        else{
            if (this.letra < o.letra){
                return -1;
            }
            else{
                return 1;
            }
        }
    }
}
