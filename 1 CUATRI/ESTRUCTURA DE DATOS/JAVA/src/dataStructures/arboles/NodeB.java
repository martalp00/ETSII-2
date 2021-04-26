package arboles;

import dataStructures.list.ArrayList;
import dataStructures.list.List;
import dataStructures.tuple.Tuple2;
import dataStructures.tuple.Tuple3;

import javax.print.DocFlavor;

public class NodeB<T> {
    NodeB<T> lt;
    NodeB<T> rt;
    T elem;

    public NodeB(NodeB<T> i, T v, NodeB<T> d) {
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

    // Suma de los vlaores de un árbol de enteros
    public static int suma(NodeB<Integer> ar) {
        int sum = 0;
        if (ar != null) {
            sum = ar.elem + suma(ar.lt) + suma(ar.rt);
        }
        return sum;
    }

    // Profundidad de un árbol
    public static <S> int prof(NodeB<S> ar) {
        int pr = 0;
        if (ar != null) {
            pr = 1 + Math.max(prof(ar.lt), prof(ar.rt));
        }
        return pr;
    }

    // Devuelve un árbol donde en cada nodo hay un par, el valor
    // y la profundidad del nodo en el árbol
    public static <S> NodeB<Tuple2<S,Integer>> anotaProf(NodeB<S> ar) {
        NodeB<Tuple2<S,Integer>> res = null;
        if (ar != null) {
            NodeB<Tuple2<S,Integer>> alt = anotaProf(ar.lt);
            NodeB<Tuple2<S,Integer>> art = anotaProf(ar.rt);
            res = new NodeB<>(alt,
                                new Tuple2<>(ar.elem,
                                            1+Math.max(verProf(alt), verProf(art))),
                              art);
        }
        return res;
    }

    private static <S> int verProf(NodeB<Tuple2<S,Integer>> ar) {
        return (ar== null) ? 0: ar.elem._2();
    }
    
    // Devuelve un árbol con una tripleta. La primera componente es el valor
    // La segunda es la suma de los nodos de cada subárbol
    // y la tercera la profundidad de cada nodo
    public static NodeB<Tuple3<Integer,Integer,Integer>> anotaSumProf(NodeB<Integer> ar) {
        NodeB<Tuple3<Integer,Integer,Integer>> res = null;
        if (ar != null){
            NodeB<Tuple3<Integer,Integer,Integer>> alt = anotaSumProf(ar.lt);
            NodeB<Tuple3<Integer,Integer,Integer>> art = anotaSumProf(ar.rt);
            res = new NodeB<>(alt,
                            new Tuple3<>( ar.elem,
                                         ar.elem + verSum3(alt) + verSum3(art),
                                            1 + Math.max (verProf3(alt), verProf3(art))
                                        )
                            ,art) ;

        }
        return res;
    }

    private static int verSum3(NodeB<Tuple3<Integer,Integer,Integer>> ar) {
        return ar == null ? 0 : ar.elem._2();
    }

    private static int verProf3(NodeB<Tuple3<Integer,Integer, Integer>> ar) {
        return ar == null? 0 : ar.elem._3();
    }

    // Devuelve una lista con los valores situados en un nivel dado
    public static <S> List<S> atLevel(int n, NodeB<S> ar) {
        List<S> res = new ArrayList<>();
        if (ar != null) {
            if (n == 0) {
                res.append(ar.elem);
            } else {
                List<S> llt = atLevel(n-1, ar.lt);
                List<S> lrt = atLevel(n-1, ar.rt);
                for (S s: llt) {
                    res.append(s);
                }
                for(S s: lrt) {
                    res.append(s);
                }
            }
        }
        return res;
    }

    // Devuuelve una lista de listas con los paths hasta un valor dado
    public static <S> List<List<S>> pathsToB(S x, NodeB<S> ar) {
        List<List<S>> res = new ArrayList<>();
        if (ar != null) {
            List<List<S>> llt = pathsToB(x, ar.lt);
            List<List<S>> lrt = pathsToB(x, ar.rt);
            for (List<S> l : llt) {
                l.prepend(ar.elem);
                res.append(l);
            }
            for(List<S> l : lrt) {
                l.prepend(ar.elem);
                res.append(l);
            }
            if (x.equals(ar.elem)) {
                List<S> p = new ArrayList<>();
                p.append(ar.elem);
                res.prepend(p);
            }
        }
        return res;
    }

    // Devuelve una lista con el recorrido en preorden
    public static <S> List<S> preOrderB(NodeB<S> ar) {
        List<S> res = new ArrayList<>();
        if (ar !=  null) {
            List<S> llt = preOrderB(ar.lt);
            List<S> lrt = preOrderB(ar.rt);
            res.append(ar.elem);
            for (S s: llt) {
                res.append(s);
            }

            for(S s: lrt) {
                res.append(s);
            }
        }
        return res;
    }

    // Devuelve una lista con el recorrido en inorden
    public static <S> List<S> inOrderB(NodeB<S> ar) {
        // COMPLETAR
        return null;
    }

    // Devuelve una lista con el recorrido en postorden
    public static <S> List<S> postOrderB(NodeB<S> ar) {
        // COMPLETAR
        return null;
    }
    
    
}

/*
PARCIAL

2. columnasAM
igual que 1 pero de forma no recursiva

3. anota
devuelve un arbol de tuplas pero en vez de a tiene que poner la tupla (a,3,1) (como las que se calculan en columnas) y asi con cada nodo, en los hijos pues (c,0,0)

4. pos cuadro
devuelve un arbol de tuplas que se define(valor del nodo, fila donde se pinta el nodo, columna donde se pinta) = (a,0,3)

5.longFlecha
devuelve un arbol de tuplas definidas como tuple3(valor del nodo, longitud en horizontal en cuadriculas que debe de tener la flecha izq que sale de ese nodo hasta su hijo izq, flecha pero pa el hijo derechp), ejemplo (a,2,1)

6. ¿cuál sera el valor del nodo raiz del arbol resultante de ejecutar la siguiente expresion?
posCuadro(anota(longFlecha(anota(ejemplo))));
ejemplo es el arbol inicial.


 */