package pintaArbol;

import dataStructures.tuple.Tuple3;

import java.util.ArrayList;
import java.util.List;

public class NodeB<T>
{
    NodeB<T> lt;
    NodeB<T> rt;
    T elem;

    public NodeB(NodeB<T> i, T v, NodeB<T> d)
    {
        lt = i;
        elem = v;
        rt = d;
    }

    public NodeB(T v) {
        this(null, v, null);
    }

    @Override
    public String toString() {
        String si =  (lt != null)? lt.toString():"EmptyB";
        String sd =  (rt != null)? rt.toString():"EmptyB";
        return "NodeB(" + si +", " + elem + ", " + sd + ")";
    }

// 1 pto.
    public static <S> int columnas(NodeB<S> ar)
    {
        int col = 0;
        if(ar.elem != null)
        {
            col++;
            col+=columnas(ar.lt);
            col+=columnas(ar.rt);
        }
        return col;
    }
/*
    // Devuelve una lista con el recorrido
    private static <S> void recorrer(NodeB<S> ar)
    {
        if(ar != null)
        {
            nelem += 1;
            recorrer(ar.lt);
            recorrer(ar.rt);
        }
    }
*/

// 1 pto.
    public static <S> int columnasAM(NodeB<Tuple3<S,Integer, Integer>> ar)
    {
        //int n = 0, ci = 0, cd = 0;
        //Tuple3<Integer, Integer, Integer> tupla = new Tuple3<Integer, Integer, Integer>(n, ci, cd);


        return (ar.elem._1()!=null)?1+ar.elem._2()+ar.elem._3():0;
    }

// 2 ptos.
    public static <S> NodeB<Tuple3<S,Integer, Integer>> anota(NodeB<S> ar) {
        //  COMPLETAR
        return null;
    }

// 3 ptos.
    public static <S> NodeB<Tuple3<S,Integer,Integer>> posCuadro(NodeB<Tuple3<S,Integer, Integer>> ar) {
        return posCuadrop(ar,0,0);
    }

    private static <S> NodeB<Tuple3<S,Integer,Integer>> posCuadrop(NodeB<Tuple3<S,Integer, Integer>> ar, int f, int c) {
        // COMPLETAR
        return null;
    }

// 2 ptos.
    public static <S> NodeB<Tuple3<S,Integer, Integer>> longFlecha(NodeB<Tuple3<S,Integer, Integer>> ar) {
        // COMPLETAR
        return null;
    }

// 1  pto.
//  cuál es el valor del nodo raíz en la expresión
//  posCuadro(anota(longFlecha(anota(ejemplo)))) ?

}

