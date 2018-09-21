package com.example.lam_m.laboratorio1;

import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

public class Huffman {

    ArrayList<Nodo> nodos;
    ArrayList<Simbolo> letra_prob;
    String textoOriginal;
    String noFinal;
    String arbolComprimido="";
    String toPrint;
    String finite="";
    String desc ="";
    Huffman(String texto){

        nodos = new ArrayList<>();
        ArrayDeSimbolos as = new ArrayDeSimbolos(texto);
        letra_prob = new ArrayList<>();

        ArrayList<Simbolo> letra_probabilidad = as.getArray();
        textoOriginal = texto;
        letra_prob = letra_probabilidad;

        for (int i =0;i<letra_probabilidad.size();i++){
            if (i==0){
                //Iniciando
                iniciar(letra_probabilidad.get(0),letra_probabilidad.get(1));
                i=1;
            }else {
                if (letra_probabilidad.size()-i>2)
                {
                    if (letra_probabilidad.get(i).getFrecuencia() + letra_probabilidad.get(i+1).getFrecuencia() <= nodos.get(0).getProbabilidad()) {
                        //Se crea nuevo subarbol cuando las proximas dos frecuencais
                        //Son menores o iguales a la primera frecuencia en nodos
                        Nodo hijoIzq = new Nodo(letra_probabilidad.get(i));
                        Nodo hijoDer = new Nodo(letra_probabilidad.get(i + 1));
                        Nodo nuevoNodo = new Nodo(hijoIzq, hijoDer);
                        nuevoNodo.hijoIzquierdo.padre = nuevoNodo;
                        nuevoNodo.hijoDerecho.padre = nuevoNodo;
                        nodos.add(nuevoNodo);
                        Collections.sort(nodos);
                        i = i + 1; //Matematicas hijo!!
                    }
                    else {
                        //Se agregan al primer nodo del listado
                        Nodo nodoProbabilidad = nodos.get(0);
                        Nodo nuevoNodo = new Nodo(letra_probabilidad.get(i));
                        Nodo padre = new Nodo(nodoProbabilidad,nuevoNodo);
                        padre.hijoIzquierdo.padre = padre;
                        padre.hijoDerecho.padre = padre;
                        nodos.set(0,padre);
                        Collections.sort(nodos);
                    }
                }else {
                    //Se agregan al primer nodo del listado
                    Nodo nodoProbabilidad = nodos.get(0);
                    Nodo nuevoNodo = new Nodo(letra_probabilidad.get(i));
                    Nodo padre = new Nodo(nodoProbabilidad,nuevoNodo);
                    padre.hijoIzquierdo.padre = padre;
                    padre.hijoDerecho.padre = padre;
                    nodos.set(0,padre);
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
            padre.hijoIzquierdo.padre=padre;
            padre.hijoDerecho.padre=padre;

            nodos.add(padre);
        }
        //Asignar codigos y almacenarlo en String
        codigoGenerado(nodos.get(0));
        String txt = cifrado();

        comprimirArbol(nodos.get(0),1);
        txt = txt+"\r\n"+getArbolComprimido();
        finite =txt;
        toPrint =txt;
        //Almacecnar datos en archivo
    }
    public  String metodoFinal()
    {
        return finite;
    }
    public  String cifrado1()
    {
        return  toPrint;
    }
    private void iniciar(Simbolo simbolo1, Simbolo simbolo2){
        Nodo nodoIzq = new Nodo(simbolo1);
        Nodo nodoDer = new Nodo(simbolo2);
        Nodo padre = new Nodo(nodoIzq,nodoDer);
        padre.hijoIzquierdo.padre =padre;
        padre.hijoDerecho.padre = padre;
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
    public void comprimirArbol(Nodo nodo, int i){
        if (nodo.simbolo == null){
            arbolComprimido = arbolComprimido + "|" + i + ",nu";

        }else{
            arbolComprimido = arbolComprimido + "|"+i+","+ nodo.simbolo.getLetra();
        }
        if (nodo.hijoIzquierdo != null){
            comprimirArbol(nodo.hijoIzquierdo,i*2);
        }
        if (nodo.hijoDerecho != null){
            comprimirArbol(nodo.hijoDerecho,i*2 +1);
        }
    }
    public String getArbolComprimido(){
        return arbolComprimido;
    }
    public void descromprimir(String texto){

        ArrayList<String> s = new ArrayList();
        Map<Integer,Character> tree = new HashMap<Integer,Character>();
        letra_prob = new ArrayList<>();

        int index = texto.indexOf('|');
        String codigoBinario = texto.substring(0,index);
        codigoBinario = codigoBinario.trim();
        String arbolito = texto.substring(index,texto.length());

        String[] arrOfBry = codigoBinario.split("\\ ");
        String[] arrOfStr = arbolito.split("\\|" );

        for (int i = 1; i< arrOfStr.length; i++){
            String[] letra = arrOfStr[i].split(",");
            int asd = Integer.valueOf(letra[0]);
            String Letra = letra[1];
            char fletra;
            if (Letra.equals("nu")) {
                fletra = '^';
            } else {
                fletra = Letra.charAt(0);
            }
            tree.put(asd, fletra);
        }

        //Empezando a descomprimir
        for (HashMap.Entry<Integer, Character> nodo: tree.entrySet() ) {
            int i = nodo.getKey();
            int aux = 0;
            noFinal = "";
            String codi = "";
            while (i>1){
                //Encontrar el codigo
                if (i%2 == 0){
                    codi = codi + "0";
                }else{
                    codi = codi + "1";
                }
                i = i/2;
            }
            //Darle la vuelta al coidgo y asignarlo
           for (int e = codi.length() - 1; 0<=e; e--){
                noFinal = noFinal + codi.charAt(e);
            }
            letra_prob.add(new Simbolo(nodo.getValue(),noFinal));
        }

        desc ="";
        //Ahora para cada conjunto de numeros, encontrar el match y nos vamos
        for (int v =0;v<arrOfBry.length;v++){
            for (Simbolo item: letra_prob) {
                if ((arrOfBry[v]).equals(item.getCodigo())){
                    desc = desc + item.getLetra();

                }
            }

        }
        String qweq = "";
        qweq = desc;
    }
    public  String decompress()
    {
        return  desc;
    }
    public String textoDescomprimido(){
        return finite;
    }
    private void codigoGenerado(Nodo nodo) {
        if (nodo.hijoIzquierdo!= null){
            if (nodo.hijoIzquierdo.simbolo != null){
                getCodigoBinario(nodo.hijoIzquierdo, "",nodo.hijoIzquierdo.simbolo);
            }
            codigoGenerado(nodo.hijoIzquierdo);
        }if (nodo.hijoDerecho != null){
            if (nodo.hijoDerecho.simbolo != null){
                getCodigoBinario(nodo.hijoDerecho, "",nodo.hijoDerecho.simbolo);
            }
            codigoGenerado(nodo.hijoDerecho);
        }
    }
    private void getCodigoBinario(Nodo nodo, String s, Simbolo simba){
        if (nodo.padre == null){
            noFinal="";
            for (int i = s.length() - 1; 0<=i; i--){
                noFinal = noFinal + s.charAt(i);
            }
            Simbolo q = letra_prob.get(letra_prob.indexOf(simba));
            q.setCodigo(noFinal);
            letra_prob.set(letra_prob.indexOf(simba),q);
            noFinal = "";
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