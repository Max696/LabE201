package com.example.lam_m.laboratorio1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ArrayDeSimbolos {

    private ArrayList<Simbolo> resultante;


    ArrayDeSimbolos(String texto){

        String as="";
        //repetido rp= new repetido(0,'l');
        Map<Character,Integer> m1 = new HashMap<Character,Integer>();

        for (char qw: texto.toCharArray())
        {
            if(m1.containsKey(qw))
            {
                Integer previousValue = m1.get(qw);
                m1.put(qw,previousValue+1);

            }
            else
            {
                m1.put(qw, 1);
                as+=qw;
            }

        }


        Map<Character,Integer> treemap = sortByValue(m1);

        ArrayList<Simbolo> ty=new ArrayList<>();
        for (Map.Entry<Character , Integer> entry : treemap.entrySet()) {
            ty.add(new Simbolo(entry.getKey(),entry.getValue()));
        }
        resultante=ty;

    }
    public ArrayList getArray()
    {
        return  resultante;

    }
    private static Map<Character, Integer> sortByValue(Map<Character, Integer> unsortMap) {


        List<Map.Entry<Character, Integer>> list =
                new LinkedList<Map.Entry<Character, Integer>>(unsortMap.entrySet());


        Collections.sort(list, new Comparator<Map.Entry<Character, Integer>>() {
            public int compare(Map.Entry<Character, Integer> o1,
                               Map.Entry<Character, Integer> o2) {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });


        Map<Character, Integer> sortedMap = new LinkedHashMap<Character, Integer>();
        for (Map.Entry<Character, Integer> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }



        return sortedMap;
    }
}
