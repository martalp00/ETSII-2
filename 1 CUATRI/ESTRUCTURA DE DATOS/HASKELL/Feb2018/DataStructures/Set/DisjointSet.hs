-------------------------------------------------------------------------------
-- Student's name: MARTA
-- Student's group:
--
-- Data Structures. February 2018. BSc. Computer Science. UMA.
-------------------------------------------------------------------------------

module DataStructures.Set.DisjointSet
                  ( DisjointSet
                  , empty
                  , isEmpty
                  , isElem
                  , numElements
                  , add
                  , areConnected
                  , kind
                  , union
                  , flatten
                  , kinds
                  ) where

import           Data.List                               (intercalate)
import           Data.Maybe                              (fromJust)
import qualified DataStructures.Dictionary.AVLDictionary as D

data DisjointSet a = DS (D.Dictionary a a)

-- | Exercise 1. empty

empty :: DisjointSet a
empty = DS (D.empty)  

-- | Exercise 2.a isEmpty

isEmpty :: DisjointSet a -> Bool
isEmpty (DS dic) = D.isEmpty dic

-- | Exercise 2.b isElem

isElem :: (Ord a) => a -> DisjointSet a -> Bool
isElem x (DS dic)
  | D.isEmpty dic = False
  | otherwise = elem  x  (D.keys  dic)

-- | Exercise 3. numElements

numElements :: DisjointSet a -> Int
numElements (DS dic) = D.size dic

-- | Exercise 4. add

add :: Ord a => a -> DisjointSet a -> DisjointSet a
add = undefined

-- | Exercise 5. root

root :: Ord a => a -> DisjointSet a -> Maybe a
root = undefined

-- | Exercise 6. isRoot

isRoot :: Ord a => a -> DisjointSet a -> Bool
isRoot = undefined

-- | Exercise 7. areConnected

areConnected :: Ord a => a -> a -> DisjointSet a -> Bool
areConnected = undefined

-- | Exercise 8. kind

kind :: Ord a => a -> DisjointSet a -> [a]
kind = undefined

-- | Exercise 9. union

union :: Ord a => a -> a -> DisjointSet a -> DisjointSet a
union = undefined

-- |------------------------------------------------------------------------

flatten :: Ord a => DisjointSet a -> DisjointSet a
flatten = undefined

kinds :: Ord a => DisjointSet a -> [[a]]
kinds = undefined

-- |------------------------------------------------------------------------

instance (Ord a, Show a) => Show (DisjointSet a) where
  show (DS d)  = "DictionaryDisjointSet(" ++ intercalate "," (map show (D.keysValues d)) ++ ")"


{-

-- Examples

-- | Exercise 1. empty

>>> empty
Ambiguous type variable ‘a0’ 
prevents the constraint ‘(Ord a0)’ from being solved.
Probable fix: use a type annotation to specify what ‘a0’ should be.
These potential instances exist:
  instance Ord OpenModule -- Defined in ‘Distribution.Backpack’
  instance Ord OpenUnitId -- Defined in ‘Distribution.Backpack’
  instance Ord CabalFeature
    -- Defined in ‘Distribution.CabalSpecVersion’
  ...plus 326 others
  (use -fprint-potential-instances to see them all)

-- | Exercise 2.a isEmpty

>>> isEmpty empty
True

>>> isEmpty (add 1 empty)
Prelude.undefined

-- | Exercise 2.b isElem

>>> isElem 1 empty
False

>>> isElem 1 (add 1 empty)
Prelude.undefined

>>> isElem 2 (add 1 empty)
Prelude.undefined

>>> isElem 1 (add 2 (add 1 empty))
Prelude.undefined

-- | Exercise 3. numElements

>>> numElements empty
0

>>> numElements (add 1 empty)
Prelude.undefined

>>> numElements (add 2 (add 1 empty))
Prelude.undefined

-- | Exercise 4. add

>>> add 1 empty
Prelude.undefined

>>> add 2 (add 1 empty)
Prelude.undefined

>>> add 1 (add 2 (add 1 empty))
Prelude.undefined

-- | Exercise 5. root

>>> root 1 empty
Prelude.undefined

>>> root 1 (add 1 empty)
Prelude.undefined

>>> root 2 (add 2 (add 1 empty))
Prelude.undefined

>>> root 1 (union 1 2 (add 2 (add 1 empty)))
Prelude.undefined

>>> root 2 (union 1 2 (add 2 (add 1 empty)))
Prelude.undefined

>>> root 1 (union 1 3 (add 3 (add 2 (add 1 empty))))
Prelude.undefined

>>> root 2 (union 1 3 (add 3 (add 2 (add 1 empty))))
Prelude.undefined

>>> root 3 (union 1 3 (add 3 (add 2 (add 1 empty))))
Prelude.undefined

>>> root 4 (union 1 3 (add 3 (add 2 (add 1 DisjointSet.empty))))
Not in scope: ‘DisjointSet.empty’
No module named ‘DisjointSet’ is imported.

-- | Exercise 6. isRoot

>>> isRoot 1 empty
Prelude.undefined

>>> isRoot 1 (add 1 empty)
Prelude.undefined

>>> isRoot 1 (union 1 2 (add 2 (add 1 empty)))
Prelude.undefined

>>> isRoot 2 (union 1 2 (add 2 (add 1 empty)))
Prelude.undefined

>>> isRoot 1 (union 1 3 (add 3 (add 2 (add 1 empty))))
Prelude.undefined

>>> isRoot 2 (union 1 3 (add 3 (add 2 (add 1 empty))))
Prelude.undefined

>>> isRoot 3 (union 1 3 (add 3 (add 2 (add 1 empty))))
Prelude.undefined

-- | Exercise 7. areConnected

>>> areConnected 1 3 (union 1 3 (add 3 (add 2 (add 1 empty))))
Prelude.undefined

>>> areConnected 3 1 (union 1 3 (add 3 (add 2 (add 1 empty))))
Prelude.undefined

>>> areConnected 1 1 (union 1 3 (add 3 (add 2 (add 1 empty))))
Prelude.undefined

>>> areConnected 1 2 (union 1 3 (add 3 (add 2 (add 1 empty))))
Prelude.undefined

>>> areConnected 1 2 (union 2 3 (union 1 3 (add 3 (add 2 (add 1 empty)))))
Prelude.undefined

>>> areConnected 1 5 (union 2 3 (union 1 3 (add 3 (add 2 (add 1 empty)))))
Prelude.undefined

-- | Exercise 8. kind

>>> kind 1 (add 2 (add 1 empty))
Prelude.undefined

>>> kind 2 (add 2 (add 1 empty))
Prelude.undefined

>>> kind 3 (add 2 (add 1 empty))
Prelude.undefined

>>> kind 1 (union 1 3 (add 3 (add 2 (add 1 empty))))
Prelude.undefined

>>> kind 3 (union 1 3 (add 3 (add 2 (add 1 empty))))
Prelude.undefined

>>> kind 2 (union 1 3 (add 3 (add 2 (add 1 empty))))
Prelude.undefined

>>> kind 2 (union 2 3 (union 1 3 (add 3 (add 2 (add 1 empty)))))
Prelude.undefined

-- | Exercise 9. union

>>> union 1 2 (add 2 (add 1 empty))
Prelude.undefined

>>> union 2 1 (add 2 (add 1 empty))
Prelude.undefined

>>> union 1 1 (add 2 (add 1 empty))
Prelude.undefined

>>> union 1 3 (add 3 (add 2 (add 1 empty)))
Prelude.undefined

>>> union 1 2 (add 1 empty)
Prelude.undefined

-}
