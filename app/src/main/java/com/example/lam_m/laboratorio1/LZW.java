package com.example.lam_m.laboratorio1;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class LZW {
    String textoComprimido;

    LZW(String xD){

        String resultado = "";
        int z = 1;
        Map<Integer, String> map = new TreeMap<>();

        List<String> a = new ArrayList();

        for (int i = 0; i < xD.length(); i++) {
            char q = xD.charAt(i);
            String s = Character.toString(q);
            if(a.isEmpty()){
                a.add(s);
            }else{
                if(!a.contains(s)){
                    a.add(s);
                }
            }
        }
        Collections.sort(a);
        for (int i = 0; i < a.size(); i++) {
            map.put(z,a.get(i));
            z++;
        }
        int contador = 0;
        Boolean aux = false;
        textoComprimido = "";
        for (int i = 0; i < xD.length(); i++) {
            String s = Character.toString(xD.charAt(i));
            int value = 0;
            if(map.containsValue(s)){
                while(map.containsValue(s)){
                    if(i+contador+1 < xD.length()){
                        contador++;
                        s = s + xD.charAt(i+contador);
                    }else{
                        //DeberÃ­a ser el fin del string o terminando
                        aux = true;
                        break;
                    }
                }
                if(aux){
                    //Aqui va el codigo en caso de que le falten letras
                    for(Map.Entry<Integer, String> entry : map.entrySet()){
                        if(entry.getValue().equals(s)){
                            if(entry.getKey().longValue() > 9){
                                //Dejar espacio
                                textoComprimido = textoComprimido +  "(" + entry.getKey() + ")";
                            }else{
                                textoComprimido = textoComprimido + entry.getKey();
                            }
                        }
                    }
                    break;
                }

                for(Map.Entry<Integer, String> entry : map.entrySet()){
                    if(entry.getValue().equals(s.substring(0,s.length()-1))){
                        if(entry.getKey().longValue() > 9){
                            //Dejar espacio
                            textoComprimido = textoComprimido +  "(" + entry.getKey() + ")";
                        }else{
                            textoComprimido = textoComprimido + entry.getKey();
                        }
                    }
                }
                map.put(z, s);
                //Se debe ver cuantas letras se comio
                if(contador > 1){
                    i = i + contador - 1;
                }
                contador = 0;
                z++;
            }
        }
        String prueba1 = toComprimir(textoComprimido);//texto comprimido
        String arbolCOMP = treeCompress(a.toString());//Quedan numeros, se convierten a ascii
        arbolCOMP = toComprimir(arbolCOMP);//Arbol comprimido
        textoComprimido = prueba1 + "◘" + arbolCOMP;//archivo final
    }
    public String deCompressArchivo(String textoComprimido){

        Map<Integer, String> khe = new HashMap<>();
        int cont = 1;
        String qwerty = textoComprimido.substring(0, textoComprimido.indexOf("◘"));
        String arbolinho = textoComprimido.substring(textoComprimido.indexOf("◘") + 1, textoComprimido.length());
        String prueba2 = deCompress(qwerty);
        String arbolDescomprimido = treeDecompress(arbolinho);

        arbolinho = arbolDescomprimido.substring(1, arbolDescomprimido.length()-1);
        String[] hellow = arbolinho.split("\\, ");

        for (String hellow1 : hellow) {
            khe.put(cont, hellow1);
            cont++;
        }

        String tD = "";
        String auxi = "";
        int uP = 1;
        int contador = 2;

        for (int i = 1; i < prueba2.length(); i++) {
            if (i == 1) {
                if(Character.toString(prueba2.charAt(1-1)).equals("(")){//Cuando el primer caracter se trata de un parentesis
                    while(prueba2.charAt(i+contador) != ')'){
                        contador++;
                    }
                    String s = prueba2.substring(i, i+contador); //Obtenemos el numero
                    tD = khe.get(Integer.valueOf(s)); //Descomprimimos
                    i = i + contador + 1; //Validamos
                    contador = 2; //Reseteamos variable

                }else{//Cuando no es un parentesis
                    int p = Integer.valueOf(Character.toString(prueba2.charAt(1-1)));
                    tD = khe.get(p);
                }
                if(Character.toString(prueba2.charAt(i)).equals("(")){//Cuando el segundo numero es mayor a 9
                    while(prueba2.charAt(i+contador) != ')'){
                        contador++;
                    }
                    String s = prueba2.substring(i+1, i+contador); //Obtenemos el numero
                    tD = tD + khe.get(Integer.valueOf(s)); //Descomprimimos
                    i = i + contador; //Validamos
                    contador = 2; //Reseteamos variable
                }else{//Cuando es un numero de 0-9
                    tD = tD + khe.get(Integer.valueOf(Character.toString(prueba2.charAt(i))));
                }
                khe.put(cont, tD);
                cont++;
                auxi = Character.toString(tD.charAt(tD.length() - 1));
            }else{
                //Se biene lo chido bro xDxD
                if(Character.toString(prueba2.charAt(i)).equals("(")){
                    //Debemos tomar N numeros
                    while(prueba2.charAt(i+contador) != ')'){
                        contador++;
                    }

                    String s = prueba2.substring(i+1, i+contador); //Obtenemos el numero
                    tD = tD + khe.get(Integer.valueOf(s)); //Descomprimimos
                    i = i + contador; //Validamos
                    contador = 2; //Reseteamos variable
                }else{
                    //Se trata de un numero
                    int p = Integer.valueOf(Character.toString(prueba2.charAt(i)));//Obtenemos el numero
                    tD = tD + khe.get(p);//Descomprimimos
                }
                uP++;
                auxi = auxi + tD.charAt(uP);
                while(khe.containsValue(auxi)){
                    uP++;
                    auxi = auxi + tD.charAt(uP);
                }
                khe.put(cont,auxi);//AÃ±adimos al mapa
                cont++;
                auxi = Character.toString(auxi.charAt(auxi.length()-1));//Tomamos la ultima letra
            }
        }
        return tD;
    }
    public String compre(){
        return textoComprimido;
    }
    private String treeCompress(String texto) {
        String resultante = "";
        for (int i = 0; i < texto.length(); i++) {
            char character = texto.charAt(i);
            int ascii = (int)character;
            resultante = resultante + ascii;
        }
        resultante = resultante.substring(2, resultante.length()-2);
        //String[]valores =
        return resultante;
    }

    private String treeDecompress(String arbol) {
        String resultante = "";
        for (int i = 0; i < arbol.length(); i++) {
            char character = arbol.charAt(i); // This gives the character 'a'
            int ascii = (int) character;
            resultante = resultante + ascii;
        }
        String[] valores = resultante.split("4432");
        resultante = "";
        for (int i = 0; i < valores.length; i++) {

            int numero = Integer.valueOf(valores[i]);
            char c = (char)numero;
            if(i ==0){//Se debe crear desde cero xD
                resultante = "[" + Character.toString(c) + ",";
            }else if(i == valores.length-1){//Se trata del final
                resultante = resultante + " " + Character.toString(c) + "]";
            }else{//Letras enmedio
                resultante = resultante + " " + Character.toString(c) + ",";
            }
        }
        return resultante;
    }

    private String toComprimir(String textoComprimido){
        String compre = "";
        int numero = 0;
        int contador = 2;
        for (int i = 0; i < textoComprimido.length(); i++) {
            if(Character.toString(textoComprimido.charAt(i)).equals("(")){
                while(textoComprimido.charAt(i+contador) != ')'){
                    contador++;
                }
                numero = Integer.valueOf(textoComprimido.substring(i+1, i+contador));
                i = i+contador;
            }else{
                numero = Integer.valueOf(Character.toString(textoComprimido.charAt(i)));
            }
            compre = compre + ((char)numero);
            contador = 2;
        }
        return compre;
    }

    private String deCompress(String textoComprimido){
        String textoOriginal = "";
        for (int i = 0; i < textoComprimido.length(); i++) {
            int valor = (int)textoComprimido.charAt(i);
            if(valor > 9){
                textoOriginal = textoOriginal + "(" + valor + ")";
            }else{
                textoOriginal = textoOriginal + valor;
            }
        }
        return textoOriginal;
    }


}
