/******************************************************************************
 * Student's name:
 * Student's group:
 * Data Structures. Grado en Informática. UMA.
******************************************************************************/

package SparseVector;

import java.util.Iterator;

public class SparseVector<T> implements Iterable<T> {

    protected interface Tree<T> {

        T get(int sz, int i);

        Tree<T> set(int sz, int i, T x);
    }

    // Unif Implementation

    protected static class Unif<T> implements Tree<T> {

        private T elem;

        public Unif(T e) {
            elem = e;
        }
        
        

        @Override
        public T get(int sz, int i) {
            return elem;
        }

        
        /*private Tree <T> setRec (int sz, int i, T x, Tree<T> y){
        	if(sz==1) {
        		return new Unif<T>(x);
        	}else if(i>=0&&i<sz/2) {
        		return new Node<T> (setRec (sz/2, i , x, y), this);
        	}else if(i>sz/2) {
        		return new Node <T>( this, setRec(sz/2,i-sz/2 , x , this));
        	}else {
        		throw new VectorException("ns");
        	}
			
        }
        
        @Override
        public Tree<T> set(int sz, int i, T x) {
            /*if(i<sz/2) {
            	return new Node <T>(setRec(sz/2,i , x , this) , this);
            }else if(i>sz/2) {
            	return new Node <T>( this, setRec(sz/2,i-sz/2 , x , this));
            }else {
            	throw new VectorException("jauams");
            }
        	
        	return setRec(sz, i, x, this);
        }*/
    
    
    	
    public Tree<T> set(int sz, int i, T x) {

            if(i<0 && i>sz-1) {
                throw new VectorException("Aurrau army");
            }

            if(sz==1) {
                elem=x;
                return new Unif<T>(elem);
            
            }else if(i<sz/2){

                return new Node<T>(new Unif<T>(elem).set(sz/2, i, x), new Unif<T>(elem)); //.simplify();

            }else {
                return new Node<T>(new Unif<T>(elem), new Unif<T>(elem).set(sz/2,i-sz/2,x)); //.simplify();
            }
    	}
    

        

		@Override
        public String toString() {
            return "Unif(" + elem + ")";
        }
    }

    // Node Implementation

    protected static class Node<T> implements Tree<T> {

        private Tree<T> left, right;

        public Node(Tree<T> l, Tree<T> r) {
            left = l;
            right = r;
        }

        
        
        @Override
        public T get(int sz, int i) {

        	
        	if(i<0 || i>sz-1) {
                throw new VectorException("Aurrau army");
            }
        	
        	if(i<sz/2 &&left instanceof Unif) {
        		return left.get(0, 0);
        	}else if(i>sz/2 &&right instanceof Unif) {
        		return  right.get(0, 0);
        	}else if(i<sz/2) {
            	return left.get(sz/2, i);
            }else {
            	return right.get(sz/2, i-sz/2);
            }/*else {
            	throw new VectorException("Exception");
            }*/
            	
            
        }

        @Override
        public Tree<T> set(int sz, int i, T x) {
        	if(i<0 || i>sz-1) {
                throw new VectorException("Aurrau army");
            }
        	
        	if(i<sz/2){

                left=left.set(sz/2, i, x);
                return simplify();

            }else{
               right=right.set(sz/2,i-sz/2,x);
               return simplify();
            }
			
			
    	}
        

        protected Tree<T> simplify() {
            if(left instanceof Unif && right instanceof Unif) {
            	if(left.get(0, 0)==right.get(0, 0)) {
            		return new Unif<T>(left.get(0, 0));
            	}else {
            		return this;
            	}
            }else {
            	return this;
            }
            
        }

        @Override
        public String toString() {
            return "Node(" + left + ", " + right + ")";
        }
    }

    // SparseVector Implementation

    private int sz;
    private Tree<T> root;

    public SparseVector(int n, T elem) {
        sz=(int) Math.pow(2,n);
        root=new Unif(elem);
        
    }

    public int size() {
        
        return sz;
    }

    public T get(int i) {
        return root.get(sz, i);
        
    }

    public void set(int i, T x) {
        root= root.set(sz, i, x);
    }

    @Override
    public Iterator<T> iterator() {
        
        return new VectorIterator();
    }

    @Override
    public String toString() {
        return "SparseVector(" + sz + "," + root + ")";
    }
    
    
    
    private class VectorIterator implements Iterator<T> {
    	int curr;
    	
    	public VectorIterator(){
    		curr=0;
    	}

		@Override
		public boolean hasNext() {
			return curr<sz;
		}

		@Override
		public T next() {
			
			T dev=root.get(sz, curr);
			curr++;
			return dev;
		}
    }
    
    /*public static void  main( String args[]) {
    	Unif<Integer> a = new Unif<Integer>(2);
    	
    	
    	System.out.print();
    	
    }*/
    
    
    
    
    
    
    
    
    
}
