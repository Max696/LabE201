/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.lam_m.laboratorio1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author lam_m
 */
public class Help {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String resultado = "";
        int z = 1;
        Map<Integer, String> map = new TreeMap<>();
        String xD = "Puto el que lo lea";
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
       String textoComprimido = "";
        for (int i = 0; i < xD.length(); i++) {
            String s = Character.toString(xD.charAt(i));
            int value = 0;
            if(map.containsValue(s)){
                while(map.containsValue(s)){
                    if(i+contador+1 < xD.length()){
                        contador++;
                        s = s + xD.charAt(i+contador); 
                    }else{
                        //Debería ser el fin del string o terminando
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
        
        textoComprimido = textoComprimido + "\n" + a.toString();
        System.out.println(xD);
        System.out.println(map.keySet());
        System.out.println(map.values());
        System.out.println(textoComprimido);
        
        //Iniciando la descompresion
        Map<Integer, String> khe = new HashMap<>();
        int cont = 1;
        String qwerty = textoComprimido.substring(0, textoComprimido.indexOf("\n"));
        String arbolinho = textoComprimido.substring(textoComprimido.indexOf("\n"), textoComprimido.length());
        arbolinho = arbolinho.substring(2, arbolinho.length()-1);
        String[] hellow = arbolinho.split("\\, ");
        for (int i = 0; i < hellow.length; i++) {
            khe.put(cont, hellow[i]);
            cont++;
        }
        String tD = "";
        String auxi = "";
        int uP = 1;
        
        for (int i = 1; i < qwerty.length(); i++) {
            if (i == 1) {
                int p = Integer.valueOf(Character.toString(qwerty.charAt(1-1)));
                tD = khe.get(p) + khe.get(Integer.valueOf(Character.toString(qwerty.charAt(i))));
                khe.put(cont, tD);
                cont++;
                auxi = Character.toString(tD.charAt(tD.length() - 1));
            }else{
                //Se biene lo chido bro xDxD
                if(Character.toString(qwerty.charAt(i)).equals("(")){
                    //Debemos tomar dos numeros
                    String s = qwerty.substring(i+1, i+3); //Obtenemos el numero
                    tD = tD + khe.get(Integer.valueOf(s)); //Descomprimimos
                    i = i + 3; //Validamos 
                }else{
                    //Se trata de un numero
                    int p = Integer.valueOf(Character.toString(qwerty.charAt(i)));//Obtenemos el numero
                    tD = tD + khe.get(p);//Descomprimimos
                }
                uP++;
                auxi = auxi + tD.charAt(uP);
                while(khe.containsValue(auxi)){
                    uP++;
                    auxi = auxi + tD.charAt(uP);
                }
                khe.put(cont,auxi);//Añadimos al mapa
                cont++;
                auxi = Character.toString(auxi.charAt(auxi.length()-1));//Tomamos la ultima letra
            }
        }
        System.out.println(tD);
        System.out.println(Integer.toBinaryString(10));
    }
}
