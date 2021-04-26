-------------------------------------------------------------------------------
-- Linear implementation of Priority Queues
--
-- Data Structures. Grado en Informática. UMA.
-------------------------------------------------------------------------------

module DataStructures.PriorityQueue.LinearPriorityQueue
  ( PQueue
-- basicas  
  , empty    
  , isEmpty  
  , first    
  , dequeue  
  , enqueue  
-- extras
  , mkPQ     
  , mapPQ    
  , filterPQ 
  , foldrPQ  
  , fromPQ   
  , toPQ     
  , toList   
  ) where

import Data.List(intercalate)
import Test.QuickCheck

data PQueue a = Empty | Node a (PQueue a)

-- empty. Crea una cola de prioridad vacia. 
empty ::PQueue a
empty = Empty

-- isEmpty. Comprueba si una cola de prioridad esta vacia.
isEmpty ::PQueue a -> Bool
isEmpty Empty = True
isEmpty _ = False

-- enqueue. Encola un elemento en una cola de prioridad.
enqueue :: (Ord a) => a ->PQueue a -> PQueue a
enqueue x Empty  = Node x Empty
enqueue x (Node y q)
  | x < y         = Node x (Node y q)
  | otherwise     = Node y (enqueue x q)

-- first. Devuelve el primer elemento de una cola de prioridad.
first :: PQueue a -> a
first Empty       = error "first en cola vacia"
first (Node x _)  = x

-- dequeue. Desencola el primer elemento de una cola de prioridad.
dequeue :: PQueue a -> PQueue a
dequeue Empty       = error "dequeue en cola vacia"
dequeue (Node _ q)  = q

-- mkPQ. Crea una cola de prioridad con los elementos de la lista.
mkPQ :: (Ord a) => [a] -> PQueue a
mkPQ xs = foldr enqueue empty xs

-- mapPQ. Transforma una cola de prioridad en otra aplicando la
-- funcion dada a cada elemento.
-- Cuidado con esta función. Hay que mantener que sea cola de prioridad
mapPQ :: (Ord a, Ord b) => (a -> b) -> PQueue a -> PQueue b
mapPQ _ Empty = Empty
mapPQ x (Node e cola) = enqueue (x e) (mapPQ x cola)

-- filterPQ. Crea una cola de prioridad con los elementos de la cola 
-- de prioridad dada que cumplen el predicado.
filterPQ :: Ord a => (a -> Bool) -> PQueue a -> PQueue a
filterPQ x Empty = empty
filterPQ x (Node e cola) = if (x e) then enqueue (e) (filterPQ x cola) else (filterPQ x cola)

-- foldrPQ. Realiza un plegado por la derecha de la cola de prioridad
-- usando la funcion y el caso base dados 
foldrPQ :: (Ord a) => (a -> b -> b) -> b -> PQueue a -> b
foldrPQ _ cb Empty = cb
foldrPQ f cb (Node e cola) = f e (foldrPQ f cb cola)

-- fromPQ. Devuelve una cola a partir de los elementos que son mayores 
-- o iguales a uno dado
-- No es buena solución hacerlo usando filterPQ
fromPQ :: Ord a => a -> PQueue a -> PQueue a
fromPQ _ _ = undefined

-- toPQ. Devuelve una cola hasta los elementos menores que uno dado.
-- No es buena solución hacerlo usando filterPQ
toPQ :: Ord a => a -> PQueue a -> PQueue a
toPQ _ Empty = empty
toPQ x (Node e cola) = if (x>e) then Node e (toPQ x cola) else (toPQ x cola)

-- toList. Devuelve una lista con los elementos de la cola
-- Es buena solución hacerlo con un plegado
toList :: Ord a => PQueue a -> [a]
toList Empty = []
toList cola = foldrPQ (:) [] cola

-- p_pqinversa. Propiedad que comprueba que mkPQueue y toList son inversas una 
-- de la otra
p_pqinversa :: (Ord a) => PQueue a -> Bool
p_pqinversa x =(mkPQ (toList x))== x

-- Tabla de complejidades
-- Completar 
{-  
                     Complejidad
        empty	        O(1)
        isEmpty      	O(1)
        enqueue	     	O(1)
        first	        O(1)
        dequeue	      	O(1)
        mkPQ	        O(1)
        mapPQ	        O(1)
        filterPQ	    O(1)
        foldrPQ	      	O(1)
        fromPQ	      	O(1)
        toPQ	        O(1)
        toList	      	O(1)
-}

{- quitar estos comentarios para probar

-- ===============
-- Ejemplos de uso
-- ===============
s1 = mkPQ [2,5,4,7,9,8,1,6,8,5,7]
-- LinearPQueue(1,2,4,5,5,6,7,7,8,8,9)

s2 = enqueue 10 (enqueue 3 (enqueue 0 s1))
-- LinearPQueue(0,1,2,3,4,5,5,6,7,7,8,8,9,10)

s3 = dequeue s2
-- LinearPQueue(1,2,3,4,5,5,6,7,7,8,8,9,10)

s4 = mapPQ (\x -> x `mod` 5) s3
-- LinearPQueue(0,0,0,1,1,2,2,2,3,3,3,4,4)

s5 = filterPQ even s4
-- LinearPQueue(0,0,0,2,2,2,4,4)

s6 = foldrPQ (-) 0 s5
-- -2

s7 = foldrPQ (\e s -> even e && s) True s5
-- True

s8 = fromPQ 5 s1
-- LinearPQueue(5,5,6,7,7,8,8,9)

s9 = toPQ 5 s1
-- LinearPQueue(1,2,4)

s10 = toList s1
-- [1,2,4,5,5,6,7,7,8,8,9]

-}










-- ===============================================
--   NO TOCAR ESTA PARTE
-- ===============================================

-- Showing a priority PQueue
instance (Show a) => Show (PQueue a) where
  show q = "LinearPQueue(" ++ intercalate "," (aux q) ++ ")"
    where
     aux Empty      =  []
     aux (Node x q) =  show x : aux q

-- Priority Queue equality
instance (Eq a) => Eq (PQueue a) where
  Empty      == Empty           =  True
  (Node x q) == (Node x' q')    =  x==x' && q==q'
  _          == _               =  False

-- This instance is used by QuickCheck to generate random Priority Queues
instance (Ord a, Arbitrary a) => Arbitrary (PQueue a) where
    arbitrary =  do
      xs <- listOf arbitrary
      return (foldr enqueue empty xs)


