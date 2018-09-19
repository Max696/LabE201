package com.example.lam_m.laboratorio1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

public class Huffman {

    ArrayList<Nodo> nodos;
    ArrayList<Simbolo> letra_prob;
    String textoOriginal;

    Huffman( String texto){
        ArrayList<Simbolo> letra_probabilidad = new ArrayList<>();
        nodos = new ArrayList<>();
        textoOriginal = texto;

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
                    nodos.add(nuevoNodo);
                    Collections.sort(nodos);
                    i = i+2; //Matematicas hijo!!
                }else {
                    //Se agregan al primer nodo del listado
                    Nodo nodoProbabilidad = nodos.get(0);
                    Nodo nuevoNodo = new Nodo(letra_probabilidad.get(i));
                    Nodo padre = new Nodo(nodoProbabilidad,nuevoNodo);
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
        //Recorrer el arbol y asignar codigos


    }

    private void iniciar(Simbolo simbolo1, Simbolo simbolo2){
        Nodo nodoIzq = new Nodo(simbolo1);
        Nodo nodoDer = new Nodo(simbolo2);
        Nodo padre = new Nodo(nodoIzq,nodoDer);
        nodos.add(padre);
        Collections.sort(nodos);
    }

    public void cifrado(){
        String txtCifrado = "";
        for (int i =0;i<textoOriginal.length();i++){
            char c = textoOriginal.charAt(i);
            for (Simbolo item: letra_prob) {
                if (item.getLetra() == c){
                    txtCifrado = txtCifrado + item.getCodigo() + " ";
                }
            }
        }

        //Guardar en un archivo .huff texto y arbol

    }

    public void descromprimir(){


    }
}