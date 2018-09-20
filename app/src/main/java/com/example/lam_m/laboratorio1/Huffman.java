package com.example.lam_m.laboratorio1;

import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

public class Huffman {

    ArrayList<Nodo> nodos;
    ArrayList<Simbolo> letra_prob;
    String textoOriginal;
    String noFinal;


    Huffman( String texto){


        nodos = new ArrayList<>();
        textoOriginal = texto;
        ArrayDeSimbolos as = new ArrayDeSimbolos(texto);
        ArrayList<Simbolo> letra_probabilidad = as.getArray();
        for (int i =0;i<letra_probabilidad.size();i++){
            if (i==0){
                //Iniciando

                iniciar(letra_probabilidad.get(0),letra_probabilidad.get(1));
                i=1;
            }else {
                if (letra_probabilidad.get(i).getFrecuencia() + letra_probabilidad.get(i+1).getFrecuencia() <= nodos.get(0).getProbabilidad()){
                    //Se crea nuevo subarbol cuando las proximas dos frecuencais
                    //Son menores o iguales a la primera frecuencia en nodos
                    Nodo hijoIzq = new Nodo(letra_probabilidad.get(i));
                    Nodo hijoDer = new Nodo(letra_probabilidad.get(i+1));
                    Nodo nuevoNodo = new Nodo(hijoIzq,hijoDer);
                    nuevoNodo.hijoIzquierdo.padre = nuevoNodo;
                    nuevoNodo.hijoDerecho.padre = nuevoNodo;
                    nodos.add(nuevoNodo);
                    Collections.sort(nodos);
                    i = i+2; //Matematicas hijo!!
                }else {
                    //Se agregan al primer nodo del listado
                    Nodo nodoProbabilidad = nodos.get(0);
                    Nodo nuevoNodo = new Nodo(letra_probabilidad.get(i));
                    Nodo padre = new Nodo(nodoProbabilidad,nuevoNodo);
                    padre.hijoIzquierdo.padre = padre;
                    padre.hijoDerecho.padre = padre;
                    nodos.add(padre);
                    Collections.sort(nodos);
                }
            }
        }

        //Unir los subarboles
        while (nodos.size() > 1){
            Nodo primer = nodos.get(0);
            Nodo segundo = nodos.get(1);
            Nodo padre = new Nodo(primer,segundo);

            nodos.remove(0);
            nodos.remove(0);

            nodos.add(padre);
        }
        //Asignar codigos y almacenarlo en String
        codigoGenerado(nodos.get(0));
        String txt = cifrado();

        //Almacecnar datos en archivo
    }

    private void iniciar(Simbolo simbolo1, Simbolo simbolo2){
        Nodo nodoIzq = new Nodo(simbolo1);
        Nodo nodoDer = new Nodo(simbolo2);
        Nodo padre = new Nodo(nodoIzq,nodoDer);
        nodos.add(padre);
        Collections.sort(nodos);
    }

    public String cifrado(){
        String txtCifrado = "";
        for (int i =0;i<textoOriginal.length();i++){
            char c = textoOriginal.charAt(i);
            for (Simbolo item: letra_prob) {
                if (item.getLetra() == c){
                    txtCifrado = txtCifrado + item.getCodigo() + " ";
                }
            }
        }
        return txtCifrado;
    }

    public void descromprimir(){


    }

    private void codigoGenerado(Nodo nodo) {
        if (nodo.hijoIzquierdo!= null){
            if (nodo.hijoIzquierdo.simbolo != null){
                getCodigoBinario(nodo.hijoIzquierdo, "",nodo.hijoIzquierdo.simbolo);
            }
            codigoGenerado(nodo.hijoIzquierdo);
        }else if (nodo.hijoDerecho != null){
            if (nodo.hijoDerecho.simbolo != null){
                getCodigoBinario(nodo.hijoDerecho, "",nodo.hijoDerecho.simbolo);
            }
            codigoGenerado(nodo.hijoDerecho);
        }
    }

    private void getCodigoBinario(Nodo nodo, String s, Simbolo simba){
        if (nodo.padre == null){
            if (nodo.padre.hijoIzquierdo == nodo){
                s = s+"0";
                for (int i = s.length() - 1; 0 >= i; i--){
                    noFinal = noFinal + s.charAt(i);
                }
                letra_prob.get(letra_prob.indexOf(simba)).setCodigo(noFinal);
                noFinal = "";
            }else{
                s = s+"1";
                for (int i = s.length() - 1; 0 >= i; i--){
                    noFinal = noFinal + s.charAt(i);
                }
                letra_prob.get(letra_prob.indexOf(simba)).setCodigo(noFinal);
                noFinal = "";
            }
        }else{
            if (nodo.padre.hijoIzquierdo == nodo){
                getCodigoBinario(nodo.padre,s + "0",simba);
                //0
            }else{
                //1
                getCodigoBinario(nodo.padre,s + "1",simba);
            }
        }

    }
}