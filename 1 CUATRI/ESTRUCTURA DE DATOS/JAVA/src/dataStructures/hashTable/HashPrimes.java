/**
 * @author Pepe Gallardo, Data Structures, Grado en Inform�tica. UMA.
 *
 * Hash Table capacities as prime numbers
 */

package dataStructures.hashTable;

import dataStructures.tuple.Tuple2;

import java.util.Random;

/**
 * Class for generating size of arrays as prime numbers.
 */
public class HashPrimes {
	
	private static int primes[] = 
	 new int[] { 53, 97, 193, 389, 769, 1543, 3079, 6151, 12289, 24593
             , 49157,	98317, 196613, 393241, 786433, 1572869, 3145739
	           , 6291469, 12582917,	25165843,	50331653,	100663319
	           , 201326611,	402653189, 805306457, 1610612741
	           };

	
	// returns a prime value > n
	/**
	 * Returns a prime number greater than {@code n} or 1610612741 if {@code n} is greater than this value.
	 * @param n Lower bound for prime number.
	 * @return A prime number greater than {@code n} or 1610612741 if {@code n} is greater than this value.	 
	 */
	public static int primeGreaterThan(int n) {
		int i = 0;
		while((i<primes.length) && (primes[i] <= n))
			i++;
		
		if(i>=primes.length)
			throw new RuntimeException("HashPrime.upperPrime: argument "+n+" too large");
		else
			return primes[i];
	}
	
	
	/**
	 * Returns a prime number greater than {@code 2*n}, or 1610612741 if {@code n} is greater than this value.
	 * @param n 2*n is lower bound for sought prime number.
	 * @return A prime number greater than {@code 2*n} or 1610612741 if {@code n} is greater than this value.	 
	 */
	public static int primeDoubleThan(int n) {
		return primeGreaterThan(n+1);
	}

    public static class HashTableTest
    {

        final static int maxValue = 10000;
        final static int numElems = 1000;

        static void initTables(Random rnd, HashTable<Integer, String> t1, HashTable<Integer, String> t2) {

            for(int i=0; i<numElems; i++) {
                Integer n = rnd.nextInt(maxValue);
                t1.insert(n, n.toString());
                t2.insert(n, n.toString());
            }
        }


        static void remove(Random rnd, HashTable<Integer, String> t1, HashTable<Integer, String> t2) {
            for(int i=0; i<numElems/2; i++) {
                Integer n = rnd.nextInt(maxValue);
                t1.delete(n);
                t2.delete(n);
            }
        }


        static void testEq(HashTable<Integer, String> t1, HashTable<Integer, String> t2) {
            for(int k=0; k<maxValue; k++) {
                String s1 = t1.search(k);
                String s2 = t2.search(k);
                if(s1==null) {
                    if(s2!=null) {
                        System.out.printf("\nERROR on search for %d",k);
                        System.exit(1);
                    }

                } else if(!s1.equals(s2)) {
                    System.out.printf("\nERROR on search for %d: %s %s",k,s1,s2);
                    System.exit(1);
                }
            }
            System.out.println("OK");
        }

        static void oneTest(int seed) {
            Random rnd = new Random(seed);

            HashTable<Integer,String> scHashTable = new SeparateChainingHashTable<Integer,String>(97,0.5);
            HashTable<Integer,String> lpHashTable = new LinearProbingHashTable<Integer,String>(97,0.5);

            initTables(rnd,scHashTable,lpHashTable);
            System.out.println("TEST for insert and search");
            testEq(scHashTable,lpHashTable);

            remove(rnd,scHashTable,lpHashTable);
            System.out.println("TEST for delete and search");
            testEq(scHashTable,lpHashTable);

            System.out.println("Associations traversal:");
            for(Tuple2<Integer, String> t : lpHashTable.keysValues())
                System.out.print(t+" ");
            System.out.println();

        }

        public static void main(String[] args) {
            for(int seed=0; seed<10; seed++)
                oneTest(seed);
        }
    }
}
