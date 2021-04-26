/**
 * Student's name:
 *
 * Student's group:
 */

import dataStructures.list.ArrayList;
import dataStructures.list.List;
import dataStructures.list.LinkedList;

import java.util.Iterator;


class Bin {
    private int remainingCapacity; // capacity left for this bin
    private List<Integer> weights; // weights of objects included in this bin

    public Bin(int initialCapacity) {
        remainingCapacity=initialCapacity;
        weights=new ArrayList<>();
    }

    // returns capacity left for this bin
    public int remainingCapacity() {
        return remainingCapacity;
    }

    // adds a new object to this bin
    public void addObject(int weight) {
        if(weight>remainingCapacity)
            throw new RuntimeException("Not enough space");

        remainingCapacity-=weight;
        weights.append(weight);
    }

    // returns an iterable through weights of objects included in this bin
    public Iterable<Integer> objects() {
        // todo
        //  SOLO PARA ALUMNOS SIN EVALUACION CONTINUA
        //  ONLY FOR STUDENTS WITHOUT CONTINUOUS ASSESSMENT
        return null;
    }

    public String toString() {
        String className = getClass().getSimpleName();
        StringBuilder sb = new StringBuilder(className);
        sb.append("(");
        sb.append(remainingCapacity);
        sb.append(", ");
        sb.append(weights.toString());
        sb.append(")");
        return sb.toString();
    }
}

// Class for representing an AVL tree of bins
public class AVL {
    static private class Node {
        Bin bin; // Bin stored in this node
        int height; // height of this node in AVL tree
        int maxRemainingCapacity; // max capacity left among all bins in tree rooted at this node
        Node left, right; // left and right children of this node in AVL tree

        // recomputes height of this node
        void setHeight() {
            int hl=0, hr=0;
            if(left != null){
                left.setHeight();
                hl=left.height;
            }
            if(right != null){
                right.setHeight();
                hr=right.height;
            }
            maxRemainingCapacity=1+Math.max(hl,hr);
        }

        // recomputes max capacity among bins in tree rooted at this node
        void setMaxRemainingCapacity() {
            int ml=0, mr=0;
            if(left != null){
                left.setMaxRemainingCapacity();
                ml=left.maxRemainingCapacity;
            }
            if(right != null){
                left.setMaxRemainingCapacity();
                mr=left.maxRemainingCapacity;
            }
            maxRemainingCapacity=Math.max(bin.remainingCapacity(),Math.max(ml,mr));
        }

        // left-rotates this node. Returns root of resulting rotated tree
        Node rotateLeft() {

            Node sol=new Node();
            Node l= new Node();
            l.bin=bin;
            l.left=left;
            l.right=right.left;

            sol.bin=right.bin;
            sol.right=right.right;
            sol.left=l;

            return sol;
        }
    }

    private static int height(Node node) {
        return node.height;
    }

    private static int maxRemainingCapacity(Node node) {
        return node.maxRemainingCapacity;
    }

    private Node root; // root of AVL tree

    public AVL() {
        this.root = null;
    }

    // adds a new bin at the end of right spine.
    private void addNewBin(Bin bin) {

        if(this.root==null){
            root=new Node();
            root.bin=bin;
            root.setHeight();
            root.setMaxRemainingCapacity();
        }else {
            Node current = root;

            while (current.right != null) {
                current = current.right;
            }

            current.right = new Node();

            current.right.bin = bin;


            root.setHeight();
            root.setMaxRemainingCapacity();

            if (root.right.height - ((root.left == null) ? 0 : root.left.height) > 1)
                root = root.rotateLeft();
        }

    }

    // adds an object to first suitable bin. Adds
    // a new bin if object cannot be inserted in any existing bin
    public void addFirst(int initialCapacity, int weight) {
        if(root==null){
            addNewBin(new Bin(initialCapacity));
            root.bin.addObject(weight);
        }else if(root.left!=null && root.left.maxRemainingCapacity>=weight)
            addAtNode(initialCapacity,weight,root.left);
        else if(root.bin.remainingCapacity()>=weight)
            root.bin.addObject(weight);
        else {
            if(root.right==null) {
                root.right = new Node();
                root.right.bin = new Bin(initialCapacity);
            }
            addAtNode(initialCapacity, weight, root.right);

        }

    }

    private void addAtNode(int initialCapacity, int weight, Node n) {

        if(n==null){
            n=new Node();
            n.bin= new Bin(initialCapacity);
            n.bin.addObject(weight);
        }else if(n.left!=null && n.left.maxRemainingCapacity>=weight)
            addAtNode(initialCapacity,weight,n.left);
        else if(n.bin.remainingCapacity()>=weight)
            n.bin.addObject(weight);
        else {
            if(n.right==null) {
                n.right = new Node();
                n.right.bin = new Bin(initialCapacity);
            }
            addAtNode(initialCapacity, weight, n.right);
        }
    }

    public void addAll(int initialCapacity, int[] weights) {
        for(int i:weights){
            addFirst(initialCapacity,i);
        }
    }

    public List<Bin> toList() {

        return root==null ? new ArrayList<>():toListRec(root);
    }

    private List<Bin> toListRec(Node root) {
        List<Bin> s=new ArrayList<>();

        if(root.left!=null) {
            for (Bin elem : toListRec(root.left)) {
                s.append(elem);
            }
        }

        s.append(root.bin);
        
        if(root.right!=null) {
            for (Bin elem : toListRec(root.right)) {
                s.append(elem);
            }
        }
        return s;
    }

    public String toString() {
        String className = getClass().getSimpleName();
        StringBuilder sb = new StringBuilder(className);
        sb.append("(");
        stringBuild(sb, root);
        sb.append(")");
        return sb.toString();
    }

    private static void stringBuild(StringBuilder sb, Node node) {
        if(node==null)
            sb.append("null");
        else {
            sb.append(node.getClass().getSimpleName());
            sb.append("(");
            sb.append(node.bin);
            sb.append(", ");
            sb.append(node.height);
            sb.append(", ");
            sb.append(node.maxRemainingCapacity);
            sb.append(", ");
            stringBuild(sb, node.left);
            sb.append(", ");
            stringBuild(sb, node.right);
            sb.append(")");
        }
    }

    public static void main(String[] args) {
        AVL a=new AVL();
        a.addAll(10, new int[]{ 5, 7, 5, 2, 4, 2, 5, 1, 6 });
        a.addNewBin(new Bin(10));
        a.addFirst(10,8);
        a.addFirst(10,3);
        System.out.println(a.toString());;
    }

}

class LinearBinPacking {
    public static List<Bin> linearBinPacking(int initialCapacity, List<Integer> weights) {
        // todo
        //  SOLO PARA ALUMNOS SIN EVALUACION CONTINUA
        //  ONLY FOR STUDENTS WITHOUT CONTINUOUS ASSESSMENT
        return null;
    }
	
	public static Iterable<Integer> allWeights(Iterable<Bin> bins) {
        // todo
        //  SOLO PARA ALUMNOS SIN EVALUACION CONTINUA
        //  ONLY FOR STUDENTS WITHOUT CONTINUOUS ASSESSMENT
        return null;		
	}

}

