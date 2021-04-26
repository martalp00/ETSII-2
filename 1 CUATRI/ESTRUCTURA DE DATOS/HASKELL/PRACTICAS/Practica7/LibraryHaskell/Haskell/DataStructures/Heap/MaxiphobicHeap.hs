-------------------------------------------------------------------------------
-- Maxiphobic Heaps
--
-- see: Fun with Binary Heap Trees
--      & Alternatives to two Classic Data Structures
--      by Chris Okasaki
--
-- Data Structures. Grado en Informática. UMA.
-- Pepe Gallardo, 2012
-------------------------------------------------------------------------------

module DataStructures.Heap.MaxiphobicHeap
  ( Heap
  , empty
  , isEmpty
  , minElem
  , delMin
  , insert
  , merge

  , mkHeap
  , size
  , isHeap

  , drawOnWith
  ) where

import DataStructures.Graphics.DrawTrees
import Test.QuickCheck

data Heap a  = Empty | Node a Int (Heap a) (Heap a) deriving Show

-- number of elements
size :: Heap a -> Int
size Empty            = 0
size (Node _ sz _ _)  = sz

empty :: Heap a
empty  = Empty

isEmpty :: Heap a -> Bool
isEmpty Empty  = True
isEmpty _      = False

singleton :: a -> Heap a
singleton x  = Node x 1 Empty Empty

insert :: (Ord a) => a -> Heap a -> Heap a
insert x h  = merge (singleton x) h

minElem :: Heap a -> a
minElem Empty           = error "minElem on empty heap"
minElem (Node x _ _ _)  = x

delMin :: (Ord a) => Heap a -> Heap a
delMin Empty             = error "delMin on empty heap"
delMin (Node _ _ lh rh)  = merge lh rh

----------------------------------------------------------
-- VVVVVVVVVVVV-SOLO TOCAR ABAJO-VVVVVVVVVVVV--------------
---------------------------------------------------------
-- recursively merges smallest subheaps. Achieves O(log n) complexity
merge :: (Ord a) => Heap a -> Heap a -> Heap a
merge Empty h'     = h'
merge h     Empty  = h
merge h@(Node x sz lh rh) h'@(Node x' sz' lh' rh')
	| x < x'          = mergeAux x h' lh rh
	| otherwise       = mergeAux x' h lh' rh'
	where	
		mergeAux r h1 h2 h3 = Node (r) (sz + sz') (mayor) (merge menor1 menor2)
		(mayor, menor1, menor2) = ordena3Heaps h1 h2 h3
	
ordena3Heaps:: (Ord a) => Heap a -> Heap a -> Heap a -> (Heap a, Heap a, Heap a)
-- el primer heap es el más pesado, el resto son menos pesados
ordena3Heaps h1 h2 h3			-- @(Node x1 s1 lh1 lh2)
	| s1 > s2 && s1 > s3 = (h1, h2, h3)
	| s2 > s1 && s2 > s3 = (h2, h1, h3)
	| otherwise = (h3, h1, h2)
	where
		s1 = size h1
		s2 = size h2
		s3 = size h3

----------------------------------------------------------
-- ^^^^^^^^^^^^^^-- SOLO TOCAR ARRIBA ^^^^^^^^^^^ ---------
----------------------------------------------------------

-- Efficient O(n) bottom-up construction for heaps
mkHeap :: (Ord a) => [a] -> Heap a
mkHeap []  = empty
mkHeap xs  = mergeLoop (map singleton xs)
  where
    mergeLoop [h]  = h
    mergeLoop hs   = mergeLoop (mergePairs hs)

    mergePairs []         = []
    mergePairs [h]        = [h]
    mergePairs (h:h':hs)  = merge h h' : mergePairs hs

-------------------------------------------------------------------------------
-- Generating arbritray Heaps
-------------------------------------------------------------------------------

instance (Ord a, Arbitrary a) => Arbitrary (Heap a) where
  arbitrary  = do
    xs <- arbitrary
    return (mkHeap xs)

-------------------------------------------------------------------------------
-- Invariants
-------------------------------------------------------------------------------

isHeap :: (Ord a) => Heap a -> Bool
isHeap Empty             = True
isHeap (Node x _ lh rh)  = x `lessEq` lh && x `lessEq` rh
                           && isHeap lh && isHeap rh
 where
  x `lessEq` Empty            = True
  x `lessEq` (Node x' _ _ _)  = x<=x'


-------------------------------------------------------------------------------
-- Drawing a Heap
-------------------------------------------------------------------------------

instance Subtrees (Heap a) where
  subtrees Empty             = []
  subtrees (Node _ _ lh rh)  = [lh,rh]

  isEmptyTree  = isEmpty

instance (Show a) => ShowNode (Heap a) where
  showNode (Node x _ _ _) = show x

drawOnWith :: FilePath -> (a -> String) -> Heap a -> IO ()
drawOnWith file toString = _drawOnWith file showHeap
 where
  showHeap (Node x _ _ _) = toString x
