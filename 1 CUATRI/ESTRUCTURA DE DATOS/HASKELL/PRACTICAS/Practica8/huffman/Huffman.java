package huffman;
/**
 * Huffman trees and codes.
 *
 * Data Structures, Grado en Informatica. UMA.
 *
 *
 * Student's name: Marta Lopez Perez
 * Student's group: 2ºA
 */

import dataStructures.dictionary.AVLDictionary;
import dataStructures.dictionary.Dictionary;
import dataStructures.list.LinkedList;
import dataStructures.list.List;
import dataStructures.priorityQueue.BinaryHeapPriorityQueue;
import dataStructures.priorityQueue.PriorityQueue;
import dataStructures.tuple.Tuple2;

public class Huffman 
{
    // Exercise 1  
    public static Dictionary<Character, Integer> weights(String s) 
	{
    	Dictionary<Character, Integer> dict = new AVLDictionary<>();
        for(char c : s.toCharArray())
        {
            if(dict.isDefinedAt(c))
            {
                dict.insert(c,dict.valueOf(c) + 1);
            }
            else
            {
                dict.insert(c,1);
            }
        }

        if(dict.size() == 1)
        {
            throw new HuffmanException("El mensaje tiene que tener  mínimo dos 2 caracteres distintos");
        }
        return dict;
    }

    // Exercise 2.a 
    public static PriorityQueue<WLeafTree<Character>> huffmanLeaves(String s) 
	{
    	PriorityQueue<WLeafTree<Character>> pq = new BinaryHeapPriorityQueue<>();
    	for(Tuple2<Character, Integer> t : weights(s).keysValues())
        {
            pq.enqueue(new WLeafTree<Character>(t._1(), t._2()));
        }
        return pq;
    }

    // Exercise 2.b  
    public static WLeafTree<Character> huffmanTree(String s) 
	{
    	// s debe tener dos caracteres distintos. Si no, excepcion
		
        PriorityQueue<WLeafTree<Character>> pq = huffmanLeaves(s);
        WLeafTree<Character> wlt, wlt1, wlt2;
		
        wlt1 = pq.first();
        pq.dequeue();
		
        wlt2 = pq.first();
        pq.dequeue();

        wlt = new WLeafTree<Character>(wlt1, wlt2);
        while (!pq.isEmpty())
        {
            wlt = new WLeafTree<Character>(pq.first(), wlt);
            pq.dequeue();
        }
        return wlt;
    }

    // Exercise 3.a 
    public static Dictionary<Character, List<Integer>> joinDics(Dictionary<Character, List<Integer>> d1, Dictionary<Character, List<Integer>> d2) 
	{
        Dictionary<Character, List<Integer>> dict = new AVLDictionary<>();
		
        for(Tuple2<Character, List<Integer>> t : d1.keysValues())
        {
            dict.insert(t._1(), t._2());
        }
    	return dict;
    }

    // Exercise 3.b  
    public static Dictionary<Character, List<Integer>> prefixWith(int i, Dictionary<Character, List<Integer>> d)
	{
        Dictionary<Character, List<Integer>> dict = new AVLDictionary<>();
		
        for(Tuple2<Character, List<Integer>> t : d.keysValues())
        {
            List<Integer> lista = new LinkedList<>();
            lista.append(i);
			
            for(Integer in : t._2())
            {
                lista.append(in);
            }
            dict.insert(t._1(), lista);
        }
    	return dict;
    }

    // Exercise 3.c  
    public static Dictionary<Character, List<Integer>> huffmanCode(WLeafTree<Character> ht) 
	{
        if(ht.isLeaf())
        {
            Dictionary<Character, List<Integer>> dict = new AVLDictionary<>();
            dict.insert(ht.elem(), new LinkedList<>());
            return dict;
        }
        else
        {
            return joinDics(
                    prefixWith(0, huffmanCode(ht.leftChild())),
                    prefixWith(1, huffmanCode(ht.rightChild()))
            );
        }
    }

    // Exercise 4  
    public static List<Integer> encode(String s, Dictionary<Character, List<Integer>> hc) 
	{
        List<Integer> list = new LinkedList<>();
        List<Integer> list_aux;
        for(char c : s.toCharArray())
        {
            list_aux = hc.valueOf(c);
            for(int i :list_aux)
            {
                list.append(i);
            }
        }
    	return list;
    }

    // Exercise 5 
    public static String decode(List<Integer> bits, WLeafTree<Character> ht) 
	{
        WLeafTree<Character> aux = ht;
        String str ="";
        for(int i:bits)
        {
            if(i==0)
            {
                aux = aux.leftChild();
            }
            else
            {
                aux = aux.rightChild();
            }

            if(aux.isLeaf())
            {
                str += aux.elem();
                aux= ht;
            }
        }
    	return str;
    }
}
